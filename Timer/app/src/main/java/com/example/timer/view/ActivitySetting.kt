package com.example.timer.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.timer.R
import com.google.android.material.snackbar.Snackbar

class ActivitySetting : AppCompatActivity() {

    private var backPressedTime : Long = 0
    //Работа с цветом фона активити:
    private lateinit var backgroundMain : ImageView
    private lateinit var background1 : ImageView
    private lateinit var background2 : ImageView
    private lateinit var background3 : ImageView
    private lateinit var background4 : ImageView
    private lateinit var background5 : ImageView
    private lateinit var background6 : ImageView
    private lateinit var background7 : ImageView
    private lateinit var background8 : ImageView
    private lateinit var background9 : ImageView
    private lateinit var background10 : ImageView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedPreferencesEditor : SharedPreferences.Editor
    //Работа с цветом текста:
    private lateinit var textColor1 : TextView
    private lateinit var textColor2 : TextView
    private lateinit var textColor3 : TextView
    private lateinit var textColor4 : TextView
    private lateinit var textColor5 : TextView
    private lateinit var textColor6 : TextView
    private lateinit var textColor7 : TextView
    private lateinit var textColor8 : TextView
    private lateinit var textColor9 : TextView
    private lateinit var textColor10 : TextView
    //Работа с текстовыми полями активити:
    private lateinit var nameActivity : TextView
    private lateinit var titleBackground : TextView
    private lateinit var titleTextColor : TextView
    private lateinit var textMusic : TextView
    //кнопки с активити для перехода на другие активити
    private lateinit var imageTimer : ImageView
    private  lateinit var imageMain : ImageView
    //ЧекБокс
    private lateinit var checkBoxMusic : CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        overridePendingTransition(0, 0)
        //Инициализация полей по смене цвета активити
        backgroundMain = findViewById(R.id.backgroundMainAct)
        background1 = findViewById(R.id.background1)
        background2 = findViewById(R.id.background2)
        background3 = findViewById(R.id.background3)
        background4 = findViewById(R.id.background4)
        background5 = findViewById(R.id.background5)
        background6 = findViewById(R.id.background6)
        background7 = findViewById(R.id.background7)
        background8 = findViewById(R.id.background8)
        background9 = findViewById(R.id.background9)
        background10 = findViewById(R.id.background10)
        //Инициализация полей по смене цвета текста
        textColor1 = findViewById(R.id.textColor1)
        textColor2 = findViewById(R.id.textColor2)
        textColor3 = findViewById(R.id.textColor3)
        textColor4 = findViewById(R.id.textColor4)
        textColor5 = findViewById(R.id.textColor5)
        textColor6 = findViewById(R.id.textColor6)
        textColor7 = findViewById(R.id.textColor7)
        textColor8 = findViewById(R.id.textColor8)
        textColor9 = findViewById(R.id.textColor9)
        textColor10 = findViewById(R.id.textColor10)
        //Инициализация текстовых полей активити:
        nameActivity = findViewById(R.id.appName)
        titleBackground = findViewById(R.id.titleBackground)
        titleTextColor = findViewById(R.id.titleTextColor)
        imageTimer = findViewById(R.id.add)
        imageMain = findViewById(R.id.home)
        textMusic = findViewById(R.id.textMusic)
        //checkBoxMusic
        checkBoxMusic = findViewById(R.id.checkBoxMusic)

        imageMain.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
        imageTimer.setOnClickListener {
            val intent = Intent (this, Timer::class.java)
            startActivity(intent)
        }
        background1.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back1)
            sharedPreferencesEditor.putInt("Background", R.drawable.back1).apply()
        }
        background2.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back2)
            sharedPreferencesEditor.putInt("Background", R.drawable.back2).apply()
        }
        background3.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back3)
            sharedPreferencesEditor.putInt("Background", R.drawable.back3).apply()
        }
        background4.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back4)
            sharedPreferencesEditor.putInt("Background", R.drawable.back4).apply()
        }
        background5.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back5)
            sharedPreferencesEditor.putInt("Background", R.drawable.back5).apply()
        }
        background6.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back6)
            sharedPreferencesEditor.putInt("Background", R.drawable.back6).apply()
        }
        background7.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back7)
            sharedPreferencesEditor.putInt("Background", R.drawable.back7).apply()
        }
        background8.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back8)
            sharedPreferencesEditor.putInt("Background", R.drawable.back8).apply()
        }
        background9.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back9)
            sharedPreferencesEditor.putInt("Background", R.drawable.back9).apply()
        }
        background10.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.back10)
            sharedPreferencesEditor.putInt("Background", R.drawable.back10).apply()
        }
        textColor1.setOnClickListener() {
            setColorText(R.color.boxFace)
        }
        textColor2.setOnClickListener() {
            setColorText(R.color.nameApp)
        }
        textColor3.setOnClickListener() {
            setColorText(R.color.purple_200)
        }
        textColor4.setOnClickListener() {
            setColorText(R.color.black)
        }
        textColor5.setOnClickListener() {
            setColorText(R.color.textColor1)
        }
        textColor6.setOnClickListener() {
            setColorText(R.color.textColor2)
        }
        textColor7.setOnClickListener() {
            setColorText(R.color.textColor3)
        }
        textColor8.setOnClickListener() {
            setColorText(R.color.textColor4)
        }
        textColor9.setOnClickListener() {
            setColorText(R.color.white)
        }
        textColor10.setOnClickListener() {
            setColorText(R.color.textColor5)
        }
        checkBoxMusic.setOnClickListener {
            if (checkBoxMusic.isChecked) {
                sharedPreferencesEditor.putBoolean("Music", true).apply()
            }else {
                sharedPreferencesEditor.putBoolean("Music", false).apply()

            }
        }
    }
    private fun setColorText(idColor :Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sharedPreferencesEditor.putInt("TextColor", idColor).apply()
            nameActivity.setTextColor(getColor(idColor))
            titleBackground.setTextColor(getColor(idColor))
            titleTextColor.setTextColor(getColor(idColor))
            textMusic.setTextColor(getColor(idColor))
        }
    }
    override fun onResume() {
        super.onResume()
        var id = sharedPreferences.getInt("Background", R.drawable.back1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nameActivity.setTextColor(getColor(idTextColor))
            titleBackground.setTextColor(getColor(idTextColor))
            titleTextColor.setTextColor(getColor(idTextColor))
            textMusic.setTextColor(getColor(idTextColor))
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
        checkBoxMusic.isChecked = sharedPreferences.getBoolean("Music", true)
    }
    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
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