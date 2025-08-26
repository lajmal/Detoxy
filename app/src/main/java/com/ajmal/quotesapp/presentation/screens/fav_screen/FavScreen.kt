package com.ajmal.quotesapp.presentation.screens.fav_screen

import com.ajmal.quotesapp.R
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ajmal.quotesapp.presentation.screens.fav_screen.util.FavQuoteEvent
import com.ajmal.quotesapp.presentation.screens.fav_screen.util.GlowingTriangle
import com.ajmal.quotesapp.presentation.screens.fav_screen.util.RainbowRays
import com.ajmal.quotesapp.presentation.screens.fav_screen.util.WhiteBeam
import com.ajmal.quotesapp.presentation.theme.GIFont
import com.ajmal.quotesapp.presentation.theme.Grey
import com.ajmal.quotesapp.presentation.viewmodel.FavQuoteViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavScreen(
    paddingValues: PaddingValues,
    navHost: NavHostController,
    quoteViewModel: FavQuoteViewModel = hiltViewModel()
) {
    val state = quoteViewModel.favQuoteState.value

    // Search box animation
    var clickedSearch by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (clickedSearch) 1f else 0f,
        label = "",
        animationSpec = tween(2000)
    )

    // Pull-to-refresh
    val pullRefreshState = rememberPullToRefreshState()
    val willRefresh by remember { derivedStateOf { pullRefreshState.distanceFraction > 1f } }

    // Stack motion
    val cardOffset by animateIntAsState(
        targetValue = when {
            state.isRefreshing -> 250
            pullRefreshState.distanceFraction in 0f..1f -> (250 * pullRefreshState.distanceFraction).roundToInt()
            pullRefreshState.distanceFraction > 1f -> (250 + ((pullRefreshState.distanceFraction - 1f) * .1f) * 100).roundToInt()
            else -> 0
        },
        label = "cardOffset"
    )
    val cardRotation by animateFloatAsState(
        targetValue = when {
            state.isRefreshing || pullRefreshState.distanceFraction > 1f -> 5f
            pullRefreshState.distanceFraction > 0f -> 5 * pullRefreshState.distanceFraction
            else -> 0f
        },
        label = "cardRotation"
    )

    // Haptics
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(willRefresh) {
        when {
            willRefresh -> {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                delay(70)
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                delay(100)
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            !state.isRefreshing && pullRefreshState.distanceFraction > 0f -> {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = state.isRefreshing,
                onRefresh = { /* hook up if needed */ },
                state = pullRefreshState
            )
            .background(Color.Transparent)
    ) {
        // Background (same as Home)
        Image(
            painter = painterResource(id = R.drawable.splash_3), // replace if your Home uses a different bg
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Content
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.error, color = White)
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { value ->
                    quoteViewModel.onEvent(FavQuoteEvent.onSearchQueryChanged(value))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, start = 16.dp, end = 16.dp, top = 10.dp)
                    .onFocusChanged { focusState -> clickedSearch = focusState.isFocused }
                    .animatedBorder({ progress }, White, Color.Black),
                maxLines = 1,
                shape = MaterialTheme.shapes.extraLarge,
                placeholder = { Text(text = "Search...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Grey,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Yellow,
                    focusedTextColor = White,
                ),
                trailingIcon = {
                    WhiteCancelIcon { clickedSearch = false }
                }
            )

            if (state.dataList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(state.dataList) { index, quoteItem ->
                        FavQuoteItem(
                            quoteItem,
                            quoteViewModel,
                            navHost,
                            modifier = Modifier
                                .zIndex((state.dataList.size - index).toFloat())
                                .graphicsLayer {
                                    rotationZ = cardRotation * if (index % 2 == 0) 1 else -1
                                    translationY = (cardOffset * ((5f - (index + 1)) / 5f)).dp
                                        .roundToPx()
                                        .toFloat()
                                }
                        )
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Looks empty...", color = White, fontFamily = GIFont)
                }
            }
        }

        // Pull-to-refresh indicator overlay
        CustomIndicator(state.isRefreshing, pullRefreshState)
    }
}

@Composable
fun WhiteCancelIcon(onClick: () -> Unit) {
    val focusManager = LocalFocusManager.current
    IconButton(onClick = {
        focusManager.clearFocus(force = true)
        onClick()
    }) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Cancel",
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomIndicator(isRefreshing: Boolean, pullRefreshState: PullToRefreshState) {
    val animatedOffset by animateDpAsState(
        targetValue = when {
            isRefreshing -> 200.dp
            pullRefreshState.distanceFraction in 0f..1f -> (pullRefreshState.distanceFraction * 200).dp
            pullRefreshState.distanceFraction > 1f -> (200 + (((pullRefreshState.distanceFraction - 1f) * .1f) * 200)).dp
            else -> 0.dp
        },
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .offset(y = (-200).dp)
            .offset { IntOffset(0, animatedOffset.roundToPx()) }
    ) {
        WhiteBeam(pullRefreshState, isRefreshing)
        RainbowRays(isRefreshing, pullRefreshState)
        GlowingTriangle(pullRefreshState, isRefreshing)
    }
}

/** Animated circular border for search box (keep outside composable) */
fun Modifier.animatedBorder(
    provideProgress: () -> Float,
    colorFocused: Color,
    colorUnfocused: Color
) = this.drawWithCache {
    val width = size.width
    val height = size.height

    val shape = CircleShape
    // Rounded outline to fetch radius for arcs
    val outline = shape.createOutline(size, layoutDirection, this) as Outline.Rounded
    val radius = outline.roundRect.topLeftCornerRadius.x
    val diameter = 2 * radius

    // Clockwise path (top center → right → bottom center)
    val pathCw = Path().apply {
        moveTo(width / 2, 0f)
        lineTo(width - radius, 0f)
        arcTo(Rect(width - diameter, 0f, width, diameter), -90f, 90f, false)
        lineTo(width, height - radius)
        arcTo(Rect(width - diameter, height - diameter, width, height), 0f, 90f, false)
        lineTo(width / 2, height)
    }

    // Counter-clockwise path (top center → left → bottom center)
    val pathCcw = Path().apply {
        moveTo(width / 2, 0f)
        lineTo(radius, 0f)
        arcTo(Rect(0f, 0f, diameter, diameter), -90f, -90f, false)
        lineTo(0f, height - radius)
        arcTo(Rect(0f, height - diameter, diameter, height), 180f, -90f, false)
        lineTo(width / 2, height)
    }

    val pmCw = PathMeasure().apply { setPath(pathCw, false) }
    val pmCcw = PathMeasure().apply { setPath(pathCcw, false) }

    fun DrawScope.drawIndicator(progress: Float, pm: PathMeasure) {
        val seg = Path()
        pm.getSegment(0f, pm.length * EaseOut.transform(progress), seg)
        drawPath(seg, colorFocused, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
    }

    onDrawBehind {
        // Base outline
        drawOutline(outline, colorUnfocused, style = Stroke(width = 2.dp.toPx()))
        // Animated highlights (both directions)
        val p = provideProgress()
        drawIndicator(p, pmCw)
        drawIndicator(p, pmCcw)
    }
}
