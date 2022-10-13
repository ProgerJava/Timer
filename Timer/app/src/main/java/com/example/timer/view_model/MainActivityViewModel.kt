package com.example.timer.view_model

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class MainActivityViewModel : ViewModel() {

    var resultCounter = MutableLiveData <Long>()
    var resultTimer = MutableLiveData <Long>()
    var count : Long = 0
    var countTimer : Long = 0
    private var timer = Timer()
    lateinit var countDownTimer : CountDownTimer
    var flag = true

    fun start () {
        if (countTimer != 0L) {
            countDownTimer.onFinish()
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                count++
                resultCounter.postValue(count)
                flag = true
            }
        }, 0, 1000)

    }
    fun stop ()  {
        timer.cancel()
        timer = Timer()
        flag = false
    }

    fun restart() {
        if (count != 0L) {
            count = 0
            timer.cancel()
            timer = Timer()
            resultCounter.postValue(count)
        }
        if (countTimer != 0L) {
            countDownTimer.onFinish()
        }
    }
    fun startCountDownTimer (milliseconds : Long) {
        if (count != 0L) {
            timer.cancel()
            timer = Timer()
            count = 0
        }
        if (countTimer != 0L) {
            countDownTimer.onFinish()
        }
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTimer++
                resultTimer.postValue(millisUntilFinished)
            }

            override fun onFinish() {
                resultTimer.postValue(0)
                countTimer = 0
                cancel()
            }
        }.start()
    }
}

