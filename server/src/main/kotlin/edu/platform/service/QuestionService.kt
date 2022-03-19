package edu.platform.service

import edu.platform.model.Question
import org.springframework.stereotype.Service

interface QuestionService {
    fun loadAllQuestionsForLesson(lessonId : Long) : List<Question>
}