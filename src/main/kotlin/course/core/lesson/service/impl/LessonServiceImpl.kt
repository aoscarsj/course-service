package course.core.lesson.service.impl

import course.core.lesson.repository.LessonRepository
import course.core.lesson.service.LessonService
import org.springframework.stereotype.Service

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository
) : LessonService {
}