package course.core.lesson.service.impl

import course.core.lesson.repository.LessonRepository
import course.core.lesson.service.LessonService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository
) : LessonService {
    @Transactional
    override fun removeAllIntoModule(moduleId: UUID) {

        val lessons = lessonRepository.findAllLessonsIntoModule(moduleId)

        if (lessons.isNotEmpty())
            lessonRepository.deleteAll(lessons)
    }

}