package com.example.timer.view_model

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.model.ModelTimer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class TimerViewModel (application: Application) : AndroidViewModel(application) {
    var result = MutableLiveData <ArrayList<String>>()
    var globalTimer = MutableLiveData <String>()
    private val model = ModelTimer (application.applicationContext)
    private var array = ArrayList <String> ()

    fun setTimerBase () {
        model.addToDb("Секундомер")
        model.addToDb("1 мин")
        model.addToDb("2 мин")
        model.addToDb("5 мин")
        model.addToDb("10 мин")
        model.addToDb("30 мин")
    }
    fun addTimer (simple : String) {
        model.addToDb(simple)
        array = model.readDb()
        result.postValue(array)
    }

    fun readTimer () {
        array = model.readDb()
        result.postValue(array)
    }
    fun deleteIntoDb(pos: String) {
        model.deleteElements(pos)
        setTimerBase()
        array = model.readDb()
        result.postValue(array)
    }

    @SuppressLint("SimpleDateFormat")
    fun getLocalTime () {
        while (true) {
            val formatter = SimpleDateFormat("hh:mm:ss a")
            val time = formatter.format(Date())
            globalTimer.postValue(time)
        }
    }

}