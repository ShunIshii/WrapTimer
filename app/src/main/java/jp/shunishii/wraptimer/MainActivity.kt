package jp.shunishii.wraptimer

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer
import android.util.Log
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private var soundShort = 0
    //private var wrapTime: Long = 1000
    private lateinit var mCountDownTimer: CountDownTimer
    private val TAG = "Main"
    private var btStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(3)
            .build()

        soundShort = soundPool.load(this, R.raw.sound_short, 1)

        playButton.setOnClickListener{
            if (!btStatus) {
                if (timerStart()) {
                    btStatus = true
                    playButton.text = "Stop"
                }
            }
            else {
                btStatus = false
                playButton.text = "Play"
            }
        }
    }

    private fun timerStart(): Boolean {
        val wrapTimeText = wrapText.text.toString()
        if (wrapTimeText.isEmpty()) {
            wrapText.error = "Input number"
            return false
        }
        else {
            val wrapTime = wrapTimeText.toInt()*1000.toLong()
            Timer().schedule(0, wrapTime) {
                Log.d(TAG, "sound")
                if (btStatus)
                    soundPool.play(soundShort, 1.0f, 1.0f, 0, 0, 1.0f)
                else
                    this.cancel()
            }
            return true
        }
    }
}
