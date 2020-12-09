package com.ix.ibrahim7.proximitysensor

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() , SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    lateinit var imageView: ImageView
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView =findViewById(R.id.tv_image)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mediaPlayer = MediaPlayer.create(this,R.raw.joker)
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        }else{
            Toast.makeText(this,"not found",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_PROXIMITY){
            val x= event.values[0]

            if (x > 0){
                val objectAnimatorX =ObjectAnimator.ofFloat(imageView,"scaleX",3f).apply {
                    duration = 1000
                }
                val objectAnimatorY =ObjectAnimator.ofFloat(imageView,"scaleY",3f).apply {
                    duration=1000
                }

                val animator = AnimatorSet().apply {
                    play(objectAnimatorX).with(objectAnimatorY)
                }
                animator.start()
            }else{

                val objectAnimatorX =ObjectAnimator.ofFloat(imageView,"scaleX",0.2f).apply {
                    duration = 1000
                }
                val objectAnimatorY =ObjectAnimator.ofFloat(imageView,"scaleY",0.2f).apply {
                    duration=1000
                }

                val animator = AnimatorSet().apply {
                    play(objectAnimatorX).with(objectAnimatorY)
                }
                animator.start().let {
                    mediaPlayer.start()
                }

            }

        }
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}