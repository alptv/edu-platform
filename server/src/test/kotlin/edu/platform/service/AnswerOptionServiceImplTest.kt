package edu.platform.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.AnswerOptionDao
import edu.platform.model.AnswerOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AnswerOptionServiceImplTest {
    private val answerOptionDao = mock<AnswerOptionDao>()
    private val answerOptionService = AnswerOptionServiceImpl(answerOptionDao)

    @Test
    fun `loadAllAnswerOptionsForQuestion should return all courses from dao`() {
        val expectedAnswerOptions = listOf(
            AnswerOption(1, 1, "First option"),
            AnswerOption(2, 1, "Second option")
        )

        whenever(answerOptionDao.findAnswerOptionsForQuestion(any())).thenReturn(expectedAnswerOptions)

        val actualAnswerOptions = answerOptionService.loadAllAnswerOptionsForQuestion(1)
        assertThat(actualAnswerOptions).isEqualTo(expectedAnswerOptions)
        verify(answerOptionDao).findAnswerOptionsForQuestion(1)
    }
}
