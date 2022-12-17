package course.core.course.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
class Course(
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
    val updated: LocalDateTime? = null

) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

enum class CourseStatus { IN_PROGRESS, CONCLUDED }
enum class CourseLevel { BEGINNER, INTERMEDIARY, ADVANCED }
