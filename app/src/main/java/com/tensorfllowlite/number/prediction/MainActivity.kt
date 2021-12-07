package com.tensorfllowlite.number.prediction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {

    var interpreter: Interpreter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            interpreter = loadModelFile()?.let { Interpreter(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mPredictButton.setOnClickListener {
            val f = doInference(mNumberToPredict.text.toString())
            mResultPrediction.text = f.toString() + ""
        }
    }

    private fun doInference(reference: String): Float {
        val input = FloatArray(1)
        input[0] = reference.toFloat()
        val output = Array(1) {
            FloatArray(
                1
            )
        }
        interpreter!!.run(input, output)
        return output[0][0]
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer? {
        val assetFileDescriptor = this.assets.openFd("linear.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val length = assetFileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length)
    }
}