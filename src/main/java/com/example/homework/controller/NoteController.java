package com.example.homework.controller;

import com.example.homework.services.NoteService;
import com.example.homework.services.dto.NoteDto;
import com.example.homework.services.exception.NoteNotFoundException;
import com.example.homework.services.mapper.NoteMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;


@Validated
@Controller
@RequestMapping("/notes")
public class NoteController {
    @Autowired private NoteService noteService;
    @Autowired private NoteMapper noteMapper;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelAndView noteList() {
        ModelAndView result = new ModelAndView("notes/notes");
        result.addObject("notes", noteMapper.toNoteResponses(noteService.listAll()));
        return result;
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public ModelAndView getNoteForEdit(@NotEmpty @RequestParam(value="id") String id) throws NoteNotFoundException {
        UUID uuid = UUID.fromString(id);
        ModelAndView result = new ModelAndView("notes/updateNote");
        result.addObject("note", noteMapper.toNoteResponse(noteService.getById(uuid)));
        return result;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView createNote(
            @RequestParam(value="title") @Size(min = 1, max = 250) String title,
            @RequestParam(value="content") @NotBlank String content) {
        NoteDto dto = new NoteDto();
        dto.setId(UUID.randomUUID());
        dto.setTitle(title);
        dto.setContent(content);
        noteService.add(dto);
        return noteList();
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ModelAndView updateNote(
            @NotEmpty @RequestParam(value="id") String id,
            @Size(min = 1, max = 250) @RequestParam(value="title") String title,
            @NotEmpty @RequestParam(value="content") String content) throws NoteNotFoundException {
        NoteDto dto = new NoteDto();
        dto.setId(UUID.fromString(id));
        dto.setTitle(title);
        dto.setContent(content);
        noteService.update(dto);
        return getNoteForEdit(id);
    }

    @PostMapping("/delete")
    public String deleteNoteById(@Valid @NotNull @RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return "redirect:/notes/list";
    }
}
