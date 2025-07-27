package de.stubbe.interlude.network.model

import de.stubbe.interlude.network.model.Error

data class DataError(
    val message: String
): Error