package com.example.chatterbox

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatterbox.ui.navigation.Screen
import com.example.chatterbox.ui.screen.ChatScreen
import com.example.chatterbox.ui.screen.DiaryScreen
import com.example.chatterbox.ui.screen.MainScreen
import com.example.chatterbox.ui.screen.OnboardScreen
import com.example.chatterbox.ui.screen.ProfileScreen
import com.example.chatterbox.ui.screen.SignInScreen.SignInScreen
import com.example.chatterbox.ui.theme.ChatterboxTheme
import com.example.chatterbox.utils.GoogleAuthUiClient
import com.example.chatterbox.ui.screen.SignInScreen.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
//    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatterboxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination =
                        if (googleAuthUiClient.getSignedInUser() == null) {
                            Screen.SignIn.route
                        } else {
                            Screen.Main.route
                        }

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(Screen.SignIn.route) {
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
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.SignIn.route) { inclusive = true }
                                    }
                                    viewModel.resetState()
                                }

                            }
                            SignInScreen(
                                navController = navController,
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
                                })
                        }
                        composable(Screen.Onboard.route) { OnboardScreen(navController) }
                        composable(Screen.Main.route) { MainScreen(navController) }
                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                navController,
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
                                    navController.navigate(Screen.SignIn.route) {
                                        popUpTo(Screen.Main.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Chat.route + "/{assistantId}") { backStackEntry ->
                            backStackEntry.arguments?.getString("assistantId")
                                ?.let { ChatScreen(navController, it) }
                        }
                        composable(Screen.Diary.route) { DiaryScreen(navController) }
                    }
                }
            }
        }
    }
}
