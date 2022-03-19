package edu.platform.controller

import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.model.AnswerOption
import edu.platform.service.AnswerOptionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(AnswerOptionController::class)
class AnswerOptionControllerTest : ControllerTest() {
    @MockBean
    private lateinit var answerOptionService: AnswerOptionService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `get answer option for question should return OK with correct json list of answer options by question id`() {

        val answerOptions = listOf(
            AnswerOption(1, 1, "First option"),
            AnswerOption(2, 1, "Second option")
        )

        whenever(answerOptionService.loadAllAnswerOptionsForQuestion(any())).thenReturn(answerOptions)

        mockMvc.httpGet("/answerOption/forQuestion/1") {
            login()
        }.andExpect {
            status { isOk() }
            content { hasSameJsonAs(answerOptions) }
        }
        verify(answerOptionService).loadAllAnswerOptionsForQuestion(1)
    }

    @Test
    fun `get answer option for question should return UNAUTHORIZED if user not logged in`() {
        mockMvc.httpGet("/answerOption/forQuestion/1").andExpect {
            status { isUnauthorized() }
        }
    }
}