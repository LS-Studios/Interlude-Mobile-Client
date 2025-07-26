package de.stubbe.interlude.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import de.stubbe.interlude.model.ConvertedLink
import java.io.File
import java.net.URL

actual fun shareSong(link: ConvertedLink, context: Any?) {
    val androidContext = (context as? Context) ?: throw IllegalArgumentException("Context must be an instance of Context")

    Thread {
        try {
            val url = URL(link.artwork)
            val connection = url.openConnection()
            connection.connect()
            val input = connection.getInputStream()
            val file = File(androidContext.cacheDir, "cover.jpg")
            file.outputStream().use { input.copyTo(it) }

            val uri = FileProvider.getUriForFile(
                androidContext,
                androidContext.packageName + ".provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "${link.displayName} – ${link.url}")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            androidContext.startActivity(Intent.createChooser(shareIntent, "Share with…"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()
}

actual fun copySongToClipboard(convertedLink: ConvertedLink, context: Any?) {
    val androidContext = (context as? Context) ?: throw IllegalArgumentException("Context must be an instance of Context")
    val clipboard = androidContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Song Link", convertedLink.url)
    clipboard.setPrimaryClip(clip)
}