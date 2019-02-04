package com.kratiukkarol.notesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kratiukkarol.notesapp.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
