package com.ajmal.quotesapp.presentation.screens.settings_screen

import com.ajmal.quotesapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajmal.quotesapp.presentation.screens.settings_screen.components.CardSection
import com.ajmal.quotesapp.presentation.screens.settings_screen.components.cardsRow
import com.ajmal.quotesapp.presentation.theme.GIFont
import com.ajmal.quotesapp.presentation.theme.Poppins

@Composable
fun SettingsScreen(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        // Background image (same as Home/Fav)
        Image(
            painter = painterResource(id = R.drawable.splash_3),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground content
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Settings",
                fontFamily = GIFont,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp),
                color = Color.White,
                fontSize = 35.sp
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                item {
                    Text(
                        text = "Socials",
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                        fontSize = 20.sp,
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )
                }
                items(cardsRow.size) { cardRowIndex ->
                    CardSection(index = cardRowIndex)
                }
            }

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "made with 🥵 by Mohamed Ajmal",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
