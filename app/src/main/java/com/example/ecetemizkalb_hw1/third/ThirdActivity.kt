    package com.example.ecetemizkalb_hw1.third

    import android.annotation.SuppressLint
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.activity.result.ActivityResult
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.appcompat.app.AlertDialog
    import com.example.ecetemizkalb_hw1.MainActivity
    import com.example.ecetemizkalb_hw1.R

    class ThirdActivity : AppCompatActivity() {
        lateinit var intent3: Intent
        lateinit var tv_result : TextView
        lateinit var btn_yes: Button
        lateinit var btn_finish: Button

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_third)
            intent3 = getIntent()
            val b = intent3.getExtras()
            var name = b!!.getString("name")
            var numOfTrueGuess=b.getInt("numOfTrueGuess")
            var numOfGuess = b.getInt("numOfGuess")
            var res = b.getString("res")

            tv_result = findViewById(R.id.tv_result)
            tv_result.setText(res)
            btn_yes = findViewById(R.id.btn_yes)
            btn_finish = findViewById(R.id.btn_finish)

            fun makeAndShowDialog(message: String) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Your Score")
                builder.setMessage(message)
                builder.setPositiveButton("Close") { dialog, which ->
                }
                builder.create()
                builder.show()
            }

            var firstActivityIntentLauncher = registerForActivityResult<Intent, ActivityResult>(
                ActivityResultContracts.StartActivityForResult()) { result  ->

                if (result.resultCode == RESULT_OK) {
                    // There are no request codes
                    val data = result.data
                    val msg: String? = data?.getStringExtra("result")

                }
                else
                    Toast.makeText(this@ThirdActivity, "Error", Toast.LENGTH_SHORT).show()
            }

            btn_finish.setOnClickListener {
                makeAndShowDialog("You guessed $numOfTrueGuess out of $numOfGuess correct!")
            }

            btn_yes.setOnClickListener {
                var firstActivityIntent: Intent
                firstActivityIntent = Intent(this@ThirdActivity, MainActivity::class.java)
                val b = Bundle()
                b.putInt("numOfGuess", numOfGuess)
                b.putInt("numOfTrueGuess", numOfTrueGuess)
                b.putString("name", name)
                firstActivityIntent.putExtras(b)
                firstActivityIntentLauncher.launch(firstActivityIntent)

            }
        }
    }