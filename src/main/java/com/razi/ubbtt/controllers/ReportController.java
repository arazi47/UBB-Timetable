package com.razi.ubbtt.controllers;

import com.razi.ubbtt.Utils.MailSystem;
import com.razi.ubbtt.domain.Report;
import com.razi.ubbtt.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ReportController {
    @Autowired
    ReportRepository reportRepository;

    @GetMapping("/report")
    public String report(Model model, Principal principal) {

        // if we're authenticated
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        model.addAttribute("reportForm", Report.builder().build());
        return "report";
    }

    @PostMapping("/report")
    public String report(@ModelAttribute("reportForm") Report report, Principal principal) {
        report.setUsername(principal.getName());
        reportRepository.save(report);
        return "redirect:/report";
    }
}
