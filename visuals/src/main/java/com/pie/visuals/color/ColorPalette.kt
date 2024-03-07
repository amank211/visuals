package com.pie.visuals.color

import android.content.Context
import com.pie.visuals.R

class ColorPalette(context: Context) {
    val colors : List<Int>
    init {
        context.resources.obtainTypedArray(R.array.colors).apply {
            val list = mutableListOf<Int>()
            for(i in 0..<length()) {
                val color = getColor(i, 0)
                list.add(color)
            }
            colors = list.toList()
            recycle()
        }
    }

    fun getColor() = colors.random()
}