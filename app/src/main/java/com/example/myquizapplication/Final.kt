package com.example.myquizapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Final : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final)

        val tv_userName  =findViewById<TextView>(R.id.tv_userName)
        val tv_finalScore = findViewById<TextView>(R.id.tv_finalScore)
        tv_userName.text= intent.getStringExtra(Constants.USER_NAME)
        tv_finalScore.text = "Your Score ${intent.getStringExtra(Constants.CORRECT_ANSWER)} out of ${intent.getStringExtra(Constants.TOTAL_QUESTION)}"


    }
}