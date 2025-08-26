package com.ajmal.quotesapp.presentation.screens.home_screen

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
// No longer need .size or .align for the full-screen background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// import androidx.compose.ui.Alignment // Not needed for the full-screen background image itself
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale // Import ContentScale
import androidx.compose.ui.res.painterResource
// import androidx.compose.ui.unit.dp // Not needed for .size(200.dp) anymore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ajmal.quotesapp.R
import com.ajmal.quotesapp.presentation.viewmodel.QuoteViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(paddingValues: PaddingValues,
               navHost: NavHostController,
               quoteViewModel: QuoteViewModel= hiltViewModel()
){

    var isVisible by remember {
        mutableStateOf(false)
    }

    // Consider if this delay is primarily for the image fade-in.
    // If so, it might be better handled by Coil's image loading events for a smoother UX.
    LaunchedEffect(Unit) {
        delay(1000) // Delay for the fade-in effect to start
        isVisible = true
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) // Parent Box, already filling max size
    {
        val painter = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // Ensure your R.drawable.bg is suitable for full-screen display
            rememberAsyncImagePainter(R.drawable.bg)
        } else {
            painterResource(R.drawable.bg)
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 3000)),
            modifier = Modifier.fillMaxSize(), // Make AnimatedVisibility fill the entire screen
            // No need for .align() here as it's filling the whole Box
        ) {
            Image(
                painter = painter,
                contentDescription = "Background image", // Provide a meaningful description or null
                modifier = Modifier.fillMaxSize(), // Make the Image itself fill its parent (AnimatedVisibility)
                contentScale = ContentScale.Crop // Crucial for full-screen display
                // Crop will scale the image to fill the bounds,
                // maintaining aspect ratio, and cropping parts
                // that extend beyond the bounds.
                // Use ContentScale.FillBounds if you want to stretch.
                // Use ContentScale.Fit if you want to see the whole image
                // potentially with letter/pillar boxing.
            )
        }

        // This Column will be overlaid on top of the AnimatedVisibility
        // because the Box parent allows stacking, and AnimatedVisibility is drawn first.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent) // Ensures the background image is visible behind this Column
                .padding(paddingValues)
        ) {
            QuoteOfTheDaySection(quoteViewModel)
            QuoteItemListSection(quoteViewModel, navHost)
        }
    }
}
