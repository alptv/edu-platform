package edu.platform.config

import edu.platform.dao.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DaoConfiguration {

    @Bean
    fun newUserDao(dataSource: DataSource): UserDao {
        return UserDaoJdbc().apply { setDataSource(dataSource) }
    }

    @Bean
    fun newCourseDao(dataSource: DataSource): CourseDao {
        return CourseDaoJdbc().apply { setDataSource(dataSource) }
    }

    @Bean
    fun newLessonDao(dataSource: DataSource): LessonDao {
        return LessonDaoJdbc().apply { setDataSource(dataSource) }
    }

    @Bean
    fun newQuestionDao(dataSource: DataSource): QuestionDao {
        return QuestionDaoJdbc().apply { setDataSource(dataSource) }
    }

    @Bean
    fun newAnswerOptionDao(dataSource: DataSource): AnswerOptionDao {
        return AnswerOptionDaoJdbc().apply { setDataSource(dataSource) }
    }

}