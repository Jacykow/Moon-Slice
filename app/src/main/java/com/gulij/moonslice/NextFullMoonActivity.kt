package com.gulij.moonslice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_next_full_moon.*

class NextFullMoonActivity : AppCompatActivity() {

    val dates = Array(12) { it.toString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_full_moon)

        val viewManager = LinearLayoutManager(this)

        checkFullMoonsButton.setOnClickListener {
            for(i in 0..11){
                dates[i] = (12 - i).toString()
            }
            next_full_moons_list.adapter!!.notifyDataSetChanged()
        }

        next_full_moons_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = MoonListAdapter(dates)
        }
    }
}
