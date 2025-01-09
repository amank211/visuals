package com.pie.visuals.graph.base

import android.graphics.Canvas
import androidx.annotation.Dimension
import com.pie.visuals.graph.bar.BarData.BarEntry

abstract class GraphRender<T> {

    protected lateinit var dimens: GraphDimension
    protected lateinit var attributes: GraphAttributes

    abstract fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int)
    abstract fun onDraw(canvas: Canvas)
    abstract fun onDataChanged(data: GraphData<T>)

    fun initDimension(dimension: GraphDimension) {
        dimens = dimension
    }

    fun initAttributes(attrs: GraphAttributes) {
        attributes = attrs
    }
}