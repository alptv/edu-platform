package edu.platform.dao

import edu.platform.model.Question

interface QuestionDao {
    fun findQuestionsForLesson(lessonId: Long): List<Question>
}