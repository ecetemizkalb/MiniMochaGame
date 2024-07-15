package com.example.ecetemizkalb_hw1

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ecetemizkalb_hw1.cat.Cat
import com.example.ecetemizkalb_hw1.third.ThirdActivity
import com.example.ecetemizkalb_hw1.R
import java.util.Collections

class SecondActivity : AppCompatActivity() {

    lateinit var intent2: Intent
    lateinit var adapter:CustomSpinnerAdapter
    lateinit var cats: ArrayList<Cat>
    lateinit var spinnerMoods: Spinner
    lateinit var imgMochaSecond: ImageView
    lateinit var btn_guess: Button
    lateinit var customDialog: Dialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        prepareData()

        createDailog()
        customDialog!!.show()

        intent2 = getIntent()
        val b = intent2.getExtras()
        var name = b!!.getString("name")
        var choosenMocha = b.getInt("choosenMocha")
        var numOfTrueGuess=b.getInt("numOfTrueGuess")
        var numOfGuess = b.getInt("numOfGuess")
        val selectedMocha = intent2.getParcelableExtra<Cat>("selectedMocha")

        spinnerMoods = findViewById(R.id.spinnerMoods)
        imgMochaSecond = findViewById(R.id.imgMochaSecond)
        imgMochaSecond.setImageResource(choosenMocha)
        //Log.d("SecondActivity", "HEEEELLLLOOOOO")
        btn_guess = findViewById(R.id.btn_guess)

        adapter = CustomSpinnerAdapter(this, cats)
        spinnerMoods.adapter = adapter


        var selectedMood: String?= null
        spinnerMoods.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val stemp = cats.get(position)
                val tvName: TextView = view.findViewById(R.id.tvItemCatMood)
                val imItem: ImageView = view.findViewById(R.id.imgItemMood)
                val clayout: ConstraintLayout = view.findViewById(R.id.itemConstraintLayout)
                tvName.text = tvName.text.toString()
                val resid = resources.getIdentifier("mood${position + 1}", "drawable", packageName)
                imItem.setImageResource(resid)
                clayout.setBackgroundColor(Color.rgb(233,247,239))

                selectedMood = tvName.text.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        var thirdActivityIntentLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result  ->

            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val data = result.data
                val msg: String? = data?.getStringExtra("result")

            }
            else
                Toast.makeText(this@SecondActivity, "Error", Toast.LENGTH_SHORT).show()
        }

        var res = "You Guessed Wrong :("
        btn_guess.setOnClickListener {
            numOfGuess += 1
            if (selectedMocha != null) {
                if(selectedMocha.isMoodMatch(selectedMood)){
                    numOfTrueGuess += 1
                    res = "You Guessed Right!"
                }
            }
            var switchtoThirhActivityIntent: Intent
            switchtoThirhActivityIntent = Intent(this@SecondActivity, ThirdActivity::class.java)
            val b = Bundle()
            b.putString("name", name)
            b.putInt("choosenMocha", choosenMocha)
            b.putInt("numOfGuess", numOfGuess)
            b.putInt("numOfTrueGuess", numOfTrueGuess)
            b.putString("res", res)
            switchtoThirhActivityIntent.putExtras(b)
            thirdActivityIntentLauncher.launch(switchtoThirhActivityIntent)
        }
    }

    fun prepareData() {
        cats = ArrayList()
        Collections.addAll<Cat>(
            cats,Cat("Mocha 1", R.drawable.mocha1, 1,"It is Hot in here",R.drawable.mood1), Cat("Mocha 2", R.drawable.mocha2, 2,"Cleany Mochy",R.drawable.mood2), Cat("Mocha 3", R.drawable.mocha3, 3,"Sleepy sleepy",R.drawable.mood3), Cat("Mocha 4", R.drawable.mocha4, 4,"Hunt it hunty",R.drawable.mood4), Cat("Mocha 5", R.drawable.mocha5,5, "He a bit angy",R.drawable.mood5)
        )
    }
    private fun createDailog() {
        customDialog = Dialog(this)
        customDialog.setContentView(R.layout.custom_dialog)

    }
}