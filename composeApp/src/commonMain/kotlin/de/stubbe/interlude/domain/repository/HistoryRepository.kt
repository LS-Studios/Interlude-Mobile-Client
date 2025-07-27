package de.stubbe.interlude.domain.repository

import de.stubbe.interlude.data.database.dao.ConvertedLinkDao
import de.stubbe.interlude.data.database.dao.HistoryDao
import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.data.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

class HistoryRepository(
    private val historyDao: HistoryDao,
    private val convertedLinkDao: ConvertedLinkDao
) {

    suspend fun addHistory(fromLink: String, convertedLinks: List<ConvertedLinkEntity>) {

        val convertedLinkIds = convertedLinkDao.insertAll(convertedLinks)

        val baseSongEntity = convertedLinkIds.getOrNull(convertedLinks.indexOfFirst { it.url == fromLink }) ?: convertedLinkIds.first()

        historyDao.insert(
            HistoryEntity(
                baseConvertedLinkId = baseSongEntity,
                convertedLinkIds = convertedLinkIds
            )
        )
    }

    fun getAllHistories(): Flow<List<HistoryEntity>> = historyDao.getAllHistories()

    suspend fun getSongsOfHistory(historyId: Long): List<ConvertedLinkEntity> {
        val history = historyDao.getHistoryById(historyId)
        return convertedLinkDao.getConvertedLinksByIds(history.convertedLinkIds)
    }

    suspend fun deleteHistoryItem(historyItemId: Long) {
        val historyItem = historyDao.getHistoryById(historyItemId)
        historyDao.deleteHistoryById(historyItem.id)
        historyItem.convertedLinkIds.forEach { convertedLinkDao.deleteConvertedLinkById(it) }
    }

    suspend fun clearHistory() {
        historyDao.clearAll()
    }

}