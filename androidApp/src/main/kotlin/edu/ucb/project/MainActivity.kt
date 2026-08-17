package edu.ucb.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Log.d("CONFIG_TEST", BuildConfig.GLOBAL_PROVIDER)
        Log.d("CONFIG_TEST", BuildConfig.BASE_URL)
        Log.d("CONFIG_TEST", BuildConfig.APP_NAME)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}