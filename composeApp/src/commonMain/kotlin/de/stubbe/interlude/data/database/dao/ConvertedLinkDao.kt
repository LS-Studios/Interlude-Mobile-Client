package de.stubbe.interlude.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConvertedLinkDao: BaseDao<ConvertedLinkEntity> {

    @Query("SELECT * FROM converted_link WHERE id = :id")
    suspend fun getConvertedLinkById(id: Long): ConvertedLinkEntity

    @Query("SELECT * FROM converted_link WHERE id IN (:ids)")
    suspend fun getConvertedLinksByIds(ids: List<Long>): List<ConvertedLinkEntity>

    @Query("SELECT * FROM converted_link WHERE id = :id")
    fun getConvertedLinkByIdWithChange(id: Long): Flow<ConvertedLinkEntity>

    @Query("SELECT * FROM converted_link")
    fun getAllConvertedLinks(): Flow<List<ConvertedLinkEntity>>

    @Query("DELETE FROM converted_link WHERE id = :id")
    suspend fun deleteConvertedLinkById(id: Long)

    @Query("DELETE FROM converted_link")
    suspend fun clearAll()

}