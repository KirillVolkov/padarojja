package com.padarojja.client

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import screen.main.Header
import screen.main.MainList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainList(emptyList()) {
        Header(null, callSearchCity = {

        })
    }
}
