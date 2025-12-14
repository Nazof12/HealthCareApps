package com.App.healtcare.data.local.entity.question

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabularyEntity")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val answer: String
)
