package com.example.timer.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.timer.R
import com.example.timer.view_model.TimerViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class Timer : AppCompatActivity() {

    private var backPressedTime : Long = 0
    private lateinit var text : TextView

    private lateinit var plusMinute : ImageView
    private lateinit var minusMinute : ImageView
    private lateinit var plusSecond : ImageView
    private lateinit var minusSecond : ImageView

    private lateinit var imageHome : ImageView
    private lateinit var imageSetting : ImageView

    private lateinit var imageSave : ImageView
    private lateinit var imageDelete : ImageView

    private lateinit var timerViewModel : TimerViewModel
    private var counterMinute : Int = 0
    private var counterSecond : Int = 0

    private lateinit var globalTimer : TextView
    private lateinit var appName : TextView
    private lateinit var globalTimeTitle : TextView
    lateinit var sharedPreferences : SharedPreferences
    lateinit var backgroundMain : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_timer)
        text = findViewById(R.id.text)
        plusMinute = findViewById(R.id.plusMinute)
        minusMinute = findViewById(R.id.minusMinute)
        plusSecond = findViewById(R.id.plusSecond)
        minusSecond = findViewById(R.id.minusSecond)
        imageHome = findViewById(R.id.home)
        imageSetting = findViewById(R.id.setting)
        imageSave = findViewById(R.id.imageSave)
        imageDelete = findViewById(R.id.imageDelete)
        globalTimeTitle = findViewById(R.id.globalTimeTitle)
        appName = findViewById(R.id.appName)
        globalTimer = findViewById(R.id.globalTimer)
        backgroundMain = findViewById(R.id.backgroundMainAct)
        timerViewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)

        plusMinute.setOnClickListener {
            counterMinute++
            text.text = String.format("%02d:%02d", counterMinute, counterSecond)
        }
        minusMinute.setOnClickListener {
            if (counterMinute != 0) {
                counterMinute--
                text.text = String.format("%02d:%02d", counterMinute, counterSecond)
            }
        }

        plusSecond.setOnClickListener {
            counterSecond++
            text.text = String.format("%02d:%02d", counterMinute, counterSecond)
        }
        minusSecond.setOnClickListener {
            if (counterSecond != 0) {
                counterSecond--
                text.text = String.format("%02d:%02d", counterMinute, counterSecond)
            }
        }
        imageHome.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
        imageSetting.setOnClickListener {
            val intent = Intent (this, ActivitySetting::class.java)
            startActivity(intent)
        }
        imageSave.setOnClickListener {
            var stringWithTime : String? = null
            if (counterMinute != 0 && counterSecond == 0) {
                stringWithTime = "$counterMinute мин"
                update(true)
            }
            else if (counterSecond != 0 && counterMinute == 0) {
                stringWithTime = "$counterSecond сек"
                update(true)
            }
            else if (counterSecond != 0 && counterMinute != 0) {
                stringWithTime = "$counterMinute мин $counterSecond сек"
                update(true)
            }
            else {
                Toast.makeText(this, "Сначала добавьте время", Toast.LENGTH_SHORT).show()
            }
            if (stringWithTime != null) {
                timerViewModel.addTimer(stringWithTime)
            }

        }
        imageDelete.setOnClickListener {
            update(false)
        }
        timerViewModel.globalTimer.observe(this) {
            globalTimer.text = timerViewModel.globalTimer.value.toString()
        }
    }
    private fun update (flag : Boolean) {
        counterMinute = 0
        counterSecond = 0
        text.text = String.format("%02d:%02d", counterMinute, counterSecond)
        if (flag) {
            Thread () {
                text.background = getDrawable(R.drawable.style2)
                Thread.sleep(1500)
                text.background = getDrawable(R.drawable.style1)
                Thread.interrupted()
            }.start()
        } else {
            Thread () {
                text.background = getDrawable(R.drawable.style3)
                Thread.sleep(1500)
                text.background = getDrawable(R.drawable.style1)
                Thread.interrupted()
            }.start()
        }
    }
    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
    }

    override fun onResume() {
        super.onResume()
        Thread () {
            timerViewModel.getLocalTime()
        }.start()
        var id = sharedPreferences.getInt("Background", R.drawable.back1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appName.setTextColor(getColor(idTextColor))
            text.setTextColor(getColor(idTextColor))
            globalTimeTitle.setTextColor(getColor(idTextColor))
            globalTimer.setTextColor(getColor(idTextColor))
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT < 19) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else {
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}