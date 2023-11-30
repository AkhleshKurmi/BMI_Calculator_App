package com.example.bmi_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi_calculator.databinding.ActivityBmiChartBinding


class BmiChart : AppCompatActivity() {

      lateinit var bindingBmiChart : ActivityBmiChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingBmiChart = ActivityBmiChartBinding.inflate(layoutInflater)
        setContentView(bindingBmiChart.root)

        bindingBmiChart.imageView.setImageResource(R.drawable.bmi_chart)
    }
}