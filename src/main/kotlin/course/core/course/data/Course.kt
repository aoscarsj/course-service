package course.core.course.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import course.core.module.data.Module
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var courseId: UUID? = null,
    @Column(nullable = false, length = 150)
    val name: String,
    @Column(nullable = false, length = 250)
    val description: String,
    @Column
    val imageUrl: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: CourseStatus,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val level: CourseLevel,
    @Column(nullable = false)
    val instructorId: UUID,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val updated: LocalDateTime? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) // 1 search to modules and 1 search to all, JOIN -> 1 to all but
    // lazy doesn't work, SELECT -> N searches, default is JOIN
    val modules: MutableSet<Module> = mutableSetOf()
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

enum class CourseStatus { IN_PROGRESS, CONCLUDED }
enum class CourseLevel { BEGINNER, INTERMEDIARY, ADVANCED }
