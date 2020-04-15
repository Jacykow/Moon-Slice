package com.gulij.moonslice

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gulij.moonslice.enums.Hemisphere
import com.gulij.moonslice.enums.PhaseAlgorithm
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onStart() {
        super.onStart()

        hemisphereRadioGroup.check(
            when (Configuration.hemisphere) {
                Hemisphere.north -> northRadioButton.id
                Hemisphere.south -> southRadioButton.id
            }
        )

        hemisphereRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            Configuration.hemisphere = when (checkedId) {
                northRadioButton.id -> Hemisphere.north
                southRadioButton.id -> Hemisphere.south
                else -> Hemisphere.north
            }
            Toast.makeText(applicationContext," On checked change : "+
                    Configuration.hemisphere.toString(), Toast.LENGTH_SHORT).show()
        }

        methodRadioGroup.check(
            when (Configuration.phaseAlgorithm) {
                PhaseAlgorithm.simple -> simpleRadioButton.id
                PhaseAlgorithm.conway -> conwayRadioButton.id
                PhaseAlgorithm.trig1 -> trig1RadioButton.id
                PhaseAlgorithm.trig2 -> trig2RadioButton.id
            }
        )

        methodRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            Configuration.phaseAlgorithm = when (checkedId) {
                simpleRadioButton.id -> PhaseAlgorithm.simple
                conwayRadioButton.id -> PhaseAlgorithm.conway
                trig1RadioButton.id -> PhaseAlgorithm.trig1
                trig2RadioButton.id -> PhaseAlgorithm.trig2
                else -> PhaseAlgorithm.simple
            }
            Toast.makeText(applicationContext," On checked change : "+
                    Configuration.phaseAlgorithm.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
