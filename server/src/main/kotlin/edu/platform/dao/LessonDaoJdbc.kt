package edu.platform.dao

import edu.platform.model.Lesson
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

class LessonDaoJdbc : JdbcDaoSupport(), LessonDao {
    override fun findLessonById(lessonId: Long): Lesson? {
        val sql = "select lesson_id as id, lesson_name as name " +
                "from Lessons " +
                "where lesson_id = ?;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(Lesson::class.java), lessonId).firstOrNull()
    }

    override fun findLessonsForCourse(courseId: Long): List<Lesson> {
        val sql =
            "select Lessons.lesson_id as id, Lessons.lesson_name as name " +
                    "from LessonsForCourses natural join Lessons " +
                    "where course_id = ?" +
                    "order by lesson_number;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(Lesson::class.java), courseId)
    }
}