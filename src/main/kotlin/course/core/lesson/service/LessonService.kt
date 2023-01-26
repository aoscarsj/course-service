package course.core.lesson.service

import java.util.UUID

interface LessonService {
    fun removeAllIntoModule(moduleId: UUID)
}