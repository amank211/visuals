package com.pie.visuals.graph.bar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.pie.visuals.color.ColorPalette
import com.pie.visuals.graph.base.GraphRender
import com.pie.visuals.graph.bar.BarData.BarEntry
import com.pie.visuals.graph.base.GraphData
import com.pie.visuals.utils.Axis
import kotlin.math.ceil
import kotlin.math.max

class BarGraphRenderer : GraphRender<BarEntry>() {

    private val frame = RectF()

    private val axisX = Axis()
    private val axisY = Axis()

    private var maxHeight = 10f
    private var axisXOffset = 20f
    private var axisYOffset = 20f
    private var barGap = 20f
    private var textGap = 10f
    private var availableHeight = 0f
    private var partitions = 5
    private var barData = BarData(listOf(BarEntry("Dummy", 10f, Color.GRAY)))

    private val barPaint = Paint()
    private val axisPaint = Paint()
    private val textPaint = Paint()


    var colorPalette: ColorPalette? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        frame.left = dimens.paddingLeft.toFloat() + axisYOffset
        frame.top = dimens.paddingTop.toFloat() + axisXOffset
        frame.bottom = (dimens.height- dimens.paddingBottom).toFloat()
        frame.right = (dimens.width - dimens.paddingRight).toFloat()

        availableHeight = dimens.height.toFloat()
        setPaint()
    }

    override fun onDraw(canvas: Canvas) {
//        for ((i, j) in barData.getEntries().withIndex()) {
//            drawBar(j, i + 1, canvas)
//        }
        drawAxis(canvas)
    }

    override fun onDataChanged(data: GraphData<BarEntry>) {
        barData = data as BarData
        barData.getEntries().forEach {
            maxHeight = max(maxHeight, it.height)
            if(it.color == -1) {
                it.color = colorPalette?.getColor()?:0
            }
        }
    }

    private fun drawAxis(canvas: Canvas) {

        axisX.start.x = frame.left
        axisX.start.y = frame.bottom
        axisX.stop.x = frame.right
        axisX.stop.y = frame.bottom

        axisX.draw(canvas)

        axisY.start.x = frame.left
        axisY.start.y = frame.bottom
        axisY.stop.x = frame.left
        axisY.stop.y = frame.top

        axisY.draw(canvas)


//        canvas.drawLine(startX, startY, stopX, stopY, axisPaint)
//        val axisY = axisYOffset
//        val part = ceil(maxHeight / partitions)
//        canvas.drawLine(axisY, axisX, axisY, axisY, axisPaint)
//        val multiplier = availableHeight / maxHeight * 0.9f
//        var height = 0f
//        for(i in 1..partitions) {
//            height += part
//            canvas.drawText("${height.toInt()}", axisYOffset + 30f, axisX - part * i * multiplier + 10f, axisPaint)
//            canvas.drawLine(axisYOffset - 10f, axisX - part * i * multiplier,
//                axisYOffset + 10f, axisX - part * i * multiplier, axisPaint)
//        }
    }

    private fun drawBar(bar : BarEntry, barStep : Int, canvas: Canvas) {
//        val attr = attributes as BarGraphAttributes
//        val x = axisXOffset + (attr.barWidth + barGap) * barStep
//        val startY = axisX
//        val endY = startY - bar.height * availableHeight / maxHeight * 0.9f
//        rect.left = x - attr.barWidth / 2.0f
//        rect.right = x + attr.barWidth / 2.0f
//        rect.bottom = startY
//        rect.top = endY
//        val attrs = attributes as BarGraphAttributes
//        barPaint.color = if(attrs.isMonoChromatic) attrs.monoColor else bar.color
//        canvas.drawRoundRect(rect, 10f, 10f, barPaint)
//        canvas.drawText(bar.name, x, endY - textGap, textPaint)
    }

    private fun setPaint() {
        val attr = attributes as BarGraphAttributes

        barPaint.strokeWidth = attr.barWidth
        axisPaint.color = Color.BLACK
        axisPaint.strokeWidth = 5f

        textPaint.textSize = attr.barWidth * 0.45f
        textPaint.color = Color.GRAY
        textPaint.textAlign = Paint.Align.CENTER

        axisPaint.textSize = 30f
        axisPaint.color = Color.DKGRAY
        axisPaint.textAlign = Paint.Align.LEFT
    }
}