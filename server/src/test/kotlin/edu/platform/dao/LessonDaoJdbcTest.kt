package edu.platform.dao

import edu.platform.model.Lesson
import io.qameta.allure.Description
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.springframework.beans.factory.annotation.Autowired
import util.database.DataBaseTest

@DisplayName("Lesson data access test")
@Epic("Data layer test")
class LessonDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var lessonDao: LessonDao

    @BeforeEach
    fun setUp() {
        val lessonInsertSql = "insert into Lessons (lesson_id, lesson_name) values (?, ?)"
        database.executeUpdate(lessonInsertSql, 1, "Lesson1")
        database.executeUpdate(lessonInsertSql, 2, "Lesson2")
        database.executeUpdate(lessonInsertSql, 3, "Lesson3")

        database.executeUpdate(
            "insert into Courses (course_id, course_name, course_description) values (?, ?, ?)",
            1,
            "Course",
            "Description"
        )

        val lessonsForCoursesInsertSql = "insert into LessonsForCourses (course_id, lesson_id, lesson_number) values (?, ?, ?)"
        database.executeUpdate(lessonsForCoursesInsertSql, 1, 1, 3)
        database.executeUpdate(lessonsForCoursesInsertSql, 1, 2, 2)
        database.executeUpdate(lessonsForCoursesInsertSql, 1, 3, 1)
    }

    @Test
    @DisplayName("Finding existing lesson by id")
    @Description("Method findLessonById should extract lesson with given id from database")
    fun `findLessonById should return lesson with given id if exists`() {
        val lesson = lessonDao.findLessonById(1)
        assertThat(lesson).isEqualTo(
            Lesson(1, "Lesson1")
        )
    }

    @Test
    @DisplayName("Finding non existing lesson by id")
    @Description("Method findCourseById should return null if lesson with given id does not exist")
    fun `findLessonById should return null if lesson with given id does not exist`() {
        val course = lessonDao.findLessonById(100)
        assertThat(course).isNull()
    }

    @Test
    @DisplayName("Finding lessons for course")
    @Description("Method findLessonsForCourse should extract lessons from database sorted by number which given course has")
    fun `findLessonsForCourse should return all lessons for given course sorted by number`() {
        val lessons = lessonDao.findLessonsForCourse(1)
        assertThat(lessons).isEqualTo(
            listOf(
                Lesson(3, "Lesson3"),
                Lesson(2, "Lesson2"),
                Lesson(1, "Lesson1")
            )
        )
    }
}