package com.ezlevup.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezlevup.stopwatch.ui.theme.SimpleStopWatchTheme
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 앱의 테마를 적용합니다.
            SimpleStopWatchTheme {
                // MainViewModel 인스턴스를 가져옵니다.
                val viewModel = viewModel<MainViewModel>()

                // ViewModel의 상태들을 관찰하여 UI에 반영합니다.
                val sec = viewModel.sec.value
                val milli = viewModel.milli.value
                val isRunning = viewModel.isRunning.value
                val lapTimes = viewModel.lapTimes.value

                // 메인 화면을 표시하고 필요한 상태와 이벤트 핸들러를 전달합니다.
                MainScreen(
                    sec = sec,
                    milli = milli,
                    isRunning = isRunning,
                    lapTimes = lapTimes,
                    onReset = { viewModel.reset() },
                    onToggle = {
                        if (isRunning) viewModel.pause()
                        else viewModel.start()
                    },
                    onLapTime = { viewModel.recordLapTime() }
                )
            }
        }
    }
}

// 스톱워치의 비즈니스 로직과 상태를 관리하는 ViewModel
class MainViewModel : ViewModel() {

    // 1/100초 단위로 시간을 측정하는 변수
    private var time = 0
    // 주기적인 작업을 수행하는 타이머 객체
    private var timerTask: Timer? = null

    // 스톱워치가 현재 실행 중인지 여부를 저장하는 상태
    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    // 화면에 표시될 초(second)
    private val _sec = mutableStateOf(0)
    val sec: State<Int> = _sec

    // 화면에 표시될 밀리초(millisecond)
    private val _milli = mutableStateOf(0)
    val milli: State<Int> = _milli

    // 기록된 랩 타임 목록을 저장하는 상태
    private val _lapTimes = mutableStateOf(emptyList<String>())
    val lapTimes: State<List<String>> = _lapTimes

    // 현재 랩 번호를 추적하는 변수
    private var lap = 1

    // 스톱워치를 시작하는 함수
    fun start() {
        _isRunning.value = true
        // 10ms(0.01초)마다 코드를 실행하는 타이머를 생성합니다.
        timerTask = timer(period = 10) {
            time++
            _sec.value = time / 100 // 100으로 나눈 몫이 초
            _milli.value = time % 100 // 100으로 나눈 나머지가 밀리초
        }
    }

    // 스톱워치를 일시정지하는 함수
    fun pause() {
        _isRunning.value = false
        timerTask?.cancel() // 타이머를 중지합니다.
    }

    // 스톱워치를 초기화하는 함수
    fun reset() {
        timerTask?.cancel() // 타이머를 중지합니다.

        time = 0 // 시간을 0으로 리셋
        _sec.value = 0
        _milli.value = 0
        _isRunning.value = false

        _lapTimes.value = emptyList() // 랩 타임 목록을 비웁니다.
        lap = 1 // 랩 번호를 1로 리셋
    }

    // 현재 시간을 랩 타임으로 기록하는 함수
    fun recordLapTime() {
        // 새로운 랩 타임을 기존 목록의 맨 앞에 추가하여 새 리스트를 만듭니다.
        _lapTimes.value = listOf("$lap LAP : ${sec.value}.${milli.value}") + _lapTimes.value
        lap++
    }
}


// 스톱워치 UI를 구성하는 메인 Composable 함수
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    sec: Int,
    milli: Int,
    isRunning: Boolean,
    lapTimes: List<String>,
    onReset: () -> Unit, // 초기화 버튼 클릭 이벤트
    onToggle: () -> Unit, // 시작/일시정지 버튼 클릭 이벤트
    onLapTime: () -> Unit, // 랩 타임 버튼 클릭 이벤트
) {
    // Material3 Scaffold를 사용하여 기본적인 앱 레이아웃(상단바 등)을 구성합니다.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StopWatch") }
            )
        }
    ) { paddingValues ->
        // 화면 전체를 차지하는 세로 정렬 레이아웃
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // 상단바에 가려지지 않도록 패딩 적용
            horizontalAlignment = Alignment.CenterHorizontally, // 자식 요소들을 가로 방향으로 중앙 정렬
        ) {
            // 상단 여백
            Spacer(modifier = Modifier.height(40.dp))

            // 시간을 표시하는 영역
            Row(
                verticalAlignment = Alignment.Bottom // 세로 방향으로 아래쪽에 정렬
            ) {
                Text("$sec", fontSize = 100.sp) // 초
                Text(".${milli}") // 밀리초
            }
            // 시간과 랩 타임 목록 사이의 여백
            Spacer(modifier = Modifier.height(16.dp))

            // 랩 타임 목록을 표시하는 영역
            Column(
                modifier = Modifier
                    .weight(1f) // 남은 세로 공간을 모두 차지
                    .verticalScroll(rememberScrollState()), // 목록이 길어지면 스크롤 가능하도록 설정
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                lapTimes.forEach { lapTime ->
                    Text(lapTime)
                }
            }

            // 하단 버튼들을 포함하는 영역
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // 버튼들을 좌우로 분산 배치
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 초기화(Reset) 버튼
                FloatingActionButton(
                    onClick = onReset,
                    containerColor = Color.Red
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.refresh_24px),
                        contentDescription = "Reset",
                        colorFilter = ColorFilter.tint(Color.White) // 아이콘 색상을 흰색으로 지정
                    )
                }

                // 시작/일시정지(Start/Pause) 버튼
                FloatingActionButton(
                    onClick = onToggle,
                    // 실행 중일 때는 빨간색, 아닐 때는 초록색으로 배경색 변경
                    containerColor = if (isRunning) Color.Red else Color.Green,
                ) {
                    Image(
                        painter = painterResource(
                            // 실행 중일 때는 '일시정지' 아이콘, 아닐 때는 '시작' 아이콘 표시
                            id = if (isRunning) R.drawable.pause_24px
                            else R.drawable.play_arrow_24px
                        ),
                        contentDescription = "start/pause",
                        colorFilter = ColorFilter.tint(Color.White) // 아이콘 색상을 흰색으로 지정
                    )
                }

                // "랩 타임" 기록 버튼
                Button(onClick = onLapTime) {
                    Text("랩 타임")
                }
            }
        }
    }
}
