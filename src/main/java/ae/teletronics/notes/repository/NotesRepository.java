package ae.teletronics.notes.repository;

import ae.teletronics.notes.model.NoteMain;
import ae.teletronics.notes.model.NoteStat;
import ae.teletronics.notes.model.NoteSummary;
import ae.teletronics.notes.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface NotesRepository extends MongoRepository<NoteEntity, String> {
    Page<NoteSummary> findAllByTagsIn(Set<Tag> tags, Pageable pageable);
    Page<NoteSummary> findBy(Pageable pageable);
    Optional<NoteMain> findNoteMainById(String id);
}
