package com.example.handcraft_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.util.fastForEachIndexed
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
            CategoryRow()
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

//自定义形状
val CustomShape = GenericShape { size, _ ->
    // width / 22 height / 8
    val (width, height) = size.width to size.height
    moveTo(0f, height / 2) // 移动到左边垂直中心
    // 左上角
    cubicTo(
        0f, height / 8, // 第一个控制点
        0f, 0f, // 第二个控制点
        width / 2, 0f // 终点
    )
    // 右上角
    cubicTo(
        width, 0f, // 第一个控制点
        width, height / 8, // 第二个控制点
        width, height / 2 // 终点
    )
    // 右下角
    cubicTo(
        width, height - height / 8, // 第一个控制点
        width, height, // 第二个控制点
        width / 2, height // 终点
    )
    // 左下角
    cubicTo(
        0f, height, // 第一个控制点
        0f, height - height / 8, // 第二个控制点
        0f, height / 2 // 终点
    )
    close()
}

@Composable
fun CategoryRow(){
    val categories = remember {
        listOf("All", "To do", "In progress", "Completed")
    }
    var currentIndex by remember { mutableIntStateOf(0) }
    Row(
        modifier = Modifier
            .padding(top = 32.px())
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.px())
    ) {
        Spacer(Modifier.width(22.px()))
        categories.fastForEachIndexed { index, category ->
            val isSelected = index == currentIndex
            CategoryItem(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(34.px())
                    .clip(CustomShape)
                    .background(
                        color = if (isSelected) Color(0xFF5F33E1) else Color(0xFFEDE8FF)
                    )
                    .clickable {
                        currentIndex = index
                    }
                    .padding(vertical = 8.px(), horizontal = 24.px()),
                category = category,
                isSelected = isSelected
            )
        }
        Spacer(Modifier.width(22.px()))
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: String,
    isSelected: Boolean
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
       Text(
           text = category,
           fontWeight = if(isSelected) FontWeight.SemiBold else FontWeight.Normal,
           color = if(isSelected) Color.White else Color(0xFF5F33E1)
       )
    }
}