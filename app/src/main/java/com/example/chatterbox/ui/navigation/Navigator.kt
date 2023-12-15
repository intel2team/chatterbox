package com.example.chatterbox.ui.navigation

//
//@Composable
//fun Navigator() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.Main.route) {
//        composable(Screen.Onboard.route) { OnboardScreen(navController) }
//        composable(Screen.SignIn.route) {
//            val viewModel = viewModel<SignInViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
//            val launcher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.StartIntentSenderForResult(),
//                onResult = { result ->
//                    if(result.resultCode == RESULT_OK) {
//                         lifecycleScope.launch {
//                             val signInResult = googleAuthUiClient.signInWithIntent(
//                                 intent = result.data ?: return@launch
//                             )
//                             viewModel.onSignInResult(signInResult)
//                         }
//                    }
//                }
//            )
//        }
//        composable(Screen.Main.route) { MainScreen(navController) }
//        composable(Screen.Chat.route) { ChatScreen(navController) }
//        composable(Screen.Diary.route) { DiaryScreen(navController) }
//    }
//}
//
