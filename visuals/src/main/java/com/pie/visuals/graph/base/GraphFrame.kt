package com.pie.visuals.graph.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.pie.visuals.R

abstract class GraphFrame<T>(context: Context, attrs : AttributeSet) : FrameLayout(context, attrs) {

    private lateinit var graphData: GraphData<T>
    protected lateinit var renderer: GraphRender<T>
    private val dimension = GraphDimension(0, 0, 0, 0, 0, 0)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setWillNotDraw(false)
        dimension.width = width
        dimension.height = height
        dimension.paddingBottom = paddingBottom
        dimension.paddingRight = paddingRight
        dimension.paddingTop = paddingTop
        dimension.paddingLeft = paddingLeft
        renderer.initDimension(dimension)
        renderer.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        renderer.onDraw(canvas)
    }

    fun setData(data: GraphData<T>) {
        graphData = data
        renderer.onDataChanged(data)
    }

}