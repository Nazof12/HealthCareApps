package com.App.healtcare.data.repository

import com.App.healtcare.data.local.dao.question.VocabularyDao
import com.App.healtcare.data.local.entity.question.VocabularyEntity
import com.App.healtcare.data.model.MathInputSet
import com.App.healtcare.ui.quiz.math.DynamicQuestion
import com.App.healtcare.ui.quiz.math.QuestionGenerator
import com.App.healtcare.ui.quiz.vocabulary.domain.DynamicVocabQuestion
import com.App.healtcare.ui.quiz.vocabulary.domain.VocabularyGenerator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class QuestionRepository @Inject constructor(
    private val questionGenerator: QuestionGenerator,
    private val vocabularyDao : VocabularyDao,
    private val vocabGenerator: VocabularyGenerator
){

    fun getNewMathQuestion(min: Int, max: Int, operators : MathInputSet): DynamicQuestion{
        return questionGenerator.generateAddtionQuestion(min, max, operators)

    }
    fun getVocabQuestion(dbVocab: VocabularyEntity) : DynamicVocabQuestion{
        return vocabGenerator.generateVocabQuestion(dbVocab)
    }

    //start vocabulary database
    fun getVocabularyWord(): Flow<List<VocabularyEntity>>{
        return vocabularyDao.getVocabulary()
    }
    fun getWordById(word: Set<Int>): Flow<List<VocabularyEntity>>{
        return vocabularyDao.getWordById(word)
    }
    suspend fun insertVocabularyWord(word: VocabularyEntity){
        vocabularyDao.insertWord(word)
    }
    suspend fun updateVocabularyWord(word: VocabularyEntity){
        vocabularyDao.updateWord(word)
    }
    suspend fun deleteVocabularyWord(word: Int){
        vocabularyDao.deleteWord(word)
    }
    suspend fun getMinIdWord(): Int{
        return vocabularyDao.getMinId()
    }
    suspend fun getMaxIdWord(): Int{
        return vocabularyDao.getMaxId()
    }
    //end voacabulary database


}