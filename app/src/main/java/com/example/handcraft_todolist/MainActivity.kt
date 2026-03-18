package com.example.handcraft_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEach
import com.example.handcraft_todolist.ui.theme.HandCraft_TodoListTheme
import com.example.handcraft_todolist.ui.util.px
import com.example.handcraft_todolist.ui.util.textPx
import java.time.LocalDate
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandCraft_TodoListTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.mipmap.bg),
                contentScale = ContentScale.Crop
            ),
        containerColor = Color.Transparent,
        topBar = {
            TodoListTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            DateRow()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListTopBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.px()),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            Icon(
                painter = painterResource(R.mipmap.left),
                contentDescription = null,
                modifier = Modifier.size(24.px())
            )
        },
        title = {
            Text(
                text = "今日任务",
                fontSize = 19.textPx(),
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {
            Image(
                painter = painterResource(R.mipmap.notification),
                contentDescription = null,
                modifier = Modifier.size(24.px())
            )
        }
    )
}

data class DayInfo(
    val month: String,
    val dayOfMonth: String,
    val dayOfWeek: String,
    val isToday: Boolean
)

val dayInfoList = buildList {
    val today = LocalDate.now()
    val local = Locale.getDefault()
    for (i in -2 .. 2){//前后共计五天
        val day = today.plusDays(i.toLong())
        val dayInfo = DayInfo(
            month = day.month.getDisplayName(java.time.format.TextStyle.SHORT, local),
            dayOfMonth = String.format(local, "%02d", day.dayOfMonth),
            dayOfWeek = day.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, local),
            isToday = day.equals(today)
        )
        add(dayInfo)
    }
}

@Composable
fun DateRow(){
    Row(
        modifier = Modifier
            .padding(top = 32.px())
            .fillMaxWidth()
            .padding(horizontal = 4.px()),
        horizontalArrangement = Arrangement.spacedBy(12.px()),
        verticalAlignment = Alignment.CenterVertically
    ){
        dayInfoList.fastForEach { date ->
            DateItem(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(15.px()))
                    .background(
                        color = if (date.isToday) Color(0xFF5F33E1) else Color.White
                    )
                    .padding(vertical = 8.px(), horizontal = 20.px()),
                dayInfo = date
            )
        }
    }
}

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    dayInfo: DayInfo
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.px())
    ) {
        Text(
            text = dayInfo.month,
            fontSize = 11.textPx(),
            color = if (dayInfo.isToday) Color.White else Color.Black
        )
        Text(
            text = dayInfo.dayOfMonth,
            fontSize = 19.textPx(),
            fontWeight = FontWeight.SemiBold,
            color = if (dayInfo.isToday) Color.White else Color.Black
        )
        Text(
            text = dayInfo.dayOfWeek,
            fontSize = 11.textPx(),
            color = if (dayInfo.isToday) Color.White else Color.Black
        )
    }
}