package com.pie.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pie.visuals.graph.BarGraph
import com.pie.visuals.graph.BarGraph.Bar

class MainActivity : AppCompatActivity() {
    lateinit var pie : BarGraph
    val data = listOf(
        Bar("2000", 1f),
        Bar("2001", 2f),
        Bar("2002", 5f),
        Bar("2003", 3f),
        Bar("2004", 2f),
        Bar("2005", 4f),
        Bar("2006", 2f),
        Bar("2007", 5f),
        Bar("2008", 7f),
        Bar("2009", 9f),
        Bar("2010", 8f),
        Bar("2011", 5f),
        Bar("2012", 5f),
        Bar("2013", 5f),
        Bar("2014", 5f),
        Bar("2015", 5f),
        Bar("2016", 5f),
        Bar("2017", 5f),
        Bar("2018", 5f),
        Bar("2019", 5f),
        Bar("2020", 5f),
        Bar("2021", 5f),
        Bar("2022", 5f),
        Bar("2023", 5f),
        Bar("2024", 5f),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pie = findViewById(R.id.line)
        pie.setData(data)
    }
}