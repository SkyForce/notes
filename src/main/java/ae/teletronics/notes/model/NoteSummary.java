package ae.teletronics.notes.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface NoteSummary {
    String getId();
    String getTitle();
    LocalDateTime getCreatedDate();
}
