package ae.teletronics.notes.controller;

import ae.teletronics.notes.model.Note;
import ae.teletronics.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping("/notes")
    List<Note> hello() {
        return notesService.notes();
    }

    @PostMapping("/note")
    ResponseEntity<?> createNote(@RequestBody Note note) {
        System.out.println(note);
        notesService.createNote(note);
        return ResponseEntity.ok().build();
    }
}
