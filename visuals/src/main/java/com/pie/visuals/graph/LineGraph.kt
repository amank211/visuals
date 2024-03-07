package com.pie.visuals.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.pie.visuals.R
import kotlin.math.ceil
import kotlin.math.max

class LineGraph(context: Context, attrs : AttributeSet) : View(context, attrs) {
    var textColor = Color.BLACK

    private val origin = Point(0f, 0f)
    private var maxHeight = 0f
    private var maxWidth = 0f
    private var axisX = 0f
    private var axisXOffset = 20f
    private var axisYOffset = 20f
    private val pointPaint = Paint()
    private val linePaint = Paint()
    private val axisPaint = Paint()
    private val textPaint = Paint()
    private var availableHeight = 0f
    private var availableWidth = 0f
    private var partitions = 5
    private var points = listOf<Point>()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Graph,
            0, 0).apply {

            try {
                textColor = getColor(R.styleable.Graph_textColor, Color.BLACK)
            } finally {
                recycle()
            }
        }
        setPaint()
    }

    fun setData(data : List<Point>) {
        points = data.sortedBy { it.x }
        points.forEach {
            maxHeight = max(maxHeight, it.y)
            maxWidth = max(maxWidth, it.x)
        }
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        axisXOffset = width.toFloat() * 0.01f
        axisYOffset = height.toFloat() * 0.01f

        axisX = height - paddingBottom - axisXOffset
        availableHeight = height.toFloat()
        availableWidth = width.toFloat()
        origin.x = paddingLeft + axisXOffset
        origin.y = height - paddingBottom - axisYOffset
        val multiplierY = availableHeight / maxHeight * 0.9f
        val multiplierX =  availableWidth / maxWidth * 0.9f
        points.forEach {
            it.x = origin.x + it.x * multiplierX
            it.y = origin.y - it.y * multiplierY
        }
        for(point in points) {
            println("${point.x}, ${point.y}")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var prv = origin
        points.forEach {
            drawLine(prv, it, canvas)
            prv = it
        }
        points.forEach {
            drawPoint(it, canvas)
        }
        drawAxis(canvas)
    }

    private fun drawPoint(point: Point, canvas: Canvas) {
        canvas.drawCircle(point.x, point.y, 4f, pointPaint)
    }

    private fun drawLine(prvPoint: Point, point: Point, canvas: Canvas) {
        canvas.drawLine(prvPoint.x, prvPoint.y, point.x, point.y, linePaint)
    }

    private fun drawAxis(canvas: Canvas) {
        val partY = ceil(maxHeight / partitions)
        val partX = ceil(maxWidth / partitions)
        val multiplierY = availableHeight / maxHeight * 0.9f
        val multiplierX =  availableWidth / maxWidth * 0.9f
        var height = 0f
        var width = 0f
        canvas.apply {
            drawLine(origin.x, origin.y, this.width - axisXOffset - paddingRight, origin.y, axisPaint)
            drawLine(origin.x, origin.y, origin.x, paddingTop + axisYOffset, axisPaint)
            for(i in 1..partitions) {
                height += partY
                width += partX
                drawLine(origin.x - 10f, origin.y - partY * i * multiplierY,
                    origin.x + 10f, origin.y - partY * i * multiplierY, axisPaint)
                drawText("${height.toInt()}", origin.x + 30f,
                    origin.y - partY * i * multiplierY + 10f, textPaint)
                drawLine(origin.x + partX * i * multiplierX, origin.y - 10f,
                    origin.x + partX * i * multiplierX, origin.y + 10f, axisPaint)
                drawText("${width.toInt()}", origin.x + partX * i * multiplierX,
                    origin.y - 30f, textPaint)
            }
        }

    }

    private fun setPaint() {
        pointPaint.color = Color.RED

        axisPaint.color = Color.BLACK
        axisPaint.strokeWidth = 5f

        textPaint.textSize = 35f
        textPaint.color = Color.GRAY
        textPaint.textAlign = Paint.Align.CENTER

        axisPaint.textSize = 35f
        axisPaint.color = Color.DKGRAY
        axisPaint.textAlign = Paint.Align.LEFT

        linePaint.color = Color.GRAY
        linePaint.strokeWidth = 5f
    }

    data class Point(
        var x : Float,
        var y : Float
    )
}