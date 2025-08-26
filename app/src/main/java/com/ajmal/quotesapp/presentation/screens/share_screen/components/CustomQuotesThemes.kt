package com.ajmal.quotesapp.presentation.screens.share_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.ajmal.quotesapp.domain.model.Quote
import com.ajmal.quotesapp.presentation.theme.DarkerGrey
import com.ajmal.quotesapp.presentation.theme.Poppins
import com.ajmal.quotesapp.presentation.theme.bratTheme
import com.ajmal.quotesapp.presentation.theme.handWritten
import com.ajmal.quotesapp.R
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.heightIn


/**  THIS SECTION COMPRISES OF ALL DIFFERENT STYLES OF QUOTES */

 /** DEFAULT STYLE */
//@Preview
 @Composable
 fun DefaultQuoteCard(modifier: Modifier, quote: Quote) {
     Box(
         modifier = modifier
             .fillMaxWidth()
             .padding(12.dp)
             .clip(RoundedCornerShape(20.dp)) // keep the rounded shape
     ) {
         // 1) Background image (use your file name here)
         Image(
             painter = painterResource(id = R.drawable.sample_default_style),
             contentDescription = null,
             modifier = Modifier.matchParentSize(),
             contentScale = ContentScale.Crop
         )

         // 2) Optional scrim to improve text contrast
         Box(
             modifier = Modifier
                 .matchParentSize()
                 .background(Color.Black.copy(alpha = 0.25f))
         )

         // 3) Foreground content
         Image(
             painter = painterResource(id = R.drawable.quotation),
             contentDescription = null,
             modifier = Modifier
                 .align(Alignment.TopStart)
                 .padding(horizontal = 15.dp, vertical = 20.dp)
                 .size(25.dp),
             contentScale = ContentScale.Crop
         )

         Column(
             modifier = Modifier
                 .align(Alignment.Center)
                 .fillMaxWidth()
                 .padding(horizontal = 12.dp, vertical = 80.dp),
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Text(
                 text = quote.quote,
                 fontSize = 19.sp,
                 lineHeight = 38.sp,
                 color = Color.White,
                 fontFamily = FontFamily(Font(R.font.glaciaiindifference_regular)),
                 modifier = Modifier
                     .align(Alignment.Start)
                     .padding(top = 12.dp)
             )

             Text(
                 text = quote.author,
                 fontSize = 18.sp,
                 color = DarkerGrey,
                 modifier = Modifier
                     .align(Alignment.Start)
                     .padding(top = 30.dp)
             )
         }

         Text(
             text = "Detoxy Quotes",
             fontSize = 16.sp,
             lineHeight = 32.sp,
             color = Color.White,
             fontFamily = FontFamily(Font(R.font.glaciaiindifference_regular)),
             modifier = Modifier
                 .align(Alignment.BottomCenter)
                 .padding(bottom = 5.dp),
             textAlign = TextAlign.Center
         )
     }
 }


/** CODE SNIPPET STYLE */
@Composable
fun CodeSnippetStyleQuoteCard(modifier: Modifier, quote: Quote) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.sample_code_snippet),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        // Optional scrim
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )

        // Content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = quote.quote,
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 30.sp
            )
            Text(
                text = quote.author,
                color = Color(0xFFFFE082), // warm highlight
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Text(
            text = "Detoxy Quotes",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}
/** SPOTIFY THEME STYLE */
@Composable
fun SolidColorQuoteCard(modifier: Modifier, quote: Quote) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_spotify_theme),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = quote.author,
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            Text(
                text = quote.quote,
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = "Detoxy Quotes",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}



/** brat THEME STYLE */
@Composable
fun BratScreen(modifier: Modifier, quote: Quote) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .heightIn(min = 260.dp) // make the card tall enough
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_brat_theme),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.20f))
        )

        // Use a Column to reserve space for the footer
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Quote gets all available vertical space
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quote.quote,
                    fontSize = 20.sp,           // a bit larger
                    lineHeight = 30.sp,
                    fontFamily = bratTheme,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            // Footer pinned at bottom, away from the quote
            Text(
                text = "Detoxy Quotes",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}



/** IGOR THEME STYLE */
@Composable
fun IgorScreen(modifier: Modifier, quote: Quote) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        // Replace collage with a single classic background
        Image(
            painter = painterResource(id = R.drawable.sample_igor),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.18f))
        )

        Text(
            text = quote.quote,
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = handWritten,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp, vertical = 80.dp)
        )
        Text(
            text = "Detoxy Quotes",
            fontSize = 14.sp,
            color = Color.Black,                  // bratGreen is light; black reads well
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}





