package ae.teletronics.notes.service;

import ae.teletronics.notes.model.Note;
import ae.teletronics.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;

    public List<Note> notes() {
        return notesRepository.findAll();
    }

    public void createNote(Note note) {
        notesRepository.save(note);
    }
}
