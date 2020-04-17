package com.gulij.moonslice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_next_full_moon.*

class NextFullMoonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_full_moon)

        val viewManager = LinearLayoutManager(this)

        next_full_moons_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = MoonListAdapter(listOf("asdf", "fdsa").toTypedArray())
        }
    }
}
