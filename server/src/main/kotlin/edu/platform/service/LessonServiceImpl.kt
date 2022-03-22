package edu.platform.service

import edu.platform.dao.LessonDao
import edu.platform.dao.QuestionDao
import edu.platform.model.Lesson

class LessonServiceImpl(
    private val lessonDao: LessonDao,
    private val questionDao: QuestionDao
) : LessonService {
    override fun loadAllLessonsForCourse(courseId: Long): List<Lesson> {
        return lessonDao.findLessonsForCourse(courseId)
    }

    override fun verifyAnswers(lessonId: Long, answers: List<Long>): Boolean {
        val correctAnswers =
            questionDao.findQuestionsForLesson(lessonId).map { question -> question.correct_answer_option_id }

        return answers == correctAnswers
    }
}