package com.example.chatterbox.ui.screen.SignInScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatterbox.R
import com.example.chatterbox.utils.SignInState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient

@Composable
fun SignInScreen(
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .height(55.dp)
                .fillMaxWidth()
                .clickable { onSignInClick() },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 1.5.dp, color = Color.Black),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google icon logo",
                )
                Text(
                    text = "login With Google",
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
        }
    }


//    signInRequest = BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(
//            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                // Your server's client ID, not your Android client ID.
//                .setServerClientId(getString(R.string.your_web_client_id))
//                // Only show accounts previously used to sign in.
//                .setFilterByAuthorizedAccounts(true)
//                .build())
//        .build()
}
