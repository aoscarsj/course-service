package course.core.course.service.impl

import course.core.course.repository.CourseRepository
import course.core.course.service.CourseService
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository
) : CourseService {

}