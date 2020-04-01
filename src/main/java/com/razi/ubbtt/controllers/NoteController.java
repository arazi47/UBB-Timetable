package com.razi.ubbtt.controllers;

import com.razi.ubbtt.domain.Note;
import com.razi.ubbtt.domain.Report;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.NoteRepository;
import com.razi.ubbtt.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.attribute.HashAttributeSet;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;

@Controller
public class NoteController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    WeekRepository weekRepository;

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/admin/add-note")
    public String addNote(Model model, Principal principal) {
        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        model.addAttribute("noteForm", Note.builder().build());
        addNoteAttributesToModel(model);

        return "/admin/add-note";
    }

    @PostMapping("/admin/add-note")
    public String addNote(@ModelAttribute("noteForm") Note note, Principal principal) {
        //! TODO if we try to add a note on a course that already has a note display an error on the website

        // maybe this is not right, what if we wanted to modify something from some other semester?
        note.setSemester(weekRepository.getCurrentSemester());
        note.setAuthor(principal.getName());
        noteRepository.save(note);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/my-notes")
    public String myNotes(Model model, Principal principal) {
        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        model.addAttribute("myNotes", noteRepository.findAllByAuthor(principal.getName()));

        return "/admin/my-notes";
    }

    @GetMapping("/admin/my-notes/{id}/edit")
    public String editNote(Model model, @PathVariable Long id, Principal principal) {
        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        // TODO change all IDs to Long in the domain
        model.addAttribute("noteForm", noteRepository.findById(id).get());
        addNoteAttributesToModel(model);

        return "/admin/edit-note";
    }

    private void addNoteAttributesToModel(Model model) {
        model.addAttribute("disciplines", courseRepository.getDisciplinesBySemester(weekRepository.getCurrentSemester()));
        model.addAttribute("weeks", weekRepository.getWeeksFromCurrentUntilTheEndOfTheSemester(weekRepository.getCurrentWeek().getWeekNumber(), weekRepository.getCurrentSemester()));
        model.addAttribute("groups", Arrays.asList("921", "921/1", "921/2", "922", "922/1", "922/2", "923", "923/1", "923/2", "924", "924/1", "924/2", "925", "925/1", "925/2", "926", "926/1", "926/2", "927", "927/1", "927/2", "IE2"));
        model.addAttribute("courseTypes", Arrays.asList("Curs", "Seminar", "Laborator"));
    }

    @PostMapping("/admin/my-notes/{id}/edit")
    public String editNote(@ModelAttribute("noteForm") Note note, @PathVariable Long id, Principal principal) {
        note.setAuthor(principal.getName());
        note.setSemester(weekRepository.getCurrentSemester());
        noteRepository.save(note);

        return "redirect:/admin/my-notes";
    }
}
