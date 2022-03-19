package edu.platform.config

import edu.platform.dao.*
import edu.platform.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ServiceConfiguration {

    @Bean
    fun newCourseService(courseDao: CourseDao): CourseService {
        return CourseServiceImpl(courseDao)
    }

    @Bean
    fun newLessonService(lessonDao: LessonDao, questionDao: QuestionDao): LessonService {
        return LessonServiceImpl(lessonDao, questionDao)
    }

    @Bean
    fun newQuestionService(questionDao: QuestionDao): QuestionService {
        return QuestionServiceImpl(questionDao)
    }

    @Bean
    fun newAnswerOptionService(answerOptionDao: AnswerOptionDao): AnswerOptionService {
        return AnswerOptionServiceImpl(answerOptionDao)
    }

    @Bean
    fun newUserService(userDao: UserDao): UserService {
        return UserServiceImpl(userDao)
    }
}