package com.pie.visuals.graph.bar

import com.pie.visuals.graph.base.GraphData

class BarData(items: List<BarEntry>) : GraphData<BarData.BarEntry>(items) {

    data class BarEntry(
        val name : String,
        val height : Float,
        var color: Int = -1
    )

    override fun getEntries(): List<BarEntry> {
        return items
    }
}