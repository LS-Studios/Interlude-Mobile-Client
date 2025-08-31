package de.stubbe.interlude.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import de.stubbe.interlude.domain.model.ConvertedLink
import java.io.File
import java.net.URL

actual fun shareSong(link: ConvertedLink, context: Any?) {
    val androidContext = (context as? Context) ?: throw IllegalArgumentException("Context must be an instance of Context")

    Thread {
        try {
            var imageUri: android.net.Uri? = null
            if (link.artwork.isNotBlank()) {
                try {
                    val url = URL(link.artwork)
                    val connection = url.openConnection()
                    connection.connect()
                    val input = connection.getInputStream()
                    val file = File(androidContext.cacheDir, "cover.jpg")
                    file.outputStream().use { input.copyTo(it) }
                    
                    imageUri = FileProvider.getUriForFile(
                        androidContext,
                        androidContext.packageName + ".provider",
                        file
                    )
                } catch (e: Exception) {
                    // Artwork download failed, continue without image
                    e.printStackTrace()
                }
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                if (imageUri != null) {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else {
                    type = "text/plain"
                }
                putExtra(Intent.EXTRA_TEXT, "${link.displayName} – ${link.url}")
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