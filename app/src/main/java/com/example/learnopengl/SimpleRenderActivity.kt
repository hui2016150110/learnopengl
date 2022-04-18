package com.example.learnopengl

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SimpleRenderActivity : AppCompatActivity() {
    lateinit var drawer: IDrawer
    lateinit var glSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_render)
        drawer = TriangleDrawer()
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