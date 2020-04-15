package com.gulij.moonslice

import android.content.Context
import com.gulij.moonslice.enums.Hemisphere

class Utility {
    companion object {
        fun getMoonResource(context: Context, hemisphere: Hemisphere, phase: Int): Int {
            return context.resources.getIdentifier(
                context.packageName + ":/drawable/"
                        + (if (hemisphere == Hemisphere.north) "n" else "s") + phase.toString(),
                null, null
            )
        }
    }
}