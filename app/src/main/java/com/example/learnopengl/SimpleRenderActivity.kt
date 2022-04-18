package com.example.learnopengl

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SimpleRenderActivity : AppCompatActivity() {
    lateinit var drawer: IDrawer
    lateinit var glSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_render)
        drawer = if(intent.getIntExtra("type", 0) == 0){
            TriangleDrawer()
        }else{
            BitmapDrawer(BitmapFactory.decodeResource(resources, R.drawable.cover))
        }
        glSurfaceView = findViewById(R.id.gl_surface)
        initRender()
    }

    private fun initRender(){
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(SimpleRender(drawer))
    }

    override fun onDestroy() {
        drawer.release()
        super.onDestroy()
    }
}