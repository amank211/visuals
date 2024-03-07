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
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

class Pie(context: Context, attrs : AttributeSet) : View(context, attrs) {

    var startAngle = 270f
    var dividerSize = 5f
    var completeAngle = 360f
    var isMonoChromatic = false
    var monoColor = Color.GRAY
    var showPercentage = true

    private var radius : Float = 0f
    private var centerX : Float = 0f
    private var centerY : Float = 0f
    private val paint = Paint()
    private val textPaint = Paint()
    private val textPerPaint = Paint()
    private val dividerPaint = Paint()
    private var textColor = 0
    private var textSize = 0f
    private val rectF = RectF()
    private var totalWeight = 1f
    private val angleValueAnimator = ValueAnimator.ofFloat(0f, completeAngle)
    private var slices : List<Slice> = listOf(Slice("Fill", 100.0f, Color.GRAY))

    private val colorPalette : ColorPalette

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Graph,
            0, 0).apply {

            try {
                showPercentage = getBoolean(R.styleable.Graph_showPercentage, true)
                isMonoChromatic = getBoolean(R.styleable.Graph_monoChromatic, false)
                monoColor = getColor(R.styleable.Graph_monoColor, Color.GRAY)
                textColor = getColor(R.styleable.Graph_textColor, Color.BLACK)
                textSize = getDimension(R.styleable.Graph_textSize, -1f)
            } finally {
                recycle()
            }
        }
        colorPalette = ColorPalette(context)
    }

    fun setData(data : List<Slice>) {
        slices = data
        totalWeight = 0.0f
        slices.forEach {
            totalWeight+= it.weight
            if(it.color == -1) {
                it.color = colorPalette.getColor()
            }
        }
        angleValueAnimator.duration = 1300
        angleValueAnimator.addUpdateListener { anim ->
            completeAngle = anim.animatedValue as Float
            invalidate()
        }
        angleValueAnimator.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        radius = min(height, width) / 2.0f - paddingEnd
        centerX = width / 2.0f
        centerY = height / 2.0f
        rectF.left = centerX - radius
        rectF.top = centerY - radius
        rectF.right = centerX + radius
        rectF.bottom = centerY + radius
        setPaint()
    }

    private fun setPaint() {
        paint.color = Color.RED
        // text paint
        textPaint.color = textColor
        println("size = $textSize")
        textPaint.textSize = if(textSize <= 0) radius * 0.09f else textSize
        textPaint.textAlign = Paint.Align.CENTER
        textPerPaint.color = textColor
        textPerPaint.textAlign = Paint.Align.CENTER
        textPerPaint.textSize = textPaint.textSize / 1.4f
        // divider Paint
        dividerPaint.color = Color.WHITE
        dividerPaint.strokeWidth = dividerSize
    }

    override fun onDraw(canvas: Canvas) {
        var angle = startAngle
        for(slice in slices) {
            val sweep = (slice.weight / totalWeight) * completeAngle
            drawSlice(slice, angle, sweep, canvas)
            angle += sweep
        }
        angle = 0f
        if(slices.size > 1) {
            for (slice in slices) {
                val sweep = (slice.weight / totalWeight) * completeAngle
                drawDivider(slice, angle, sweep, canvas)
                angle += sweep
            }
        }
        super.onDraw(canvas)
    }

    private fun drawSlice(slice : Slice, startAngle : Float, sweep: Float, canvas: Canvas) {
        paint.color = if(isMonoChromatic) monoColor else slice.color
        canvas.drawArc(rectF, startAngle, sweep, true, paint)
        drawText(slice, startAngle, sweep, canvas)
    }

    private fun drawText(slice : Slice, startAngle : Float, sweep: Float, canvas: Canvas) {
        val midAngle = (startAngle + sweep / 2.0f)
        val textAngle = (Math.PI / 180f).toFloat() * midAngle
        val radialDistance = radius / 1.5f
        val textX = centerX + radialDistance * cos(textAngle)
        val textY = centerY + radialDistance * sin(textAngle)
        canvas.drawText(slice.name, textX, textY, textPaint)
        if(showPercentage) {
            var percentage = (slice.weight / totalWeight * 100f)
            percentage = (percentage * 100f).roundToInt() / 100f
            val perString = "$percentage%"
            canvas.drawText(perString, textX, textY + textPaint.textSize, textPerPaint)
        }
    }

    private fun drawDivider(slice : Slice, startAngle: Float, sweep: Float, canvas: Canvas) {
        val angle = (Math.PI / 180f).toFloat() * startAngle
        val endX = centerX + radius * sin(angle)
        val endY = centerY - radius * cos(angle)
        canvas.drawLine(centerX, centerY, endX, endY, dividerPaint)
    }

    data class Slice(
        val name : String,
        val weight : Float,
        var color: Int = -1
    )
}