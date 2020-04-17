package com.gulij.moonslice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_next_full_moon.*
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter

class NextFullMoonActivity : AppCompatActivity() {

    val dates = Array(12) { it.toString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_full_moon)

        val viewManager = LinearLayoutManager(this)
        updateDates(LocalDateTime.now().year)

        checkFullMoonsButton.setOnClickListener {
            val year = yearEditText.text.toString().toIntOrNull()?.coerceIn(1950, 2100)
            if (year == null) {
                Toast.makeText(this, "Wprowadź rok!", Toast.LENGTH_SHORT).show()
            } else {
                updateDates(year)
            }
        }

        next_full_moons_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = MoonListAdapter(dates)
        }
    }

    private fun updateDates(year: Int){
        yearEditText.setText(year.toString())
        var date = LocalDateTime.now()
        date = date.minusDays((date.dayOfYear - 1).toLong())
        date = date.plusYears((year - date.year).toLong())

        var i = 0
        while (i < 12) {
            if (MoonPhase.calculate(Configuration.instance.phaseAlgorithm, date) == 15) {
                dates[i] = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                i++
            }
            date = date.plusDays(1)
        }

        next_full_moons_list.adapter!!.notifyDataSetChanged()
    }
}
