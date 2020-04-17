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

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        Configuration.load(this)

        val phase = MoonPhase.calculate(Configuration.instance.phaseAlgorithm, LocalDateTime.now())

        todayTextView.text = "Dzisiaj: ${(phase * 100.0 / 29.0).toInt()}%"
        moonImageView.setImageDrawable(getMoonDrawable(Configuration.instance.hemisphere, phase))

        settingsButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }

        fullMoonButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NextFullMoonActivity::class.java
                )
            )
        }
    }

    private fun getMoonDrawable(hemisphere: Hemisphere, phase: Int): Drawable {
        return getDrawable(
            resources.getIdentifier(
                packageName + ":/drawable/"
                        + (if (hemisphere == Hemisphere.north) "n" else "s") + phase.toString(),
                null, null
            )
        )!!
    }
}
