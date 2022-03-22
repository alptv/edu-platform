package edu.platform.controller

import edu.platform.model.Course
import edu.platform.service.CourseService
import io.qameta.allure.Description
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import util.controller.ControllerTest

@WebMvcTest(CourseController::class)
@DisplayName("Course controller test")
@Epic("Controller test")
class CourseControllerTest : ControllerTest() {

    @MockBean
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    @DisplayName("Unauthorized getting all courses")
    @Description("GET on /course/all should return UNAUTHORIZED status code for unauthorized user")
    fun `get all courses should return UNAUTHORIZED if user not logged in`() {
        mockMvc.httpGet("/course/all").andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    @DisplayName("Unauthorized getting course by id")
    @Description("GET on /course/{courseId} should return UNAUTHORIZED status code for unauthorized user")
    fun `get course by id should return UNAUTHORIZED for if user not logged in`() {
        mockMvc.httpGet("/course/1").andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    @DisplayName("Successful getting all courses")
    @Description("GET on /course/all should return OK status code with courses json list")
    fun `get all courses should return OK with json list containing all courses`() {
        val courses = listOf(
            Course(1, "First course", "First description"),
            Course(2, "Second course", "First description")
        )

        whenever(courseService.loadAllCourses()).thenReturn(courses)

        mockMvc.httpGet("/course/all") {
            login()
        }.andExpect {
            status { isOk() }
            content { hasSameJsonAs(courses) }
        }
        verify(courseService).loadAllCourses()
    }

    @Test
    @DisplayName("Successful getting course by id")
    @Description("GET on /course/{courseId} should return OK status code course json")
    fun `get course by id should return OK with course that was found by given id`() {
        val course = Course(1, "Course", "Description")

        whenever(courseService.hasCourseWithId(any())).thenReturn(true)
        whenever(courseService.loadCourseById(any())).thenReturn(course)

        mockMvc.httpGet("/course/1") {
            login()
        }.andExpect {
            status { isOk() }
            content { hasSameJsonAs(course) }
        }

        verify(courseService).hasCourseWithId(1)
        verify(courseService).loadCourseById(1)

    }

    @Test
    @DisplayName("Unsuccessful getting course by id")
    @Description("GET on /course/{courseId} should return NOT_FOUND if course with given id not exists")
    fun `get course by id should return NOT_FOUND if course with given id not exist`() {
        whenever(courseService.hasCourseWithId(any())).thenReturn(false)

        mockMvc.httpGet("/course/1") {
            login()
        }.andExpect {
            status { isNotFound() }
        }
        verify(courseService).hasCourseWithId(1)
    }
}