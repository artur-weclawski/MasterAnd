package com.example.masterand

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.masterand.ui.theme.MasterAndTheme
import java.util.Collections

@Composable
fun CheckCircularButton(
    onClick: () -> Unit,
    color: Color,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .size(50.dp)
            .clickable {
                if (enabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Composable
fun StartOverButton(
    onClick: () -> Unit,
    color: Color,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .width(130.dp)
            .height(40.dp)
            .clickable {
                if (enabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Start over",
            color = Color.White
        )
    }
}


@Composable
fun CrossCircularButton(
    onClick: () -> Unit,
    color: Color,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .size(50.dp)
            .clickable {
                if (enabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun CircularButton(
    onClick: () -> Unit,
    color: Color,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier

) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .size(50.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable {
                if (enabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        //content
    }
}

@Composable
fun SelectableColorsRow(
    colors: List<Color>,
    onClick: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEachIndexed { index, color ->
            CircularButton(onClick = { onClick(index) }, color = color)
        }
    }
}

@Composable
fun SmallCircle(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .size(30.dp)
    )
}

@Composable
fun FeedbackCircles(
    feedbackColors: List<Color>
) {
    val rows = 2
    val columns = 2

    Column(
        modifier = Modifier.width(80.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(rows) { rowIndex ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(80.dp)
            ) {
                repeat(columns) { colIndex ->
                    val index = rowIndex * columns + colIndex
                    SmallCircle(color = feedbackColors.getOrElse(index) { Color.White })
                }
            }
        }
    }
}

@Composable
fun GameRow(
    selectedColors: List<Color>,
    feedbackColors: List<Color>,
    onClick: (Int) -> Unit,
    onCheckClick: () -> Unit,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // SelectableColorsRow
        SelectableColorsRow(colors = selectedColors, onClick = onClick)
        Spacer(modifier = Modifier.width(16.dp))
        // Check Button
        CheckCircularButton(
            onClick = { onCheckClick() },
            color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(16.dp))
        FeedbackCircles(feedbackColors = feedbackColors)
    }
}

@Composable
fun WinMessage(onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Congratulations! You won!",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CrossCircularButton(
            onClick = onRestart,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun GameScreen(navController: NavController, colorNumber: String?) {

    var selectedColorsList by remember { mutableStateOf<List<List<Color>>>(listOf()) }
    var feedbackColorsList by remember { mutableStateOf<List<List<Color>>>(listOf()) }
    var attemptCount by remember { mutableStateOf(0) }
    var rows by remember { mutableStateOf(1) }

    val availableColors =
        remember { mutableListOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan, Color.Magenta, Color.White) }
    val colorAnimation0 = remember { Animatable(availableColors[0]) }
    val colorAnimation1 = remember { Animatable(availableColors[1]) }

    LaunchedEffect(availableColors) {
        colorAnimation0.animateTo(availableColors[1], animationSpec = tween(500))
        colorAnimation1.animateTo(availableColors[2], animationSpec = tween(500))
    }

    var clickable by remember { mutableStateOf(true) }

    val masterSequence by remember { mutableStateOf(selectRandomColors(availableColors, colorNumber)) }
    var showWinMessage by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        item {
            // Result
            Text(
                text = "Attempts: $attemptCount",
                modifier = Modifier.padding(bottom = 16.dp, top = 20.dp),
                fontSize = 36.sp,
            )
        }

        items((rows).coerceAtLeast(1)) { index ->
            val selectedColors = selectedColorsList.getOrNull(index) ?: List(4) { Color.White }
            val feedbackColors = feedbackColorsList.getOrNull(index) ?: List(4) { Color.White }


            GameRow(
                selectedColors = selectedColors,
                feedbackColors = feedbackColors,
                onClick = { buttonIndex ->
                    if (index == attemptCount && clickable) {
                        selectedColorsList = selectedColorsList.toMutableList().apply {
                            if (size <= index) add(List(4) { Color.White })
                            this[index] = selectNextAvailableColor(
                                availableColors,
                                selectedColors,
                                buttonIndex
                            )
                        }
                    }
                },
                onCheckClick = {
                    if (index == attemptCount && clickable) {
                        feedbackColorsList = feedbackColorsList.toMutableList().apply {
                            if (size <= index) add(List(4) { Color.White })
                            this[index] =
                                checkColors(selectedColors, masterSequence, Color.White)
                        }
                        attemptCount++
                        val isWinner =
                            feedbackColorsList.lastOrNull()?.all { it == Color.Red } ?: false
                        if (isWinner) {
                            showWinMessage = true
                        } else {
                            rows++
                        }
                    }
                },
                enabled = attemptCount == index
            )
        }

        item {
            if (showWinMessage) {
                // Start over Button
                StartOverButton(
                    onClick = {
                        selectedColorsList = emptyList()
                        feedbackColorsList = emptyList()
                        attemptCount = 0
                        clickable = true
                        rows = 1
                    },

                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

        }

    }
}

fun selectNextAvailableColor(
    availableColors: List<Color>,
    selectedColors: List<Color>,
    buttonIndex: Int
): List<Color> {
    return if (availableColors.any { it !in selectedColors }) {
        val nextColor = availableColors.first { it !in selectedColors }
        Collections.rotate(availableColors, -1)
        availableColors.toMutableList()
        selectedColors.toMutableList().also { it[buttonIndex] = nextColor }
    } else
        selectedColors.toMutableList().also { it[buttonIndex] = Color.White }
}

fun selectRandomColors(availableColors: List<Color>, colorNumber: String?): List<Color> {
    val number = colorNumber?.toInt()
    return availableColors.shuffled().take(number!!)
}

fun checkColors(
    selectedColors: List<Color>,
    correctColors: List<Color>,
    notFoundColor: Color
): List<Color> {
    val feedback = mutableListOf<Color>()

    for (i in selectedColors.indices) {
        if (selectedColors[i] == correctColors[i]) {
            feedback.add(Color.Red)
        } else if (correctColors.contains(selectedColors[i])) {
            feedback.add(Color.Yellow)
        } else {
            feedback.add(notFoundColor)
        }
    }

    return feedback
}
