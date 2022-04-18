package com.example.learnopengl

import android.opengl.GLES20
import com.example.learnopengl.IDrawer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class TriangleDrawer : IDrawer {
    //顶点坐标
    private val mVertexCoors = floatArrayOf(
        -1f, -1f,
        1f, -1f,
        0f, 1f
    )

    //纹理坐标
    private val mTextureCoors = floatArrayOf(
        0f, 1f,
        1f, 1f,
        0.5f, 0f
    )

    //纹理ID
    private var mTextureId: Int = -1

    //openGl程序id
    private var mProgram: Int = -1

    //顶点坐标接收者
    private var mVertexPosHandler: Int = -1

    //纹理坐标接收者
    private var mTexturePosHandler: Int = -1

    //将顶点坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
    private lateinit var mVertexBuffer: FloatBuffer

    //将纹理坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
    private lateinit var mTextureBuffer: FloatBuffer

    init {
        //步骤1：初始化顶点坐标
        initPos()
    }

    //
    private fun initPos() {
        val bb = ByteBuffer.allocateDirect(mVertexCoors.size * 4)
        bb.order(ByteOrder.nativeOrder())
        //将顶点坐标数据转换为FloatBuffer，用于传入给OpenGL ES程序
        mVertexBuffer = bb.asFloatBuffer()
        mVertexBuffer.put(mVertexCoors)
        mVertexBuffer.position(0)

        val cc = ByteBuffer.allocateDirect(mTextureCoors.size * 4)
        cc.order(ByteOrder.nativeOrder())
        //将纹理坐标数据转换为FloatBuffer，用于传入给OpenGL ES程序
        mTextureBuffer = cc.asFloatBuffer()
        mTextureBuffer.put(mTextureCoors)
        mTextureBuffer.position(0)

    }

    private fun createGLPrg() {
        if (mProgram == -1) {
            //顶点着色器
            val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, getVertexShader())
            //片元着色器
            val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, getFragmentShader())

            //创建openGL ES程序，注意：需要在openGL渲染线程中创建，否则无法渲染
            mProgram = GLES20.glCreateProgram()
            //将顶点着色器加入到程序
            GLES20.glAttachShader(mProgram, vertexShader)
            //将片元着色器加入到程序
            GLES20.glAttachShader(mProgram, fragmentShader)
            //连接到着色器程序
            GLES20.glLinkProgram(mProgram)

            mVertexPosHandler = GLES20.glGetAttribLocation(mProgram, "aPosition")
            mTexturePosHandler = GLES20.glGetAttribLocation(mProgram, "aCoordinate")

        }
        GLES20.glUseProgram(mProgram)

    }

    private fun getVertexShader(): String {
        return "attribute vec4 aPosition;" +
                "void main() {" +
                "  gl_Position = aPosition;" +
                "}"
    }

    private fun getFragmentShader(): String {
        return "precision mediump float;" +
                "void main() {" +
                "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
                "}"
    }

    private fun loadShader(type: Int, shaderCoder: String): Int {
        //根据type创建顶点着色器或者片元着色器
        val shader = GLES20.glCreateShader(type)
        //将资源加入着色器中，并编译
        GLES20.glShaderSource(shader, shaderCoder)
        GLES20.glCompileShader(shader)
        return shader
    }

    override fun draw() {
        if (mTextureId != -1) {
            createGLPrg()
            doDraw()
        }
    }

    private fun doDraw() {
        GLES20.glEnableVertexAttribArray(mVertexPosHandler)
        GLES20.glEnableVertexAttribArray(mTexturePosHandler)

        GLES20.glVertexAttribPointer(mVertexPosHandler, 2, GLES20.GL_FLOAT, false, 0, mVertexBuffer)
        GLES20.glVertexAttribPointer(
            mTexturePosHandler,
            2,
            GLES20.GL_FLOAT,
            false,
            0,
            mTextureBuffer
        )

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3)
    }

    override fun setTextureID(id: Int) {
        mTextureId = id
    }

    override fun release() {
        GLES20.glDisableVertexAttribArray(mVertexPosHandler)
        GLES20.glDisableVertexAttribArray(mTexturePosHandler)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        GLES20.glDeleteTextures(1, intArrayOf(mTextureId), 0)
        GLES20.glDeleteProgram(mProgram)
    }
}