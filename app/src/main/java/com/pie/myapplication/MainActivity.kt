package com.pie.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.pie.visuals.graph.LineGraph
import com.pie.visuals.graph.LineGraph.Point
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var pie : LineGraph
    val pointList = mutableListOf<Point>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repeat(50) {
            val randomX = Random.nextFloat() * 10 // Random float between 0 and 10
            val randomY = Random.nextFloat() * 5 // Random float between 0 and 10
            pointList.add(Point(randomX, randomY))
        }
        pie = findViewById(R.id.line)
        pie.setData(pointList)
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