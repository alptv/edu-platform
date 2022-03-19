package edu.platform.dao

import edu.platform.model.AnswerOption
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

class AnswerOptionDaoJdbc : JdbcDaoSupport(), AnswerOptionDao {
    override fun findAnswerOptionsForQuestion(questionId: Long): List<AnswerOption> {
        val sql = "select answer_option_id as id, question_id, answer_option_text as text " +
                "from AnswerOptions " +
                "where question_id = ?;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(AnswerOption::class.java), questionId)
    }
}