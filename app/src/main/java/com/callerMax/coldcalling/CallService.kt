package com.callerMax.coldcalling

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CallService : Service() {
    
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var telecomManager: TelecomManager
    private lateinit var executor: ScheduledExecutorService
    private var numberList = mutableListOf<String>()
    private var currentIndex = 0
    private var isCalling = false
    private var callStartTime = 0L
    
    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    // Call ended, wait a bit then call next number
                    if (isCalling) {
                        executor.schedule({
                            callNextNumber()
                        }, 2, TimeUnit.SECONDS)
                    }
                }
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    // Call answered, wait for natural end
                    callStartTime = System.currentTimeMillis()
                }
                TelephonyManager.CALL_STATE_RINGING -> {
                    // Call ringing
                }
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        executor = Executors.newSingleThreadScheduledExecutor()
        
        createNotificationChannel()
        startForeground(1, createNotification())
        
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringArrayListExtra("numberList")?.let { list ->
            numberList.clear()
            numberList.addAll(list)
            currentIndex = 0
            isCalling = true
            
            if (numberList.isNotEmpty()) {
                callNextNumber()
            }
        }
        
        return START_STICKY
    }
    
    private fun callNextNumber() {
        if (currentIndex < numberList.size && isCalling) {
            val number = numberList[currentIndex]
            makeCall(number)
            currentIndex++
        } else {
            // All numbers called, stop service
            stopSelf()
        }
    }
    
    private fun makeCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = android.net.Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent)
                }
            } else {
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // If call fails, try next number
            executor.schedule({
                callNextNumber()
            }, 1, TimeUnit.SECONDS)
        }
    }
    
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Call Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Cold calling service"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("CallerMax - Cold Calling")
            .setContentText("Calling numbers automatically - waiting for calls to end naturally...")
            .setSmallIcon(R.drawable.ic_phone)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        isCalling = false
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
        executor.shutdown()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    companion object {
        const val CHANNEL_ID = "call_service_channel"
    }
}
