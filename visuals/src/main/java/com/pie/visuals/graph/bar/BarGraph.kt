package com.pie.visuals.graph.bar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.pie.visuals.R
import com.pie.visuals.color.ColorPalette

import com.pie.visuals.graph.bar.BarData.BarEntry
import com.pie.visuals.graph.base.GraphFrame

class BarGraph(context: Context, attrs : AttributeSet) : GraphFrame<BarEntry>(context, attrs) {

    init {
        val barRenderer = BarGraphRenderer()
        renderer = barRenderer
        barRenderer.colorPalette = ColorPalette(context)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Graph,
            0, 0).apply {
            try {
                val attributes = BarGraphAttributes()
                attributes.isMonoChromatic = getBoolean(R.styleable.Graph_monoChromatic, false)
                attributes.monoColor = getColor(R.styleable.Graph_monoColor, Color.GRAY)
                attributes.textColor = getColor(R.styleable.Graph_textColor, Color.BLACK)
                attributes.barWidth = getDimension(R.styleable.Graph_barThickness, 50f)

                renderer.initAttributes(attributes)
            } finally {
                recycle()
            }
        }
    }
}