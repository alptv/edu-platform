package edu.platform.controller

import edu.platform.model.Question
import edu.platform.service.QuestionService
import io.qameta.allure.Description
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import util.controller.ControllerTest

@WebMvcTest(QuestionController::class)
@DisplayName("Question controller test")
@Epic("Controller test")
class QuestionControllerTest : ControllerTest() {
    @MockBean
    private lateinit var questionService: QuestionService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Unauthorized getting lessons for course")
    @Description("GET on /question/forLesson/{lessonId} should return UNAUTHORIZED status code for unauthorized user")
    fun `questions for lesson should return UNAUTHORIZED if user not logged in`() {
        mockMvc.httpGet("/question/forLesson/1").andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    @Description("GET on /question/forLesson/{lessonId} should return OK status code with questions json list which given lesson")
    fun `questions for lesson should return OK with json list all questions for given lesson`() {
        val lessons = listOf(
            Question(1, "Q1?", 1),
            Question(2, "Q2?", 2)
        )

        whenever(questionService.loadAllQuestionsForLesson(any())).thenReturn(lessons)

        mockMvc.httpGet("/question/forLesson/1") {
            login()
        }.andExpect {
            status { isOk() }
            content { hasSameJsonAs(lessons) }
        }
        verify(questionService).loadAllQuestionsForLesson(1)
    }


}