package com.example.masterand

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage

class tempProfile(val login: String, val description: String, val image: String?){
}
@Composable
fun profileCard(navController: NavController, profile: tempProfile, colorNumber: String){
    Row(
        Modifier
            .fillMaxWidth()
            .height(200.dp)){
//            profileImage(profile.image!!)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp, 0.dp, 0.dp)){
            profileNick(profile.login)
            profileDescription(profile.description)
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                previousActivityButton(navController)
                nextActivityButton(navController, colorNumber)
            }
        }

    }
}
@Composable
fun profileNick(login: String){
    Text(text = login, modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp, 0.dp, 5.dp), fontStyle = FontStyle.Normal, fontFamily = FontFamily.Monospace)
}

@Composable
fun profileDescription(description: String){
    Text(text = description, modifier = Modifier
        .height(100.dp)
        .fillMaxWidth())
}
@Composable
fun profileImage(photoUrl: String){
    if(photoUrl != null){
        AsyncImage(model = photoUrl.toUri(), contentDescription = null,
            Modifier
                .size(100.dp)
                .clip(CircleShape)

        )
    }else{
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

}
@Composable
fun nextActivityButton(navController: NavController, colorNumber: String){
    Button(onClick = {navController.navigate(route = "${Screen.Third.route}/${colorNumber}")}, modifier = Modifier
        .width(150.dp)
        .height(50.dp)
        .clip(CircleShape)
    ) {
        Text(text = "Next")
    }
}
@Composable
fun previousActivityButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(route = Screen.First.route) }, modifier = Modifier
            .width(150.dp)
            .height(50.dp)
            .clip(CircleShape)
    ) {
        Text(text = "Back")
    }
}