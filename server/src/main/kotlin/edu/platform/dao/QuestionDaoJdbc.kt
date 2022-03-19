package edu.platform.dao

import edu.platform.model.Question
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

class QuestionDaoJdbc : JdbcDaoSupport(), QuestionDao {
    override fun findQuestionsForLesson(lessonId: Long): List<Question> {
        val sql = "select Questions.question_id as id, Questions.question_text as text, Questions.correct_answer_option_id " +
                "from Questions natural join QuestionsForLessons " +
                "where lesson_id = ?" +
                "order by question_number;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(Question::class.java), lessonId)
    }
}