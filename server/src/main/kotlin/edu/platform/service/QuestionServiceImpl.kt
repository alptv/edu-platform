package edu.platform.service

import edu.platform.dao.QuestionDao
import edu.platform.model.Question

class QuestionServiceImpl(
    private val questionDao: QuestionDao
) : QuestionService {

    override fun loadAllQuestionsForLesson(lessonId: Long): List<Question> {
        return questionDao.findQuestionsForLesson(lessonId)
    }

}