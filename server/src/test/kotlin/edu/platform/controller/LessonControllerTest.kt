package edu.platform.controller

import edu.platform.model.Lesson
import edu.platform.service.LessonService
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(LessonController::class)
class LessonControllerTest : ControllerTest() {
    @MockBean
    private lateinit var lessonService: LessonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `lesson for course should return UNAUTHORIZED if user not logged in`() {
        mockMvc.httpGet("/lesson/forCourse/1").andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `get all lessons for course should return OK with json list all lessons for given course`() {
        val lessons = listOf(
            Lesson(1, "First lesson"),
            Lesson(2, "First lesson")
        )

        whenever(lessonService.loadAllLessonsForCourse(any())).thenReturn(lessons)

        mockMvc.httpGet("/lesson/forCourse/1") {
            header("origin", "http://localhost:3000")
            login()
        }.andExpect {
            status { isOk() }
            content { hasSameJsonAs(lessons) }
        }
        verify(lessonService).loadAllLessonsForCourse(1)
    }


    @Test
    fun `verify answers should return UNAUTHORIZED if user not logged in`() {
        mockMvc.httpPost("/lesson/1/verifyAnswers") {
            content = "[]"
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `verify answers should return OK with boolean mark indicating answers correctness`(correctAnswers: Boolean) {
        val answers = listOf(1L, 2L, 3L)

        whenever(lessonService.verifyAnswers(any(), any())).thenReturn(correctAnswers)

        mockMvc.httpPost("/lesson/1/verifyAnswers") {
            login()
            content = toJson(answers)
        }.andExpect {
            status { isOk() }
            content { string(correctAnswers.toString()) }
        }

        verify(lessonService).verifyAnswers(1, answers)
    }
}
