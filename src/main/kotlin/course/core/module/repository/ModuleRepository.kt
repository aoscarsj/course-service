package course.core.module.repository

import course.core.module.data.Module
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ModuleRepository : JpaRepository<Module, UUID> {
}