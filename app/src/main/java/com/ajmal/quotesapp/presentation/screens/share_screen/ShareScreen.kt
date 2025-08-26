package com.ajmal.quotesapp.presentation.screens.share_screen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ajmal.quotesapp.R
import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.presentation.screens.share_screen.components.CaptureBitmap
import com.ajmal.quotesapp.presentation.theme.GIFont
import com.ajmal.quotesapp.presentation.viewmodel.ShareQuoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareScreen(
    paddingValues: PaddingValues,
    navHost: NavHostController,
    viewModel: ShareQuoteViewModel= hiltViewModel()
) {

    var imgBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()
    var quoteStyleState by remember { mutableStateOf<QuoteStyle>(QuoteStyle.DefaultTheme) }

    LaunchedEffect(Unit) {
        quoteStyleState = viewModel.getDefaultQuoteStyle()
    }
    val quote = navHost.previousBackStackEntry?.savedStateHandle?.get<Quote>("quote")

    Box(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .background(color = Color.Black)
            .fillMaxSize(),
    ) {

        Box(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center)) {

//            Spacer(modifier = Modifier.weight(1f))

            if (quote != null) {
                CaptureBitmap(quoteData = quote,quoteStyleState) { capturedBitmap ->
                    // saving the captured bitmap
                    imgBitmap = capturedBitmap
                }
            } else {
                Log.d("TAG", "ShareScreen: quote is null")
                Toast.makeText(context, "quote is null", Toast.LENGTH_SHORT).show()
            }

//            Spacer(modifier = Modifier.weight(1f))

        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.Black)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {

            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(horizontal = 50.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                Image(
                    painter = painterResource(R.drawable.custom), contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(28.dp)
                        .clickable {
                            showSheet = true
                        })

                Image(
                    painter = painterResource(R.drawable.downloads), contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(28.dp).clickable {

                        imgBitmap?.let {
                            saveImgInGallery(context, it.asAndroidBitmap())
                        } ?: run {
                            Toast.makeText(context, "No image to save", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("TAG", "ShareScreen: imgBitmap is null")
                        }

                    })

                Image(
                    painter = painterResource(R.drawable.share), contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(28.dp)
                        .clickable {
                            imgBitmap?.let {
                                shareImg(context, it.asAndroidBitmap())
                            } ?: run {
                                Toast.makeText(context, "No image to share", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                )

            }

        }

    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {showSheet=false},
            sheetState = sheetState,
            containerColor = Color.LightGray)
        {

            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .verticalScroll(scrollState)
                ,horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Custom Quotes Style",
                    fontSize = 25.sp,
                    fontFamily = GIFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier= Modifier.padding(bottom = 15.dp))


                /**  Christmas STYLE*/
                Column(modifier= Modifier.fillMaxWidth().wrapContentHeight())
                {

                    Text(text = "Christmas Style",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 5.dp),
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )

                    Row(modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()) {
                        Box{
                            Image(painter = painterResource(R.drawable.sample_code_snippet),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clickable{
                                        quoteStyleState = QuoteStyle.CodeSnippetTheme
                                        showSheet=false
                                    },
                                contentScale = ContentScale.Fit)
                            Checkbox(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                checked = quoteStyleState == QuoteStyle.CodeSnippetTheme,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        quoteStyleState = QuoteStyle.CodeSnippetTheme
                                        viewModel.changeDefaultQuoteStyle(quoteStyleState)
                                    }
                                }
                            )
                        }
                    }

                }

                /**  Retro THEME  */
                Column(modifier= Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 10.dp))
                {

                    Text(text = "Retro Theme Style",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Box {
                            Image(
                                painter = painterResource(R.drawable.sample_brat_theme),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                                    .clickable {
                                        quoteStyleState = QuoteStyle.bratTheme
                                        showSheet = false
                                    },
                                contentScale = ContentScale.Fit
                            )
                            Checkbox(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                checked = quoteStyleState == QuoteStyle.bratTheme,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        quoteStyleState = QuoteStyle.bratTheme
                                        viewModel.changeDefaultQuoteStyle(quoteStyleState)
                                    }
                                }
                            )
                        }
                    }

                }

                    /**  Classic  THEME */
                Column(modifier= Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 10.dp))
                {

                    Text(text = "Classic Theme Style",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Box {
                            Image(
                                painter = painterResource(R.drawable.sample_igor),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                                    .clickable {
                                        quoteStyleState = QuoteStyle.igorTheme
                                        showSheet = false
                                    },
                                contentScale = ContentScale.Fit
                            )
                            Checkbox(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                checked = quoteStyleState == QuoteStyle.igorTheme,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        quoteStyleState = QuoteStyle.igorTheme
                                        viewModel.changeDefaultQuoteStyle(quoteStyleState)
                                    }
                                }
                            )
                        }
                    }

                }


                /**  Sports THEME  STYLE */
                Column(modifier= Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 10.dp))
                {

                    Text(text = "Sports Theme Style",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Box {
                            Image(
                                painter = painterResource(R.drawable.sample_spotify_theme),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                                    .clickable {
                                        quoteStyleState = QuoteStyle.SpotifyTheme
                                        showSheet = false
                                    },
                                contentScale = ContentScale.Fit
                            )
                            Checkbox(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                checked = quoteStyleState == QuoteStyle.SpotifyTheme,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        quoteStyleState = QuoteStyle.SpotifyTheme
                                        viewModel.changeDefaultQuoteStyle(quoteStyleState)
                                    }
                                }
                            )
                        }
                    }

                }

                /**  DEFAULT STYLE*/
                Column(modifier= Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 10.dp))
                {

                    Text(text = "Default Style",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontFamily = GIFont,
                        fontWeight = FontWeight.Medium
                    )

                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                        Box {
                            Image(
                                painter = painterResource(R.drawable.sample_default_style),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                                    .clickable {
                                        quoteStyleState = QuoteStyle.DefaultTheme
                                        showSheet = false
                                    },
                                contentScale = ContentScale.Fit
                            )
                            Checkbox(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                checked = quoteStyleState == QuoteStyle.DefaultTheme,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        quoteStyleState = QuoteStyle.DefaultTheme
                                        viewModel.changeDefaultQuoteStyle(quoteStyleState)
                                    }
                                }
                            )
                        }
                    }
                }
            }

        }
    }



}
