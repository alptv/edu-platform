package edu.platform.dao

import edu.platform.model.AnswerOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import util.database.Connection.executeUpdate
import util.database.DataBaseTest

class AnswerOptionDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var answerOptionDao: AnswerOptionDao


    @Test
    fun `findAnswerOptionsForQuestion should return all answer options for given question`() {
        database.inTransaction {
            executeUpdate("set constraints correct_answer_fk deferred")
            executeUpdate("insert into Questions (question_id, question_text, correct_answer_option_id) values (?, ?, ?)", 1, "Question", 1)
            val answerOptionInsertSql = "insert into AnswerOptions (answer_option_id, question_id, answer_option_text) values (?, ?, ?)"
            executeUpdate(answerOptionInsertSql, 1, 1, "A")
            executeUpdate(answerOptionInsertSql, 2, 1, "B")
            executeUpdate(answerOptionInsertSql, 3, 1, "C")
            executeUpdate(answerOptionInsertSql, 4, 1, "D")
        }

        val answerOptions = answerOptionDao.findAnswerOptionsForQuestion(1)
        val expectedAnswerOptions = listOf(
            AnswerOption(1, 1, "A"),
            AnswerOption(2, 1, "B"),
            AnswerOption(3, 1, "C"),
            AnswerOption(4, 1, "D"),
        )
        assertThat(answerOptions).hasSameSizeAs(expectedAnswerOptions)
        assertThat(answerOptions).hasSameElementsAs(expectedAnswerOptions)
    }
}