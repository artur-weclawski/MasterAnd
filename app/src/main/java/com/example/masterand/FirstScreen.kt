package com.example.masterand

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenInitial(navController: NavController) {

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(initialValue = 1F, targetValue = 1.2F, animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse))

    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val colorNumber = rememberSaveable { mutableStateOf("") }

    val profileImageUri = rememberSaveable{
        mutableStateOf<Uri?>(null)
    }
    var isErrorName by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorEmail by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorColorNumber by rememberSaveable {
        mutableStateOf(false)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            if (selectedUri != null) {
                profileImageUri.value = selectedUri
            }
        })

    fun validateName(text: String){
        if (text.length == 0){
            isErrorName = true
        }
    }

    fun validateEmail(text: String){
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if(text.length == 0){
            isErrorEmail = true
        }
    }
    fun validateColorNumber(text: String){
        if(text.toInt() < 4 || text.toInt() > 10){
            isErrorColorNumber = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 48.dp).graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            }
        )
        Box {
            if(profileImageUri.value != null){
                AsyncImage(model = profileImageUri.value, contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)
            }else {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_question_mark_foreground
                    ),
                    contentDescription = "Profile photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(onClick = {imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))},
                Modifier
                    .align(Alignment.Center)
                    .size(100.dp)) {

                Image(painter = painterResource(id = R.drawable.baseline_image_search_24), contentDescription = "", modifier = Modifier.align(
                    Alignment.TopEnd))
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name.value,
            onValueChange = { name.value = it
                validateName(it)
            },
            label = { Text("Enter Name") },
            singleLine = true,
            isError = isErrorName,
            supportingText = {
                if(isErrorName){
                    Text("Błędne dane")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email.value,
            onValueChange = { email.value = it
                validateEmail(it)
            },
            label = { Text("Enter email") },
            singleLine = true,
            isError = isErrorEmail,
            supportingText = {
                if(isErrorEmail) {
                    Text("Błędne dane")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = colorNumber.value,
            onValueChange = { colorNumber.value = it
                validateColorNumber(it)
            },
            label = { Text("Enter number of colors") },
            singleLine = true,
            isError = isErrorColorNumber,
            supportingText = {
                if(isErrorColorNumber){
                    Text("Błędne dane")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("second_screen/${name.value}/${email.value}/string/${colorNumber.value}")
        }, modifier = Modifier.fillMaxWidth()){
            Text("Next")

        }

    }
}