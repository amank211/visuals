package com.pie.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.pie.visuals.graph.bar.BarGraph
import com.pie.visuals.graph.LineGraph
import com.pie.visuals.graph.LineGraph.Point
import com.pie.visuals.graph.bar.BarData
import com.pie.visuals.graph.bar.BarData.BarEntry
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var pie : BarGraph
    private val pointList = mutableListOf<BarEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for(i in 1..10) {
            val randomH = Random.nextFloat() * 10 // Random float between 0 and 10
            pointList.add(BarEntry(i.toString(), randomH))
        }
        pie = findViewById(R.id.line)
        pie.setData(BarData(pointList))
    }

    fun loadJSONFromAsset(): String {
        var json = ""
        json = try {
            val `is`: InputStream = assets.open("cords.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return json
        }
        return json
    }
}