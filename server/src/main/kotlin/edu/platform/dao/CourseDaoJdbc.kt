package edu.platform.dao

import edu.platform.model.Course
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

class CourseDaoJdbc : JdbcDaoSupport(), CourseDao {
    override fun findAllCourses(): List<Course> {
        val sql =
            "select course_id as id, course_name as name, course_description as description " +
                    "from Courses " +
                    "where true;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(Course::class.java))
    }

    override fun findCourseById(courseId: Long): Course? {
        val sql =
            "select course_id as id, course_name as name, course_description as description " +
                    "from Courses " +
                    "where course_id = ?;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(Course::class.java), courseId).firstOrNull()
    }

}