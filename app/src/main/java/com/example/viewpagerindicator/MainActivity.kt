package com.example.viewpagerindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.viewpagerindicator.ui.theme.ViewPagerIndicatorTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewPagerIndicatorTheme {
                IntroScreen()
            }
        }
    }
}

data class HorizontalPagerContent(
    val title: String,
    @DrawableRes val res: Int,
    val description: String
)

val getList: List<HorizontalPagerContent> =
    listOf(
        HorizontalPagerContent(
            "First Page",
            R.drawable.firstimage,
            "The oldest classical British and Latin writing had little or no space between words and could be written in boustrophedon (alternating directions). Over time, text direction (left to right) became standardized. Word dividers and terminal punctuation became common"
        ),
        HorizontalPagerContent(
            "Second Page",
            R.drawable.secondimage,
            "Ancient manuscripts also divided sentences into paragraphs with line breaks (newline) followed "
        ),
        HorizontalPagerContent(
            "Third Page",
            R.drawable.thirdimage,
            "Ancient manuscripts also divided sentences into paragraphs with line breaks (newline) followed "
        ),
        HorizontalPagerContent("Fourth Page", R.drawable.fourimage, "Ancient man")
    )


@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroScreen() {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val isNextVisible = remember {
        derivedStateOf {
            pagerState.currentPage != getList.size - 1
        }
    }
    val isPrevVisible = remember {
        derivedStateOf {
            pagerState.currentPage != 0
        }
    }


    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
        ) {

            HorizontalPager(count = getList.size, state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = getList[currentPage].title,
                        style = MaterialTheme.typography.h4,
                        color = Color.Black
                    )
                    AsyncImage(
                        model = getList[currentPage].res,
                        contentDescription = "",
                        modifier = Modifier
                            .height(300.dp)
                            .width(330.dp)
                    )
                    Text(
                        text = getList[currentPage].description,
                        style = MaterialTheme.typography.body2,
                        color = Color.Black
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pageCount = getList.size,
            indicatorWidth = 15.dp,
            indicatorHeight = 15.dp,
            activeColor = Color.Blue,
            inactiveColor = Color.Yellow
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (isPrevVisible.value)
                Button(onClick = {
                    scope.launch { pagerState.currentPage - 1 }
                }) {
                    Text(text = "Prev")
                }

            if (isNextVisible.value)
                Button(onClick = { scope.launch { pagerState.currentPage + 1 } }) {
                    Text(text = "Next")
                }
        }
    }
}


