package edu.platform.e2e

import com.codeborne.selenide.Selenide.open
import edu.platform.e2e.pages.CoursesPage
import edu.platform.e2e.pages.LessonPage
import edu.platform.e2e.pages.LoginPage
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Test solving E2E test")
@Epic("E2E test")
class TestSolveEndToEndTest : BaseEndToEndTest() {

    @Test
    @DisplayName("User solves test")
    @Description("User picking course, choose lesson, does not solve test at first try, but solves at second try")
    fun `user go to test fails at first try, then solve`() {
        val loginPage = LoginPage()
        open("/login")
        loginPage.typeLogin("user")
        loginPage.typePassword("password")
        loginPage.login()

        val coursesPage = CoursesPage()
        coursesPage.pickTab("Курсы")
        coursesPage.pickCourse("Алгебра. Начальная школа")

        val lessonPage = LessonPage()
        lessonPage.pickLesson("Запись чисел")
        lessonPage.pickAnswer(0, 0)
        lessonPage.pickAnswer(1, 1)
        lessonPage.pickAnswer(2, 2)
        lessonPage.pickAnswer(3, 3)
        lessonPage.submit()
        lessonPage.closePopup("Есть неверные ответы, попробуйте еще раз")

        lessonPage.pickAnswer(0, 0)
        lessonPage.pickAnswer(1, 0)
        lessonPage.pickAnswer(2, 1)
        lessonPage.pickAnswer(3, 3)
        lessonPage.submit()
        lessonPage.closePopup("Тест успешно пройден")

    }
}