package ae.teletronics.notes.service;

import ae.teletronics.notes.model.*;
import ae.teletronics.notes.repository.NoteEntity;
import ae.teletronics.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;

    public Page<NoteSummary> getNotes(Set<Tag> tags, Pageable pageable) {
        if (tags == null || tags.isEmpty()) {
            return notesRepository.findBy(pageable);
        }
        return notesRepository.findAllByTagsIn(tags, pageable);
    }

    public String createNote(NoteRequest noteRequest) {
        NoteEntity noteEntity = NoteEntity.builder()
                .title(noteRequest.getTitle())
                .text(noteRequest.getText())
                .tags(noteRequest.getTags())
                .wordsCount(getStats(noteRequest.getText()))
                .build();
        noteEntity = notesRepository.save(noteEntity);
        return noteEntity.getId();
    }

    public void updateNote(String id, NoteRequest noteRequest) {
        NoteEntity noteEntity = notesRepository.findById(id).orElseThrow();
        noteEntity.setTitle(noteRequest.getTitle());
        noteEntity.setText(noteRequest.getText());
        noteEntity.setTags(noteRequest.getTags());
        noteEntity.setWordsCount(getStats(noteRequest.getText()));
        notesRepository.save(noteEntity);
    }

    private Map<String, Integer> getStats(String text) {
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");

        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        LinkedHashMap<String, Integer> sortedMap = wordFrequencyMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return sortedMap;
    }

    public void deleteNote(String id) {
        notesRepository.deleteById(id);
    }

    public NoteMain getNote(String id) {
        return notesRepository.findNoteMainById(id).orElseThrow();
    }

    public Map<String, Integer> getNoteStat(String id) {
        NoteEntity noteEntity = notesRepository.findById(id).orElseThrow();
        return noteEntity.getWordsCount();
    }
}
