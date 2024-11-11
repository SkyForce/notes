package ae.teletronics.notes.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

public interface NoteMain {
    String getId();
    String getTitle();
    String getText();
    LocalDateTime getCreatedDate();
    Set<Tag> getTags();
}
