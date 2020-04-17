package com.gulij.moonslice

import android.content.Context
import com.google.gson.Gson
import com.gulij.moonslice.enums.Hemisphere
import com.gulij.moonslice.enums.PhaseAlgorithm
import java.io.File

class Configuration {
    var hemisphere: Hemisphere = Hemisphere.north
    var phaseAlgorithm: PhaseAlgorithm = PhaseAlgorithm.simple

    companion object {
        var instance = Configuration()

        fun save(context: Context) {
            val file = File(context.getExternalFilesDir(null), "config.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            file.writeText(Gson().toJson(instance))
        }

        fun load(context: Context) {
            val file = File(context.getExternalFilesDir(null), "config.json")
            instance = if (file.exists()) {
                Gson().fromJson<Configuration>(file.readText(), Configuration::class.java)
            } else {
                Configuration()
            }
        }
    }
}