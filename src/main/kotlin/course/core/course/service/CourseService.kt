package course.core.course.service

import course.core.course.data.Course
import java.util.*

interface CourseService {
    fun delete(courseId: UUID)
    fun find(courseId: UUID): Course
}