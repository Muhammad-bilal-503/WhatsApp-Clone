package com.example.whatsapp.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsapp.R
import com.example.whatsapp.presentation.bottomnavigation.BottomNavigationBar
import com.example.whatsapp.presentation.viewmodels.BaseViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navHostController: NavHostController, homeBaseViewModel: BaseViewModel) {

    var showPopup by remember {
        mutableStateOf(false)
    }

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null){
        LaunchedEffect(userId){
            homeBaseViewModel.getChatForUser(userId){chats->


            }
        }
    }

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(size = 65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(size = 28.dp),
                    tint = Color.White
                )

            }

        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) {


    }

}

