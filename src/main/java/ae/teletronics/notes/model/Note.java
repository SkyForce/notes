package ae.teletronics.notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notes")
@Data
public class Note {
    @Id
    private String id;
    private String title;
    private String text;
    private LocalDateTime createdDate = LocalDateTime.now();
    private List<String> tags;

    // Getters and Setters
}
