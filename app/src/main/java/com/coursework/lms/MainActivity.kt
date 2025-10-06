package com.coursework.lms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.coursework.corePresentation.navigation.LocalNavControllersHolder
import com.coursework.corePresentation.navigation.NavControllersHolder
import com.coursework.lms.ui.theme.MyApplicationTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    val navControllersHolder = get<NavControllersHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(
                    LocalNavControllersHolder provides navControllersHolder
                ) {
                    RootNavigation()
                }
            }
        }
    }
}
