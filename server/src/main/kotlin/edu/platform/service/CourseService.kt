package edu.platform.service

import edu.platform.model.Course

interface CourseService {
    fun loadAllCourses(): List<Course>

    fun loadCourseById(courseId : Long): Course

    fun hasCourseWithId(courseId : Long): Boolean
}