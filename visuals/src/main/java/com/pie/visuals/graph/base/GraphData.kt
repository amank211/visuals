package com.pie.visuals.graph.base

abstract class GraphData<T>(protected var items: List<T>) {

    abstract fun getEntries(): List<T>

    fun updateEntries(entries: List<T>) {
        this.items = entries
    }
}