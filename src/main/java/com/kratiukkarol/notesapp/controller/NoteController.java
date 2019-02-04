package com.kratiukkarol.notesapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kratiukkarol.notesapp.repository.NoteRepository;

@RestController
@RequestMapping("/")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	
	// get all notes
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	// get a single note
	@GetMapping("/notes/{id}")
	public Note getNote(@PathVariable Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
	}
	// create a note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	// update a note
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable Long id, @Valid @RequestBody Note note) {
		Note currentNote = noteRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
		currentNote.setTitle(note.getTitle());
		currentNote.setContent(note.getContent());
		Note updatedNote = noteRepository.save(currentNote);
		return updatedNote;
	}
	
	// delete a note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable Long id) {
		Note currentNote = noteRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
		noteRepository.delete(currentNote);
		return ResponseEntity.ok().build();
	}
}
