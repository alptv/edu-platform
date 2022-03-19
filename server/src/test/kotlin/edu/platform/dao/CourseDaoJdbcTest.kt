package edu.platform.dao

import edu.platform.model.Course
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import util.database.DataBaseTest

class CourseDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var courseDao: CourseDao

    @BeforeEach
    fun setUp() {
        val courseInsertSql = "insert into Courses (course_id, course_name, course_description) values (?, ?, ?)"
        database.executeUpdate(courseInsertSql, 1, "Course1", "Description1")
        database.executeUpdate(courseInsertSql, 2, "Course2", "Description2")
        database.executeUpdate(courseInsertSql, 3, "Course3", "Description3")
    }

    @Test
    fun `findAllCourses should return all courses from database`() {
        val courses = courseDao.findAllCourses()

        val expectedCourses = listOf(
            Course(1, "Course1", "Description1"),
            Course(2, "Course2", "Description2"),
            Course(3, "Course3", "Description3")
        )
        assertThat(courses).hasSameSizeAs(expectedCourses)
        assertThat(courses).hasSameElementsAs(expectedCourses)
    }


    @Test
    fun `findCourseById should return course with given id if exists`() {
        val course = courseDao.findCourseById(1)
        assertThat(course).isEqualTo(
            Course(1, "Course1", "Description1")
        )
    }

    @Test
    fun `findCourseById should return null if course with given id does not exist`() {
        val course = courseDao.findCourseById(5)
        assertThat(course).isNull()
    }
}