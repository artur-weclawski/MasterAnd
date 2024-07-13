package com.example.masterand

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreenInitial(navController: NavController, highScore: Int){
    scoreScreen(score = highScore)
}

@Composable
fun LogoutButton(@SuppressLint("ModifierParameter") modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .width(130.dp)
            .height(40.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Logout",
            color = Color.White
        )
    }
}

@Composable
fun RestartGameButton(@SuppressLint("ModifierParameter") modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .width(130.dp)
            .height(40.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Restart Game",
            color = Color.White
        )
    }
}

@Composable
fun recentScore(@SuppressLint("ModifierParameter") modifier: Modifier = Modifier, score: Int){
    Text(
        text = "Recent score: $score",
        style = MaterialTheme.typography.displayMedium,
        color = Color.Black,
        modifier = modifier.padding(25.dp)
    )
}

@Composable
fun scoreScreen(score: Int){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Results", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(40.dp))
        recentScore(score = score)
        RestartGameButton()
        Spacer(modifier = Modifier.height(16.dp))
        LogoutButton()
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdScreenPreview() {
    scoreScreen(score = 5)
}