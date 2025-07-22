package de.stubbe.together.together.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.together.together.data.database.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao: BaseDao<MessageEntity> {

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: Long): MessageEntity

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageByIdWithChange(id: Long): Flow<MessageEntity>

    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteMessageById(id: Long)

    @Query("DELETE FROM messages")
    suspend fun clearAll()

}