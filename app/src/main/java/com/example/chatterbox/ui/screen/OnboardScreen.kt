package com.example.chatterbox.ui.screen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterbox.R
import com.example.chatterbox.ui.screen.SignInScreen.SignInScreen
import com.example.chatterbox.utils.SignInState

data class OnboardingData(
    val appName: Int = R.drawable.appname,
    val backgroundImage: Int,
    val image: Int,
    val title: String,
    val desc: String
)


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun OnboardScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val items = ArrayList<OnboardingData>()

    items.add(
        OnboardingData(
            backgroundImage = R.drawable.rectangle_1,
            image = R.drawable.ironman,
            title = "Character",
            desc = "유명 캐릭터와의 직접 대화를 진행합니다\n나만의 친구가 이제는 아이언맨, 백설공주 등 \n유명 캐릭터 4명과 함께합니다.",
        )
    )
    items.add(
        OnboardingData(
            backgroundImage = R.drawable.rectangle_2,
            image = R.drawable.princess,
            title = "Dairy",
            desc = "캐릭터의 대화가 간편하고 쉽게\n일기로 기록되고 그날 어떤 기분을 가지고\n있는지 기록이 됩니다.",
        )
    )
    items.add(
        OnboardingData(
            backgroundImage = R.drawable.rectangle_3,
            image = R.drawable.olaf,
            title = "Feeling",
            desc = "오늘의 기분\n이제 캐릭터와 이야기해서 마음을 공유해보세요\n캐릭터가 당신의 마음을 파악해서 공감합니다.",
        )
    )
    items.add(
        OnboardingData(
            backgroundImage = R.drawable.rectangle_4,
            image = R.drawable.genie,
            title = "Information",
            desc = "궁금하고 원하는 것을\n램프의 요정 지니에게 쉽게 물어보세요\nChat-GPT를 통해서 정보를 제공해드립니다.",
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { items.size },
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OnBoardingPager(
            item = items,
            pagerState = pagerState,
        )
        if (pagerState.currentPage == items.size - 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            ) {
                SignInScreen(state, onSignInClick)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager(
    item: List<OnboardingData>,
    pagerState: PagerState,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                    ) {
                        Image(
                            painter = painterResource(id = item[page].backgroundImage),
                            contentDescription = "background",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp, start = 4.dp, end = 4.dp)
                                .width(400.dp)
                                .height(510.dp)
                        )
                        Image(
                            painter = painterResource(id = item[page].appName),
                            contentDescription = "app_name",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 45.dp)
                                .width(100.dp)
                                .height(20.dp)
                        )
                        Image(
                            painter = painterResource(id = item[page].image),
                            contentDescription = item[page].title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = if(page == 0) 130.dp else 130.dp)
                                .width(400.dp)
                                .height(400.dp)
                        )
                    }
                    Text(
                        text = item[page].title,
                        modifier = Modifier
                            .padding(start = 28.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = item[page].desc,
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                    )
                }
            }
            PagerIndicator(item.size, pagerState.currentPage)
        }
    }
}


@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(10.dp)
    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 24.dp else 12.dp, label = "")
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(12.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFF048ABF) else Color.LightGray
            )
    )
}


//@RequiresApi(Build.VERSION_CODES.Q)
//@Preview(showBackground = true)
//@Composable
//fun OnboardScreenPreview() {
//    OnboardScreen()
//}