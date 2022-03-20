package edu.platform.service

import edu.platform.dao.AnswerOptionDao
import edu.platform.model.AnswerOption
import org.springframework.stereotype.Service

class AnswerOptionServiceImpl(
    private val answerOptionDao: AnswerOptionDao
) : AnswerOptionService {
    override fun loadAllAnswerOptionsForQuestion(questionId: Long): List<AnswerOption> {
        return answerOptionDao.findAnswerOptionsForQuestion(questionId)
    }
}