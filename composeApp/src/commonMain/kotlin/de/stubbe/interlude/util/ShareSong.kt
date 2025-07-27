package de.stubbe.interlude.util

import de.stubbe.interlude.domain.model.ConvertedLink

expect fun copySongToClipboard(convertedLink: ConvertedLink, context: Any?)

expect fun shareSong(link: ConvertedLink, context: Any?)