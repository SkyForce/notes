package ae.teletronics.notes.repository;

import ae.teletronics.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends MongoRepository<Note, Long> {
}
