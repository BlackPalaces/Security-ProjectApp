package com.example.logmaster_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    var cardView1: CardView? = null
    var cardView2: CardView? = null
    var ScrollView1: ScrollView? = null
    var ButtonQ: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardView1 = findViewById<CardView>(R.id.CardView1)
        cardView2 = findViewById<CardView>(R.id.CardView2)
        ScrollView1 = findViewById<ScrollView>(R.id.ScrollView1)
        ButtonQ = findViewById<Button>(R.id.button2)

        ButtonQ!!.setOnClickListener {
            val intent = Intent(this, LOGMASTER::class.java)
            startActivity(intent)
        }
    }
}
