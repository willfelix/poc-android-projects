package com.example.kotlinfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val a: String = intent.getStringExtra("a")
        val b: String = intent.getStringExtra("b")

        try {
            result_sum.setText("${a.toInt() + b.toInt()}")
        } catch (e: Exception) {
            result_sum.setText("Não foi possivel calcular isso ai")
        }
    }

}
