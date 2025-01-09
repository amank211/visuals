package com.pie.visuals.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.Range
import kotlin.math.hypot

class Axis {

    val start: PointF = PointF()
    val stop: PointF = PointF()

    private var scaleEnabled = false
    private var axisRange = Range(0, 0)
    private var divisions = 1

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
    }

    fun setScaleDivisions(divs: Int) {
        scaleEnabled = true
        axisRange = getDefaultRange()
        divisions = divs
    }

    private fun getDefaultRange() : Range<Int> {
        val axisLen = hypot((stop.x - start.x), (stop.y - start.y)).toInt()
        return Range(0, axisLen)
    }

    fun draw(canvas: Canvas) {
        canvas.drawLine(start.x, start.y, stop.x, stop.y, paint)

        if(scaleEnabled) {

        }
    }

    fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width
    }

    fun setColor(color: Int) {
        paint.color = color
    }

}