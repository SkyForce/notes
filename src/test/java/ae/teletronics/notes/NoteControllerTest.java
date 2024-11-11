package ae.teletronics.notes;

import ae.teletronics.notes.controller.NotesController;
import ae.teletronics.notes.model.NoteMain;
import ae.teletronics.notes.model.NoteRequest;
import ae.teletronics.notes.model.NoteSummary;
import ae.teletronics.notes.model.Tag;
import ae.teletronics.notes.repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class NoteControllerTest {
	@Container
	public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0"));

	@Autowired
	private NotesController notesController;

	@Autowired
	private NotesRepository notesRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		registry.add("spring.data.mongodb.database", () -> "testdb");
	}

	@BeforeEach
	void before() {
		notesRepository.deleteAll();
	}

	@Test
	public void testCreateNote() {
		ResponseEntity<?> responseEntity = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		NoteMain note = notesController.getNote(responseEntity.getBody().toString());
		assertEquals("B", note.getText());
		assertEquals("A", note.getTitle());
	}

	@Test
	public void testUpdateNote() {
		ResponseEntity<?> responseEntity = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		notesController.updateNote(responseEntity.getBody().toString(), NoteRequest.builder().title("C").text("D").build());
		NoteMain note = notesController.getNote(responseEntity.getBody().toString());
		assertEquals("D", note.getText());
		assertEquals("C", note.getTitle());
	}

	@Test
	public void testUpdateNotExists() {
		assertThrows(NoSuchElementException.class, () ->
				notesController.updateNote("123", NoteRequest.builder().title("C").text("D").build()));
	}

	@Test
	public void testDeleteNote() {
		ResponseEntity<?> responseEntity = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		String id = responseEntity.getBody().toString();
		notesController.deleteNote(responseEntity.getBody().toString());
		assertThrows(NoSuchElementException.class, () -> {
			notesController.getNote(id);
		});
	}

	@Test
	public void testSortNotes() {
		ResponseEntity<?> responseEntity1 = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		ResponseEntity<?> responseEntity2 = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		ResponseEntity<?> responseEntity3 = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		Page<NoteSummary> notes = notesController.getNotes(Set.of(), PageRequest.of(0, 10));
		List<String> noteIds = notes.stream().map(NoteSummary::getId).toList();
		assertEquals(List.of(responseEntity3.getBody().toString(), responseEntity2.getBody().toString(),
						responseEntity1.getBody().toString()), noteIds);
	}

	@Test
	public void testNoteStat() {
		ResponseEntity<?> responseEntity = notesController.createNote(NoteRequest.builder().title("A").text("Abc, abc, cba").build());
		ResponseEntity<?> noteStat = notesController.getNoteStat(responseEntity.getBody().toString());
		Map<String, Integer> map = (Map<String, Integer>) noteStat.getBody();
		assertEquals(Map.of("abc", 2, "cba", 1), map);
	}

	@Test
	public void testTagsNotes() {
		ResponseEntity<?> responseEntity1 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.BUSINESS)).build());
		ResponseEntity<?> responseEntity2 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.BUSINESS, Tag.IMPORTANT)).build());
		ResponseEntity<?> responseEntity3 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.PERSONAL)).build());
		ResponseEntity<?> responseEntity4 = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		Page<NoteSummary> notes = notesController.getNotes(Set.of(Tag.BUSINESS), PageRequest.of(0, 10));
		List<String> noteIds = notes.stream().map(NoteSummary::getId).toList();
		assertEquals(List.of(responseEntity2.getBody().toString(), responseEntity1.getBody().toString()), noteIds);
	}

	@Test
	public void testPagination() {
		ResponseEntity<?> responseEntity1 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.BUSINESS)).build());
		ResponseEntity<?> responseEntity2 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.BUSINESS, Tag.IMPORTANT)).build());
		ResponseEntity<?> responseEntity3 = notesController.createNote(NoteRequest.builder().title("A").text("B").tags(Set.of(Tag.PERSONAL)).build());
		ResponseEntity<?> responseEntity4 = notesController.createNote(NoteRequest.builder().title("A").text("B").build());
		Page<NoteSummary> notes = notesController.getNotes(Set.of(), PageRequest.of(1, 2));
		List<String> noteIds = notes.stream().map(NoteSummary::getId).toList();
		assertEquals(List.of(responseEntity2.getBody().toString(), responseEntity1.getBody().toString()), noteIds);
	}
}