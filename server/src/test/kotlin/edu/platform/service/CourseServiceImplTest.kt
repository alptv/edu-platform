package edu.platform.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.CourseDao
import edu.platform.model.Course
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test



class CourseServiceImplTest {
    private val courseDao = mock<CourseDao>()
    private val courseService = CourseServiceImpl(courseDao)

    @Test
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
    fun `loadCourseById should return course if course with given id present`() {
        val course = Course(1, "course", "description")

        whenever(courseDao.findCourseById(any())).thenReturn(course)

        assertThat(courseService.loadCourseById(1)).isEqualTo(course)
        verify(courseDao).findCourseById(1)
    }

    @Test
    fun `loadCourseById should throw exception if course not found by id`() {
        whenever(courseDao.findCourseById(any())).thenReturn(null)

        assertThatThrownBy {
            courseService.loadCourseById(1)
        }.hasMessage("Course with id 1 not found").isExactlyInstanceOf(IllegalStateException::class.java)
        verify(courseDao).findCourseById(1)
    }

    @Test
    fun `hasCourseWithId should return true if course present`() {
        val course = Course(1, "course", "description")

        whenever(courseDao.findCourseById(any())).thenReturn(course)

        assertThat(courseService.hasCourseWithId(1)).isTrue
        verify(courseDao).findCourseById(1)
    }

    @Test
    fun `hasCourseWithId should return false if course not present`() {
        whenever(courseDao.findCourseById(any())).thenReturn(null)

        assertThat(courseService.hasCourseWithId(1)).isFalse
        verify(courseDao).findCourseById(1)
    }
}