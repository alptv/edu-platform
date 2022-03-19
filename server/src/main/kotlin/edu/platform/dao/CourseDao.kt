package edu.platform.dao

import edu.platform.model.Course
import org.springframework.stereotype.Repository

interface CourseDao {
    fun findAllCourses(): List<Course>

    fun findCourseById(courseId: Long): Course?

}