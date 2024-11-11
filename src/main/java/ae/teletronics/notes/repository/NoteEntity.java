package ae.teletronics.notes.repository;

import ae.teletronics.notes.model.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

@Document(collection = "notes")
@Builder
@Data
public class NoteEntity {
    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now(ZoneOffset.UTC);
    @Builder.Default
    private Set<Tag> tags = Set.of();
    @NotNull
    private Map<String, Integer> wordsCount;
}
