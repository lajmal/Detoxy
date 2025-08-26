package com.ajmal.quotesapp.presentation.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajmal.quotesapp.presentation.theme.GIFont
import com.ajmal.quotesapp.presentation.viewmodel.QuoteViewModel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun QuoteOfTheDaySection(quoteViewModel: QuoteViewModel) {
    val state = quoteViewModel.quoteState.value
    val quote = state.qot?.quote.orEmpty()
    val author = state.qot?.author.orEmpty()

    val titleBrush = Brush.linearGradient(
        listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp, vertical = 8.dp)
        ) {
            // Title with gradient
            Text(
                text = "Detoxy of the day",
                fontFamily = GIFont,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(brush = titleBrush)
            )

            // Quote (2 lines max, ellipsis)
            if (quote.isNotBlank()) {
                Text(
                    text = quote,
                    fontFamily = GIFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Author (optional)
            if (author.isNotBlank()) {
                Text(
                    text = author,
                    fontFamily = GIFont,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color(0xFFB0BEC5),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
