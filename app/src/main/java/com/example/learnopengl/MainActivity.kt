package com.example.learnopengl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //绘制三角形
    fun jumpToDrawTriangle(view:View) {
        val intent = Intent(this, SimpleRenderActivity::class.java)
        intent.putExtra("type", 0)
        startActivity(intent)
    }

    //绘制bitmap
    fun jumpToDrawBitmap(view:View) {
        val intent = Intent(this, SimpleRenderActivity::class.java)
        intent.putExtra("type", 1)
        startActivity(intent)
    }
}