package com.example.timer.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.example.timer.R
import com.example.timer.view_model.MainActivityViewModel
import com.example.timer.view_model.TimerViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    //счетчик времени для сворачивания программы
    private var backPressedTime : Long = 0
    //кнопки с активити для работы со временем
    private lateinit var start : ImageView
    private lateinit var stop : ImageView
    private lateinit var restart : ImageView
    private lateinit var menu : ImageView
    //объекты viewModel
    private lateinit var viewModelMain: MainActivityViewModel
    private lateinit var viewModelTimer: TimerViewModel
    //текстовое поле с активити
    private lateinit var counter : TextView
    private lateinit var appName : TextView
    //адаптер
    private var array: ArrayAdapter<String>? = null
    //список таймеров для выбора нужного
    private lateinit var listView : ListView
    //кнопки с активити для перехода на другие активити
    private lateinit var imageTimer : ImageView
    private  lateinit var imageSetting : ImageView
    lateinit var sharedPreferences : SharedPreferences
    lateinit var backgroundMain : ImageView
    //Работа со звуком:
    lateinit var musicTic : MediaPlayer
    private var flag = true
    private var musicFlag = true
    //SnackBar
    private lateinit var snackbar: Snackbar


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(0, 0)
        ///////////////////////
        viewModelMain = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModelTimer = ViewModelProvider(this)[TimerViewModel::class.java]
        start = findViewById(R.id.start)
        stop = findViewById(R.id.stop)
        restart = findViewById(R.id.restart)
        counter = findViewById(R.id.timerCount)
        imageTimer = findViewById(R.id.add)
        imageSetting = findViewById(R.id.setting)
        listView = findViewById(R.id.listView)
        menu = findViewById(R.id.menu)
        appName = findViewById(R.id.appName)
        backgroundMain = findViewById(R.id.backgroundMainAct)
        ////////////////////////
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)
        musicTic = MediaPlayer.create(this, R.raw.tic2)




        start.setOnClickListener {
            if (viewModelMain.count == 0L || !viewModelMain.flag) {
                viewModelMain.start()
            }
        }
        stop.setOnClickListener {
            viewModelMain.stop()
        }
        restart.setOnClickListener {
            viewModelMain.restart()
        }
        menu.setOnClickListener {
            when (listView.visibility) {
                View.INVISIBLE -> listView.visibility = View.VISIBLE
                View.VISIBLE -> listView.visibility = View.INVISIBLE
            }
        }
        viewModelMain.resultCounter.observe(this) {
            if (musicTic.isPlaying) {
                musicTic.stop()
            }
            if (flag && musicFlag) {
                musicTic.start()
            }
            counter.text = String.format("%02d:%02d:%02d", it / 3600, it / 60 % 60, it % 60)

        }
        viewModelMain.resultTimer.observe(this) {
            if (musicTic.isPlaying) {
                musicTic.stop()
            }
            if (flag && musicFlag) {
                musicTic.start()
            }
            val date = Date(it)
            counter.text =
                String.format("%02d:%02d:%02d", date.hours - 3, date.minutes, date.seconds)
        }
        viewModelTimer.result.observe(this) {
            array = ArrayAdapter(this, R.layout.activity_list1, R.id.text1, it)
            listView.adapter = array
        }
        imageTimer.setOnClickListener {
            if (musicTic.isPlaying) {
                musicTic.stop()
            }
            flag = false
            val intent = Intent(this, Timer::class.java)
            startActivity(intent)
        }
        imageSetting.setOnClickListener {
            if (musicTic.isPlaying) {
                musicTic.stop()
            }
            flag = false
            val intent = Intent(this, ActivitySetting::class.java)
            startActivity(intent)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val stringPosition = listView.getItemAtPosition(position).toString()
            val array: List<String> = stringPosition.split(" ")

            if (stringPosition.contains("мин")) {
                when (array.size) {
                    2 -> {
                        val second: Long = array[0].toLong() * 60
                        viewModelMain.startCountDownTimer(second * 1000)
                    }
                    4 -> {
                        val minute: Long = array[0].toLong() * 60
                        val second: Long = array[2].toLong() + minute
                        viewModelMain.startCountDownTimer(second * 1000)
                    }
                }
            } else if (stringPosition.equals("Секундомер")) {
                viewModelMain.start()
            } else {
                val second: Long = array[0].toLong()
                viewModelMain.startCountDownTimer(second * 1000)
            }
        }
        listView.onItemLongClickListener =
            OnItemLongClickListener { arg0, arg1, pos, id ->
                val a = listView.getItemAtPosition(pos).toString()
                when (a) {
                    "Секундомер", "1 мин", "2 мин", "5 мин", "10 мин", "30 мин" -> {

                    }
                    else -> {
                        viewModelTimer.deleteIntoDb(a)
                        array!!.remove(a)
                        array!!.notifyDataSetChanged()
                    }
                }
                //SnackBar
                snackbar = Snackbar.make(listView[pos], "Удаление элемента", Snackbar.LENGTH_SHORT)
                snackbar.setAction("Отмена") {
                    viewModelTimer.addTimer(a)
                }
                snackbar.show()
                musicFlag = true
                true
            }
    }
    override fun onResume() {
        super.onResume()
        flag = true
        viewModelTimer.setTimerBase()
        viewModelTimer.readTimer()
        var id = sharedPreferences.getInt("Background", R.drawable.back1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appName.setTextColor(getColor(idTextColor))
            counter.setTextColor(getColor(idTextColor))
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
        musicFlag = sharedPreferences.getBoolean("Music", true)
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