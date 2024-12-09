package com.example.sensorreaderapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var mSensorManager : SensorManager
    private var mAccelerometer : Sensor ?= null
    private var mGyroscope : Sensor ?= null
    private var resume = false;

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && resume) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                findViewById<TextView>(R.id.accelerometer_value).text = event.values[0].toString()+":"+event.values[1].toString()+":"+event.values[2].toString()
            }
            if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                findViewById<TextView>(R.id.gyroscope_value).text = event.values[0].toString()+":"+event.values[1].toString()+":"+event.values[2].toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //not yet implemented
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroscope=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    override fun onResume() {
        super.onResume()
        if (mAccelerometer != null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            findViewById<TextView>(R.id.accelerometer_value).text = "Accelerometer not available"
        }
        if (mGyroscope != null) {
            mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            findViewById<TextView>(R.id.accelerometer_value).text = "Gyroscope not available"
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
    fun resumeReading(view: View) {
        this.resume = true
    }

    fun pauseReading(view: View) {
        this.resume = false
    }
}