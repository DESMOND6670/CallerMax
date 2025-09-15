package com.callerMax.coldcalling

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.callerMax.coldcalling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CallViewModel
    private lateinit var adapter: NumberListAdapter
    private lateinit var telephonyManager: TelephonyManager
    
    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    // Call ended, proceed to next number
                    viewModel.onCallEnded()
                }
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    // Call answered
                    viewModel.onCallAnswered()
                }
                TelephonyManager.CALL_STATE_RINGING -> {
                    // Call ringing
                    viewModel.onCallRinging()
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViewModel()
        setupUI()
        setupTelephonyManager()
        checkPermissions()
    }
    
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[CallViewModel::class.java]
        
        viewModel.callState.observe(this) { state ->
            updateUI(state)
        }
        
        viewModel.currentNumber.observe(this) { number ->
            binding.tvCurrentNumber.text = "Calling: $number"
        }
        
        viewModel.callCount.observe(this) { count ->
            binding.tvCallCount.text = "Calls made: $count"
        }
    }
    
    private fun setupUI() {
        // Setup RecyclerView
        adapter = NumberListAdapter { number ->
            viewModel.removeNumber(number)
        }
        binding.recyclerViewNumbers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNumbers.adapter = adapter
        
        // Setup buttons
        binding.btnAddNumber.setOnClickListener {
            val number = binding.etPhoneNumber.text.toString().trim()
            if (number.isNotEmpty()) {
                viewModel.addNumber(number)
                binding.etPhoneNumber.text?.clear()
            } else {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnStartCalls.setOnClickListener {
            if (viewModel.getNumberList().isNotEmpty()) {
                startCallService()
            } else {
                Toast.makeText(this, "Please add some numbers first", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnStopCalls.setOnClickListener {
            stopCallService()
        }
        
        binding.btnClearList.setOnClickListener {
            viewModel.clearNumberList()
        }
        
        // Bulk import functionality
        binding.btnImportBulk.setOnClickListener {
            val bulkText = binding.etBulkNumbers.text.toString().trim()
            if (bulkText.isNotEmpty()) {
                importBulkNumbers(bulkText)
            } else {
                Toast.makeText(this, "Please enter some numbers first", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnClearBulk.setOnClickListener {
            binding.etBulkNumbers.text?.clear()
        }
        
        // Observe number list changes
        viewModel.numberList.observe(this) { numbers ->
            adapter.updateNumbers(numbers)
        }
    }
    
    private fun setupTelephonyManager() {
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }
    
    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS
        )
        
        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), 1001)
        }
    }
    
    private fun startCallService() {
        val intent = Intent(this, CallService::class.java)
        intent.putStringArrayListExtra("numberList", ArrayList(viewModel.getNumberList()))
        ContextCompat.startForegroundService(this, intent)
        viewModel.startCalling()
    }
    
    private fun stopCallService() {
        val intent = Intent(this, CallService::class.java)
        stopService(intent)
        viewModel.stopCalling()
    }
    
    private fun updateUI(state: CallState) {
        when (state) {
            CallState.IDLE -> {
                binding.btnStartCalls.isEnabled = true
                binding.btnStopCalls.isEnabled = false
                binding.tvStatus.text = "Ready to start calling"
            }
            CallState.CALLING -> {
                binding.btnStartCalls.isEnabled = false
                binding.btnStopCalls.isEnabled = true
                binding.tvStatus.text = "Calling in progress..."
            }
            CallState.RINGING -> {
                binding.tvStatus.text = "Ringing..."
            }
            CallState.ANSWERED -> {
                binding.tvStatus.text = "Call answered"
            }
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                setupTelephonyManager()
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions required for calling", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun importBulkNumbers(bulkText: String) {
        val lines = bulkText.split("\n")
        var addedCount = 0
        var skippedCount = 0
        
        for (line in lines) {
            val number = line.trim()
            if (number.isNotEmpty() && isValidPhoneNumber(number)) {
                viewModel.addNumber(number)
                addedCount++
            } else if (number.isNotEmpty()) {
                skippedCount++
            }
        }
        
        // Clear the bulk input after importing
        binding.etBulkNumbers.text?.clear()
        
        // Show result message
        val message = when {
            addedCount > 0 && skippedCount > 0 -> "Added $addedCount numbers, skipped $skippedCount invalid numbers"
            addedCount > 0 -> "Successfully added $addedCount numbers"
            skippedCount > 0 -> "No valid numbers found. Please check the format"
            else -> "No numbers to import"
        }
        
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    
    private fun isValidPhoneNumber(number: String): Boolean {
        // Remove all non-digit characters except +
        val cleanNumber = number.replace(Regex("[^\\d+]"), "")
        
        // Check if it's a valid phone number format
        return when {
            cleanNumber.startsWith("+") && cleanNumber.length >= 10 -> true
            cleanNumber.length >= 10 && cleanNumber.length <= 15 -> true
            cleanNumber.length >= 7 && cleanNumber.length <= 10 -> true
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }
}
