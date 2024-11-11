package ae.teletronics.notes.controller;

import ae.teletronics.notes.model.*;
import ae.teletronics.notes.service.NotesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping("/notes")
    public Page<NoteSummary> getNotes(@RequestParam(required = false) Set<Tag> tags, Pageable pageable) {
        return notesService.getNotes(tags, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("createdDate").descending()));
    }

    @GetMapping("/note/{id}")
    public NoteMain getNote(@PathVariable String id) {
        return notesService.getNote(id);
    }

    @GetMapping("/stat/{id}")
    public ResponseEntity<?> getNoteStat(@PathVariable String id) {
        Map<String, Integer> noteStat = notesService.getNoteStat(id);
        return ResponseEntity.ok(noteStat);
    }

    @PostMapping("/note")
    public ResponseEntity<?> createNote(@Valid @RequestBody NoteRequest noteRequest) {
        String noteId = notesService.createNote(noteRequest);
        return ResponseEntity.ok(noteId);
    }

    @PostMapping("/note/{id}")
    public ResponseEntity<?> updateNote(@PathVariable String id, @Valid @RequestBody NoteRequest noteRequest) {
        notesService.updateNote(id, noteRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id) {
        notesService.deleteNote(id);
    }
}
