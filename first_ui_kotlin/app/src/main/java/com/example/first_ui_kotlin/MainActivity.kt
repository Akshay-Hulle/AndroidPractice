package com.example.first_ui_kotlin

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MainActivity : AppCompatActivity() {

    private lateinit var loadingPB : ProgressBar
    private var progressStetus : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // <-- ADD THIS LINE
        enableEdgeToEdge()

        loadingPB = findViewById(R.id.loadingProgressBar)
        var greetingTextView : TextView = findViewById(R.id.greetingTextView)
        var nameEditText : EditText = findViewById(R.id.nameEditText)
        var greetButton : Button = findViewById(R.id.greetButton)
        var imageView : ImageView = findViewById(R.id.imageView)

        var subscribeCheckBox : CheckBox = findViewById(R.id.subscribeCheckBox)
        var notificationSwitch : Switch = findViewById(R.id.notificationSwitch)
        var favoratColorRGroup : RadioGroup = findViewById(R.id.colorRadioGroup)
        var seekBarLabel : TextView = findViewById(R.id.seekBarLabel)
        var seekBarVolume : SeekBar = findViewById(R.id.volumeSeekBar)
        var countrySpinner : Spinner = findViewById(R.id.countrySpinner)

        var submitButton : Button = findViewById(R.id.submitButton)


        // Setup Spinner (Countries)
        val countries = arrayOf("India","Canada","Germany","USA","UK")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.setAdapter(adapter)

        // Greet Button
        greetButton.setOnClickListener(View.OnClickListener { v: View? ->
            val name: String = nameEditText.getText().toString().trim()
            val greeting = "Hello, $name!"
            greetingTextView.setText(greeting)

            nameEditText.visibility = View.GONE
            greetButton.visibility = View.GONE

            Glide.with(this)
                .load(R.drawable.wellcome)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(imageView)
        })


        // SeekBar Listener
        seekBarVolume.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarLabel.setText("Volume: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

    }
}
