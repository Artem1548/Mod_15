package com.example.homework.services;

import com.example.homework.services.dto.NoteDto;
import com.example.homework.services.exception.NoteNotFoundException;

import javax.naming.AuthenticationException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<NoteDto> listAll();

    NoteDto add(NoteDto note);

    List<NoteDto> addAll(Collection<NoteDto> notes);

    void deleteById(UUID id) throws NoteNotFoundException;

    void update(NoteDto note) throws NoteNotFoundException;

    NoteDto getById(UUID id) throws NoteNotFoundException;
}
