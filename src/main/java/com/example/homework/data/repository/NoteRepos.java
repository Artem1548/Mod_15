package com.example.homework.data.repository;

import com.example.homework.data.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface NoteRepos extends JpaRepository<Note, UUID> {

}
