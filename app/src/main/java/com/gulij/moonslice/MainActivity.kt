package com.gulij.moonslice

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gulij.moonslice.enums.Hemisphere
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        Configuration.load(this)

        val phase = MoonPhase.calculate(Configuration.instance.phaseAlgorithm, LocalDateTime.now()).toInt()

        todayTextView.text = "Dzisiaj: $phase%"
        moonImageView.setImageDrawable(getMoonDrawable(Configuration.instance.hemisphere, phase))

        settingsButton.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }

    private fun getMoonDrawable(hemisphere: Hemisphere, phase: Int): Drawable {
        return getDrawable(resources.getIdentifier(
            packageName + ":/drawable/"
                    + (if (hemisphere == Hemisphere.north) "n" else "s") + phase.toString(),
            null, null
        ))!!
    }
}
