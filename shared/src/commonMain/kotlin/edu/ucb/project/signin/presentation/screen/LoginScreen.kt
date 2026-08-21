/*package edu.ucb.project.presentation.screen

import androidx.compose.runtime.Composable

@Composable
fun LoginScreen() {
    var userSignIn by remember { mutableStateOf("") }
    var passwordSignIn by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In")
        TextField(value = userSignIn, onValueChange = { userSignIn = it })
        TextField(value = passwordSignIn, onValueChange = { passwordSignIn = it })
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Sign In")
        }
    }
}*/

package edu.ucb.project.signin.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    var userSignIn by remember { mutableStateOf("") }
    var passwordSignIn by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In")
        TextField(value = userSignIn, onValueChange = { userSignIn = it })
        TextField(value = passwordSignIn, onValueChange = { passwordSignIn = it })
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Sign In")
        }
    }
}