package com.example.whatsapp.presentation.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R
import com.example.whatsapp.presentation.bottomnavigation.BottomNavigationBar

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun UpdateScreen() {

    val scrollState = rememberScrollState()

    val sampleStatus = listOf(
        StatusData( R.drawable.shahyan, name = "Shayan Ahmad", time = "Just Now"),
        StatusData( R.drawable.salleh, name = "Saleh Hayat", time = "10 min ago"),
        StatusData( R.drawable.haider, name = "Haider Ali", time = "1 hour ago"),
        StatusData( R.drawable.mrbeast, name = "Mr Beast", time = "10 hours ago"),
    )


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(size = 60.dp),
                contentColor = Color.White
            ) {
                Icon(

                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = null,
                    modifier = Modifier.size(size = 28.dp)
                )

            }


        },

        bottomBar = {
            BottomNavigationBar()
        },

        topBar = {
            TopBar()
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )



            MyStatus()

            sampleStatus.forEach {
                StatusItem(statusData = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Color.Gray)




        }
    }

}