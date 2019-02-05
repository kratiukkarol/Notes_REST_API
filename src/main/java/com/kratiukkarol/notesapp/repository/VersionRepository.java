package com.kratiukkarol.notesapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kratiukkarol.notesapp.model.Note;
import com.kratiukkarol.notesapp.model.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long>{
	
	public List<Version> findByNote(Note note);

}
