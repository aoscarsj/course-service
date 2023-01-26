package course.core.course.service

import course.core.course.data.Course
import course.core.course.data.request.CourseRegistrationRequest
import course.core.course.data.request.CourseUpdateRequest
import java.util.*

interface CourseService {
    fun delete(courseId: UUID)
    fun find(courseId: UUID): Course
    fun create(registrationRequest: CourseRegistrationRequest): Course
    fun update(courseId: UUID, updateRequest: CourseUpdateRequest): Course
    fun findAll(): List<Course>
}