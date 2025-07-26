package de.stubbe.interlude

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.stubbe.interlude.repository.AppShareRepository
import de.stubbe.interlude.view.App
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val repository: AppShareRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        println("DEBUG: onNewIntent called with intent: $intent")

        if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            sharedText?.let { link ->
                repository.setInjectedLink(link)
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}