package com.example.whatsapp.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.whatsapp.presentation.navigation.Routes


@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.whatsapp_sticker),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )

            Text(
                text = "Welcome To WhatsApp",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Row {
                Text(
                    text = "Read our",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Privacy policy",
                    color = colorResource(id = R.color.light_green)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Tap 'Agree and Continue' to",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

            }

            Row {

                Text(
                    text = "accept the",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Term and Condition",
                    color = colorResource(id = R.color.light_green)
                )

            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            Button(
                onClick = { navHostController.navigate(Routes.UserRegistrationScreen.route) },
                modifier = Modifier
                    .width(260.dp)
                    .height(43.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_green)
                )
            )
            {
                Text(text = "Agree and Continue", color = Color.White)
            }


        }
    }
}
