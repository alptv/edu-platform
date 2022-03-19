package edu.platform.service

import edu.platform.model.Lesson

interface LessonService {
    fun loadAllLessonsForCourse(courseId: Long) : List<Lesson>

    fun verifyAnswers(lessonId: Long, answers : List<Long>): Boolean
}