package com.pie.visuals.graph

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.pie.visuals.R
import com.pie.visuals.color.ColorPalette
import kotlin.math.ceil
import kotlin.math.max

class BarGraph(context: Context, attrs : AttributeSet) : View(context, attrs) {

    var isMonoChromatic = false
    var monoColor = Color.GRAY
    var textColor = Color.BLACK

    private var heightAnimator = ValueAnimator.ofFloat(0f,0f)
    private var maxHeight = 10f
    private var axisX = 0f
    private var axisXOffset = 20f
    private var axisYOffset = 20f
    private val barPaint = Paint()
    private val axisPaint = Paint()
    private val textPaint = Paint()
    private var barWidth = 100f
    private var barGap = 20f
    private var textGap = 10f
    private var availaibleHeight = 0f
    private var partitions = 5
    private val colorPalette: ColorPalette

    val rect = RectF()

    var bars = listOf(Bar("Dummy", 10f, Color.GRAY))

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Graph,
            0, 0).apply {

            try {
                isMonoChromatic = getBoolean(R.styleable.Graph_monoChromatic, false)
                monoColor = getColor(R.styleable.Graph_monoColor, Color.GRAY)
                textColor = getColor(R.styleable.Graph_textColor, Color.BLACK)
                barWidth = getDimension(R.styleable.Graph_barThickness, 50f)
            } finally {
                recycle()
            }
        }
        colorPalette = ColorPalette(context)
    }

    fun setData(data : List<Bar>) {
        heightAnimator.cancel()
        bars = data
        bars.forEach {
            maxHeight = max(maxHeight, it.height)
            if(it.color == -1) {
                it.color = colorPalette.getColor()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        axisX = height - paddingBottom - axisXOffset
        availaibleHeight = height.toFloat()
        heightAnimator = ValueAnimator.ofFloat(0f, availaibleHeight)
        heightAnimator.addUpdateListener { anim ->
            availaibleHeight = anim.animatedValue as Float
            invalidate()
        }
        heightAnimator.duration = 1200
        heightAnimator.start()
        setPaint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for ((i, j) in bars.withIndex()) {
            drawBar(j, i + 1, canvas)
        }
        drawAxis(canvas)
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(axisYOffset, axisX, width - axisXOffset, axisX, axisPaint)
        val axisY = axisYOffset
        val part = ceil(maxHeight / partitions)
        canvas.drawLine(axisY, axisX, axisY, axisY, axisPaint)
        val multiplier = availaibleHeight / maxHeight * 0.9f
        var height = 0f
        for(i in 1..partitions) {
            height += part
            canvas.drawText("${height.toInt()}", axisYOffset + 30f, axisX - part * i * multiplier + 10f, axisPaint)
            canvas.drawLine(axisYOffset - 10f, axisX - part * i * multiplier,
                axisYOffset + 10f, axisX - part * i * multiplier, axisPaint)
        }
    }

    private fun drawBar(bar : Bar, barStep : Int, canvas: Canvas) {
        val x = axisXOffset + (barWidth + barGap) * barStep
        val startY = axisX
        val endY = startY - bar.height * availaibleHeight / maxHeight * 0.9f
        rect.left = x - barWidth / 2.0f
        rect.right = x + barWidth / 2.0f
        rect.bottom = startY
        rect.top = endY

        barPaint.color = if(isMonoChromatic) monoColor else bar.color
        canvas.drawRoundRect(rect, 10f, 10f, barPaint)
        canvas.drawText(bar.name, x, endY - textGap, textPaint)
    }

    private fun setPaint() {
        barPaint.strokeWidth = barWidth
        axisPaint.color = Color.BLACK
        axisPaint.strokeWidth = 5f

        textPaint.textSize = barWidth * 0.45f
        textPaint.color = Color.GRAY
        textPaint.textAlign = Paint.Align.CENTER

        axisPaint.textSize = 30f
        axisPaint.color = Color.DKGRAY
        axisPaint.textAlign = Paint.Align.LEFT
    }

    data class Bar(
        val name : String,
        val height : Float,
        var color: Int = -1
    )
}