package com.kratiukkarol.notesapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kratiukkarol.notesapp.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findByDeletedTrue();
	public List<Note> findByDeletedFalse();
	public Note findByIdAndDeletedTrue(Long id);
	public Note findByIdAndDeletedFalse(Long id);
}
