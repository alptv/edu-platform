package edu.platform.unit.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.CourseDao
import edu.platform.model.Course
import edu.platform.service.CourseServiceImpl
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("Course service test")
@Epic("Service test")
class CourseServiceImplTest {
    private val courseDao = mock<CourseDao>()
    private val courseService = CourseServiceImpl(courseDao)

    @Test
    @DisplayName("Loading answer options for question from data layer")
    @Description("Method loadAllAnswerOptionsForQuestion should extract all courses from data layer")
    fun `loadAllCourses should return all courses from dao`() {
        val courses = listOf(
            Course(1, "first course", "first description"),
            Course(2, "second course", "second description")
        )

        whenever(courseDao.findAllCourses()).thenReturn(courses)

        assertThat(courseService.loadAllCourses()).isEqualTo(courses)
        verify(courseDao).findAllCourses()
    }

    @Test
    @DisplayName("Successful loading course by id from data layer")
    @Description("Method loadCourseById should load course with given id from data layer")
    fun `loadCourseById should return course if course with given id present`() {
        val course = Course(1, "course", "description")

        whenever(courseDao.findCourseById(any())).thenReturn(course)

        assertThat(courseService.loadCourseById(1)).isEqualTo(course)
        verify(courseDao).findCourseById(1)
    }

    @Test
    @DisplayName("Successful loading course by id from data layer")
    @Description("Method loadCourseById should throw exception if course with given id not found in data layer")
    fun `loadCourseById should throw exception if course not found by id`() {
        whenever(courseDao.findCourseById(any())).thenReturn(null)

        assertThatThrownBy {
            courseService.loadCourseById(1)
        }.hasMessage("Course with id 1 not found").isExactlyInstanceOf(IllegalStateException::class.java)
        verify(courseDao).findCourseById(1)
    }

    @Test
    @DisplayName("Detecting course existence")
    @Description("Method hasCourseWithId should return true if course with given id exists in data layer")
    fun `hasCourseWithId should return true if course present`() {
        val course = Course(1, "course", "description")

        whenever(courseDao.findCourseById(any())).thenReturn(course)

        assertThat(courseService.hasCourseWithId(1)).isTrue
        verify(courseDao).findCourseById(1)
    }

    @Test
    @DisplayName("Detecting course nonexistence")
    @Description("Method hasCourseWithId should return false if course with given id does not exist in data layer")
    fun `hasCourseWithId should return false if course not present`() {
        whenever(courseDao.findCourseById(any())).thenReturn(null)

        assertThat(courseService.hasCourseWithId(1)).isFalse
        verify(courseDao).findCourseById(1)
    }
}