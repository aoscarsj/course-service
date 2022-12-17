package course.core.course.repository

import course.core.course.data.Course
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourseRepository : JpaRepository<Course, UUID> {
}