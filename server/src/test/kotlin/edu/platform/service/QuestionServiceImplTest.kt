package edu.platform.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.QuestionDao
import edu.platform.model.Question
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QuestionServiceImplTest {
    private val questionDao = mock<QuestionDao>()
    private val questionService = QuestionServiceImpl(questionDao)

    @Test
    fun `loadAllQuestionsForLesson should return all questions from dao for given lesson id`() {
        val questions = listOf(
            Question(1, "Q1?", 1),
            Question(2, "Q2?", 5)
        )

        whenever(questionDao.findQuestionsForLesson(any())).thenReturn(questions)

        assertThat(questionService.loadAllQuestionsForLesson(1)).isEqualTo(questions)
        verify(questionDao).findQuestionsForLesson(1)
    }
}