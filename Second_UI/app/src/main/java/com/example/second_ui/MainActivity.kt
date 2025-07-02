package com.example.second_ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import android.animation.ObjectAnimator
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var fl = false
    private var fr = false
    private var rl = false
    private var rr = false
    private var all = false

    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnFL = findViewById<Button>(R.id.btnFL)
        val btnFR = findViewById<Button>(R.id.btnFR)
        val btnRL = findViewById<Button>(R.id.btnRL)
        val btnRR = findViewById<Button>(R.id.btnRR)
        val btnAll = findViewById<Button>(R.id.btnAll)
        txtStatus = findViewById(R.id.doorStetus)

        // Animate the car from left to right
//        val animator = ObjectAnimator.ofFloat(carIcon, "translationX", 0f, 600f)
//        animator.duration = 2000  // Duration of animation in milliseconds
//        animator.start()

        btnFL.setOnClickListener {
            fl = !fl
            updateStatus()
        }

        btnFR.setOnClickListener {
            fr = !fr
            updateStatus()
        }

        btnRL.setOnClickListener {
            rl = !rl
            updateStatus()
        }

        btnRR.setOnClickListener {
            rr = !rr
            updateStatus()
        }

        btnAll.setOnClickListener {
            val newState = !(fl && fr && rl && rr)
            fl = newState
            fr = newState
            rl = newState
            rr = newState
            updateStatus()
        }
    }
    private fun updateStatus() {
        val status = when {
            fl && fr && rl && rr -> "All doors open"
            fl && fr && rl -> "3 doors open (except rear-right)"
            fl && fr && rr -> "3 doors open (except rear-left)"
            fl && rl && rr -> "3 doors open (except front-right)"
            fr && rl && rr -> "3 doors open (except front-left)"
            rl && rr -> "Both rear doors open"
            fr && rr -> "Right-side doors open"
            fr && rl -> "Front-right & Rear-left open"
            fl && rr -> "Front-left & Rear-right open"
            fl && rl -> "Left-side doors open"
            fl && fr -> "Both front doors open"
            rr -> "Rear-right open"
            rl -> "Rear-left open"
            fr -> "Front-right open"
            fl -> "Front-left open"
            else -> "All doors closed"
        }

        txtStatus.text = "Door Status: $status"
    }
}
