package com.App.healtcare.data.local.dao.question

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM vocabularyEntity")
    fun getVocabulary(): Flow<List<VocabularyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: VocabularyEntity)

    @Update
    suspend fun updateWord(word: VocabularyEntity)

    @Query("SELECT * FROM vocabularyEntity WHERE id In (:word)")
    fun getWordById(word: Set<Int>): Flow<List<VocabularyEntity>>
    @Query("DELETE FROM vocabularyEntity WHERE id = :word")
    suspend fun deleteWord(word: Int)

}