package edu.platform.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.LessonDao
import edu.platform.dao.QuestionDao
import edu.platform.model.Lesson
import edu.platform.model.Question
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test

@DisplayName("Lesson service test")
@Epic("Service test")
class LessonServiceImplTest {
    private val questionDao = mock<QuestionDao>()
    private val lessonDao = mock<LessonDao>()
    private val lessonService = LessonServiceImpl(lessonDao, questionDao)

    @Test
    @DisplayName("Loading lessons for course from data layer")
    @Description("Method loadAllLessonsForCourse should extract all lessons for course from data layer")
    fun `loadAllLessonsForCourse should return all lessons from dao for given course id`() {
        val lessons = listOf(
            Lesson(1, "first lesson"),
            Lesson(2, "second lesson")
        )

        whenever(lessonDao.findLessonsForCourse(any())).thenReturn(lessons)

        assertThat(lessonService.loadAllLessonsForCourse(1)).isEqualTo(lessons)
        verify(lessonDao).findLessonsForCourse(1)
    }

    @Test
    @DisplayName("Verifying correct answers")
    @Description("Method verifyAnswers should return true if answers is equals to answers from data layer")
    fun `verifyAnswers should return true if given answer for lesson is correct`() {
        whenever(questionDao.findQuestionsForLesson(any())).thenReturn(
            listOf(
                Question(10, "Question 1?", 1),
                Question(20, "Question 2?", 2),
                Question(30, "Question 3?", 3)
            )
        )

        assertThat(lessonService.verifyAnswers(1, listOf(1, 2, 3))).isTrue
        verify(questionDao).findQuestionsForLesson(1)
    }

    @Test
    @DisplayName("Verifying incorrect answers")
    @Description("Method verifyAnswers should return false if answers is not equals to answers from data layer")
    fun `verifyAnswers should return false if given answer for lesson is incorrect`() {
        whenever(questionDao.findQuestionsForLesson(any())).thenReturn(
            listOf(
                Question(10, "Question 1?", 1),
                Question(20, "Question 2?", 2),
                Question(30, "Question 3?", 3)
            )
        )

        assertThat(lessonService.verifyAnswers(1, listOf(1, 2, 7))).isFalse
        verify(questionDao).findQuestionsForLesson(1)
    }
}