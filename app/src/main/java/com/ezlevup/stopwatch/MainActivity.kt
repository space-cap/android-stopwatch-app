package com.ezlevup.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.ezlevup.stopwatch.ui.theme.SimpleStopWatchTheme
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleStopWatchTheme {

            }
        }
    }
}

class MainViewModel: ViewModel() {

    private var time = 0
    private var timerTask: Timer? = null

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    private val _sec = mutableStateOf(0)
    val sec: State<Int> = _sec

    private val _milli = mutableStateOf(0)
    val min: State<Int> = _milli



    fun start() {
        _isRunning.value = true
        timerTask = timer(period = 10) {
            time++
            _sec.value = time / 100
            _milli.value = time % 100
        }

    }

    fun pause() {

    }

    fun reset() {

    }

    fun recordLapTime() {

    }
}