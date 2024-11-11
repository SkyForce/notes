package ae.teletronics.notes.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NoteRequest {
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private Set<Tag> tags = Set.of();
}
