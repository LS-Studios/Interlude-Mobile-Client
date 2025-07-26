package de.stubbe.interlude.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.interlude.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao: BaseDao<HistoryEntity> {

    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getHistoryById(id: Long): HistoryEntity

    @Query("SELECT * FROM history WHERE id = :id")
    fun getHistoryByIdWithChange(id: Long): Flow<HistoryEntity>

    @Query("SELECT * FROM history")
    fun getAllHistories(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistoryById(id: Long)

    @Query("DELETE FROM history")
    suspend fun clearAll()

}