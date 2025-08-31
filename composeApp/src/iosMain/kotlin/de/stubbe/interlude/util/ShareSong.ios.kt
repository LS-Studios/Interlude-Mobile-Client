package de.stubbe.interlude.util

import de.stubbe.interlude.domain.model.ConvertedLink
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIPasteboard
import platform.UIKit.popoverPresentationController

@OptIn(ExperimentalForeignApi::class)
actual fun shareSong(link: ConvertedLink, context: Any?) {
    val text = "${link.displayName} – ${link.url}"
    val activityItems = mutableListOf<Any>()

    if (link.artwork.isNotBlank()) {
        val url = NSURL.URLWithString(link.artwork)
        val data = url?.let { NSData.dataWithContentsOfURL(it) }
        val image = data?.let { UIImage(data = it) }
        image?.let { activityItems.add(it) }
    }
    
    activityItems.add(text)

    val activityVC = UIActivityViewController(
        activityItems = activityItems,
        applicationActivities = null
    )

    activityVC.popoverPresentationController?.sourceRect = CGRectMake(0.0, 0.0, 1.0, 1.0)

    val controller = UIApplication.sharedApplication
        .keyWindow?.rootViewController ?: return

    controller.presentViewController(activityVC, animated = true, completion = null)
}

actual fun copySongToClipboard(convertedLink: ConvertedLink, context: Any?) {
    UIPasteboard.generalPasteboard.string = convertedLink.url
}