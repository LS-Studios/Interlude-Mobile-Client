package de.stubbe.interlude.util

import de.stubbe.interlude.model.ConvertedLink

expect fun copySongToClipboard(convertedLink: ConvertedLink, context: Any?)

expect fun shareSong(link: ConvertedLink, context: Any?)