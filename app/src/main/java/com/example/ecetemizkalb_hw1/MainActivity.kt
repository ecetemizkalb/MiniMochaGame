package com.example.ecetemizkalb_hw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ecetemizkalb_hw1.cat.Cat
import com.example.ecetemizkalb_hw1.R
import java.util.Collections

class MainActivity : AppCompatActivity() {
    lateinit var tv_title:TextView
    lateinit var sb_mochas:SeekBar
    lateinit var imageMocha:ImageView
    lateinit var et_userName:EditText
    lateinit var btn_choose:Button
    lateinit var cats: ArrayList<Cat>

    var numOfGuess : Int = 0
    var numOfTrueGuess: Int = 0
    var choosenMocha: Int = 3
    var name: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_main)
        prepareData()

        if(name != null){
            Log.d("Eyo","Eyyyyyyyoooo")
            var intent1 = getIntent()
            val b = intent1.getExtras()
            numOfGuess = b!!.getInt("numOfGuess")
            numOfTrueGuess = b.getInt("numOfTrueGuess")
            name = b.getString("name")
            et_userName.setText(name)

        }


        for (item in cats) {
            Log.d("Cats", "Name: ${item.getName()}, Image ID: ${item.getImgId()}")
        }

        @Suppress("DEPRECATION")
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tv_title = findViewById(R.id.tv_title)
        sb_mochas= findViewById(R.id.sb_mochas)
        imageMocha = findViewById(R.id.imageMocha)
        et_userName = findViewById(R.id.et_userName)
        btn_choose = findViewById(R.id.btn_choose)
        imageMocha.setImageResource(R.drawable.mocha3)

        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 300
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        tv_title.startAnimation(anim)

        val imgIds = intArrayOf(R.drawable.mocha1,R.drawable.mocha2,R.drawable.mocha3,R.drawable.mocha4,R.drawable.mocha5)
        sb_mochas.setProgress(1);
        sb_mochas.incrementProgressBy(1);
        sb_mochas.setMax(4);


        var secondActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result  ->

            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val data = result.data
                val receivedData = data!!.getStringExtra("res")
                Toast.makeText(this@MainActivity, receivedData, Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
        }

        var selectedMocha:Cat ?= null
        var choosenMochaindex: Int = 0

        sb_mochas.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //displayToast("$progress")
                val resid = resources.getIdentifier("mocha${progress + 1}", "drawable", packageName)
                imageMocha.setImageResource(resid)
                choosenMocha = resid

                selectedMocha = cats[progress]

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

        })

        btn_choose.setOnClickListener {

            var switchActivityIntent: Intent //create intent in here bc we use it when we clicked the button
            switchActivityIntent = Intent(this@MainActivity, SecondActivity::class.java)
            name = et_userName.text.toString()
            var b = Bundle() //to send datas between activities

            if (et_userName.text.isNullOrBlank()) {
                // EditText is null or empty
                displayToast("Don't be shy tell your name!")
            } else {
                b.putString("name", name)
                b.putInt("choosenMocha", choosenMocha)
                b.putInt("numOfGuess", numOfGuess)
                b.putInt("numOfTrueGuess", numOfTrueGuess)
                b.putParcelable("selectedMocha",selectedMocha)
                //b.putParcelableArrayList("cats", cats)
                switchActivityIntent.putExtras(b)
                //Log.d("MainActivity", "Before launching SecondActivity")
                secondActivityResultLauncher.launch(switchActivityIntent)
                //Log.d("MainActivity", "After launching SecondActivity")
            }

        }
    }

    private fun displayToast(msg:String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun prepareData() {
        cats = ArrayList()
        Collections.addAll<Cat>(
            cats,Cat("Mocha 1", R.drawable.mocha1, 1,"It is Hot in here",R.drawable.mood1), Cat("Mocha 2", R.drawable.mocha2, 2,"Cleany Mochy",R.drawable.mood2), Cat("Mocha 3", R.drawable.mocha3, 3,"Sleepy sleepy",R.drawable.mood3), Cat("Mocha 4", R.drawable.mocha4, 4,"Hunt it hunty",R.drawable.mood4), Cat("Mocha 5", R.drawable.mocha5,5, "He a bit angy",R.drawable.mood5)
        )
    }

}