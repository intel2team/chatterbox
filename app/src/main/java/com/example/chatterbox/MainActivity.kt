package com.example.chatterbox

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.User
import com.example.chatterbox.ui.component.BottomNavigationBar
import com.example.chatterbox.ui.navigation.BottomNavItem
import com.example.chatterbox.ui.navigation.Screen
import com.example.chatterbox.ui.screen.ChattingScreen
import com.example.chatterbox.ui.screen.bottomNavScreen.FriendsScreen
import com.example.chatterbox.ui.screen.OnboardScreen
import com.example.chatterbox.ui.screen.ProfileScreen
import com.example.chatterbox.ui.screen.signInScreen.SignInViewModel
import com.example.chatterbox.ui.screen.bottomNavScreen.ChatScreen
import com.example.chatterbox.ui.screen.bottomNavScreen.DetailScreen
import com.example.chatterbox.ui.screen.bottomNavScreen.OthersScreen
import com.example.chatterbox.ui.screen.bottomNavScreen.ShopScreen
import com.example.chatterbox.ui.theme.ChatterboxTheme
import com.example.chatterbox.utils.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
//    private lateinit var mAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatterboxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val db = remember {
                        AppDatabase.getDatabase(this)
                    }
                    val navController = rememberNavController()
                    val startDestination =
                        if (googleAuthUiClient.getSignedInUser() == null) {
                            Screen.Onboard.route
                        } else {
                            BottomNavItem.Friends.route
                        }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(Screen.Onboard.route) {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            LaunchedEffect(state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT)
                                        .show()
                                    val userId = googleAuthUiClient.getSignedInUser()?.userId!!
                                    withContext(Dispatchers.IO) {
                                        db.userDao().insertUser(User(userId))
                                    }
                                    navController.navigate(BottomNavItem.Friends.route) {
                                        popUpTo(Screen.Onboard.route) { inclusive = true }
                                    }
                                    viewModel.resetState()
                                }
                            }
                            OnboardScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                userdata = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "로그아웃 성공",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    navController.navigate(Screen.Onboard.route) {
                                        popUpTo(BottomNavItem.Friends.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Chatting.route + "/{assistantId}") { backStackEntry ->
                            backStackEntry.arguments?.getString("assistantId")
                                ?.let { ChattingScreen(navController, it) }
                        }
                        composable(BottomNavItem.Friends.route) {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                bottomBar = { BottomNavigationBar(navController = navController) }
                            ) { innerPadding ->
                                FriendsScreen(
                                    navController,
                                    googleAuthUiClient.getSignedInUser(),
                                    innerPadding
                                )
                            }
                        }
                        composable(BottomNavItem.Chat.route) {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                bottomBar = { BottomNavigationBar(navController = navController) }
                            ) { innerPadding ->
                                ChatScreen(
                                    navController,
                                    innerPadding
                                )
                            }
                        }
                        composable(BottomNavItem.Shop.route) {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                bottomBar = { BottomNavigationBar(navController = navController) }
                            ) { innerPadding ->
                                ShopScreen(
                                    navController,
                                    innerPadding
                                )
                            }
                        }
                        composable(Screen.Detail.route + "/{itemImage}") { backStackEntry ->
                            backStackEntry.arguments?.getString("itemImage")
                                ?.let { DetailScreen(navController, it) }
                        }
                        composable(BottomNavItem.Others.route) {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                bottomBar = { BottomNavigationBar(navController = navController) }
                            ) { innerPadding ->
                                OthersScreen(
                                    navController,
                                    innerPadding
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}