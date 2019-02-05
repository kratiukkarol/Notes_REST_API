package com.kratiukkarol.notesapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kratiukkarol.notesapp.exception.NotFoundException;
import com.kratiukkarol.notesapp.model.Note;
import com.kratiukkarol.notesapp.model.Version;
import com.kratiukkarol.notesapp.repository.NoteRepository;
import com.kratiukkarol.notesapp.repository.VersionRepository;

@RestController
@RequestMapping("/")
public class NoteController {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	VersionRepository versionRepository;

	// get all undeleted notes
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return noteRepository.findByDeletedFalse();
	}

	// get all deleted notes
	@GetMapping("/notes/deleted")
	public List<Note> getAllDeletedNotes() {
		return noteRepository.findByDeletedTrue();
	}

	// get a single undeleted note
	@GetMapping("/notes/{id}")
	public ResponseEntity<?> getNote(@PathVariable Long id) {
		Note note = noteRepository.findByIdAndDeletedFalse(id);
		if(note==null) {
			return new ResponseEntity<RuntimeException>(new NotFoundException("Note with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	// get a single deleted note
	@GetMapping("/notes/deleted/{id}")
	public ResponseEntity<?> getDeletedNote(@PathVariable Long id) {
		Note note = noteRepository.findByIdAndDeletedTrue(id);
		if(note==null) {
			return new ResponseEntity<RuntimeException>(new NotFoundException("Note with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	// get history of changes of particular note
	@GetMapping("/notes/{id}/revision")
	public List<Version> getNoteVersions(@PathVariable Long id) {
		Note currentNote = noteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Note with id " + id + " not found."));
		return versionRepository.findByNote(currentNote);
	}

	// create a note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		Note newNote = noteRepository.save(note);
		newNote.setTitle(note.getTitle());
		newNote.setContent(note.getContent());
		Version newNoteVersion = new Version(note);
		newNoteVersion.setTitle(note.getTitle());
		newNoteVersion.setContent(note.getContent());
		versionRepository.save(newNoteVersion);
		return newNote;
	}

	// update a note
	@PutMapping("/notes/{id}")
	public ResponseEntity<?> updateNote(@PathVariable Long id, @Valid @RequestBody Note note) {
		Note currentNote = noteRepository.findByIdAndDeletedFalse(id);
		if(currentNote==null) {
			return new ResponseEntity<RuntimeException>(new NotFoundException("Note with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		currentNote.setTitle(note.getTitle());
		currentNote.setContent(note.getContent());
		Version newNoteVersion = new Version(currentNote);
		newNoteVersion.setTitle(note.getTitle());
		newNoteVersion.setContent(note.getContent());
		versionRepository.save(newNoteVersion);
		noteRepository.save(currentNote);
		return new ResponseEntity<Note>(currentNote, HttpStatus.OK);
	}

	// delete a note
	@DeleteMapping("/notes/{id}")
	public Note deleteNote(@PathVariable Long id) {
		Note currentNote = noteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Note with id " + id + " not found."));
		currentNote.setDeleted(true);
		Version newNoteVersion = new Version(currentNote);
		newNoteVersion.setTitle(currentNote.getTitle());
		newNoteVersion.setContent(currentNote.getContent());
		versionRepository.save(newNoteVersion);
		return noteRepository.save(currentNote);
	}
}
