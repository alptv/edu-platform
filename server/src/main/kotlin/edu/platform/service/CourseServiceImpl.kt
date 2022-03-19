package edu.platform.service

import edu.platform.dao.CourseDao
import edu.platform.model.Course
import java.lang.IllegalStateException

class CourseServiceImpl(
    private val courseDao: CourseDao
) : CourseService {

    override fun loadAllCourses(): List<Course> {
        return courseDao.findAllCourses()
    }

    override fun loadCourseById(courseId: Long): Course {
        return courseDao.findCourseById(courseId) ?: throw IllegalStateException("Course with id $courseId not found")
    }

    override fun hasCourseWithId(courseId: Long): Boolean {
        return courseDao.findCourseById(courseId) != null
    }

}