package edu.platform.dao

import edu.platform.model.Lesson
import org.springframework.stereotype.Repository

interface LessonDao {
    fun findLessonById(lessonId: Long): Lesson?

    fun findLessonsForCourse(courseId: Long): List<Lesson>
}