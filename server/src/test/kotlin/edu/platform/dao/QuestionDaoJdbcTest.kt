package edu.platform.dao

import edu.platform.model.Question
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import util.database.Connection.executeUpdate

import util.database.DataBaseTest

class QuestionDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var questionDao: QuestionDao

    @Test
    fun `findQuestionsForLesson should return all questions for given lesson sorted by number`() {
        database.inTransaction {
            executeUpdate("set constraints correct_answer_fk deferred")
            val questionsInsertSql =
                "insert into Questions (question_id, question_text, correct_answer_option_id) values (?, ?, ?)"
            executeUpdate(questionsInsertSql, 1, "Question1", 1)
            executeUpdate(questionsInsertSql, 2, "Question2", 2)
            executeUpdate(questionsInsertSql, 3, "Question3", 3)
            val answerOptionInsertSql =
                "insert into AnswerOptions (answer_option_id, question_id, answer_option_text) values (?, ?, ?)"
            executeUpdate(answerOptionInsertSql, 1, 1, "A")
            executeUpdate(answerOptionInsertSql, 2, 2, "B")
            executeUpdate(answerOptionInsertSql, 3, 3, "C")
        }
        database.executeUpdate("insert into Lessons (lesson_id, lesson_name) values (?, ?)", 1, "Lesson")
        val questionForLessonInsert =
            "insert into QuestionsForLessons (lesson_id, question_id, question_number) values (?, ?, ?)"
        database.executeUpdate(questionForLessonInsert, 1, 1, 3)
        database.executeUpdate(questionForLessonInsert, 1, 2, 2)
        database.executeUpdate(questionForLessonInsert, 1, 3, 1)

        val questions = questionDao.findQuestionsForLesson(1)

        assertThat(questions).isEqualTo(
            listOf(
                Question(3, "Question3", 3),
                Question(2, "Question2", 2),
                Question(1, "Question1", 1)
            )
        )
    }

}