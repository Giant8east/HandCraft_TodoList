package com.example.handcraft_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.handcraft_todolist.ui.theme.HandCraft_TodoListTheme
import com.example.handcraft_todolist.ui.util.px
import com.example.handcraft_todolist.ui.util.textPx

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