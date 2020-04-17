package com.gulij.moonslice

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gulij.moonslice.enums.Hemisphere
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        previousNewMoon.text = "Poprzedni nów: ${getPreviousNewMoonDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"
        nextFullMoon.text = "Następna pełnia: ${getNextFullMoonDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"

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

    private fun getPreviousNewMoonDate(): LocalDateTime {
        var date = LocalDateTime.now()
        while (MoonPhase.calculate(Configuration.instance.phaseAlgorithm, date) != 0) {
            date = date.minusDays(1)
        }
        return date
    }

    private fun getNextFullMoonDate(): LocalDateTime {
        var date = LocalDateTime.now()
        while (MoonPhase.calculate(Configuration.instance.phaseAlgorithm, date) != 15) {
            date = date.plusDays(1)
        }
        return date
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
