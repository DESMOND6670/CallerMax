package com.callerMax.coldcalling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CallViewModel : ViewModel() {
    
    private val _numberList = MutableLiveData<List<String>>()
    val numberList: LiveData<List<String>> = _numberList
    
    private val _callState = MutableLiveData<CallState>()
    val callState: LiveData<CallState> = _callState
    
    private val _currentNumber = MutableLiveData<String>()
    val currentNumber: LiveData<String> = _currentNumber
    
    private val _callCount = MutableLiveData<Int>()
    val callCount: LiveData<Int> = _callCount
    
    private val numbers = mutableListOf<String>()
    private var callsMade = 0
    
    init {
        _numberList.value = emptyList()
        _callState.value = CallState.IDLE
        _callCount.value = 0
    }
    
    fun addNumber(number: String) {
        if (number.isNotEmpty() && !numbers.contains(number)) {
            numbers.add(number)
            _numberList.value = numbers.toList()
        }
    }
    
    fun removeNumber(number: String) {
        numbers.remove(number)
        _numberList.value = numbers.toList()
    }
    
    fun clearNumberList() {
        numbers.clear()
        _numberList.value = emptyList()
    }
    
    fun getNumberList(): List<String> = numbers.toList()
    
    fun startCalling() {
        _callState.value = CallState.CALLING
    }
    
    fun stopCalling() {
        _callState.value = CallState.IDLE
    }
    
    fun onCallRinging() {
        _callState.value = CallState.RINGING
    }
    
    fun onCallAnswered() {
        _callState.value = CallState.ANSWERED
    }
    
    fun onCallEnded() {
        callsMade++
        _callCount.value = callsMade
        _callState.value = CallState.CALLING
    }
    
    fun setCurrentNumber(number: String) {
        _currentNumber.value = number
    }
}

enum class CallState {
    IDLE,
    CALLING,
    RINGING,
    ANSWERED
}
