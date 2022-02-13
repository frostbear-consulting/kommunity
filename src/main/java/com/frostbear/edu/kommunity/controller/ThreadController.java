package com.frostbear.edu.kommunity.controller;

import com.frostbear.edu.kommunity.domain.Session;
import com.frostbear.edu.kommunity.form.CreateCommentForm;
import com.frostbear.edu.kommunity.form.CreateThreadForm;
import com.frostbear.edu.kommunity.service.ThreadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class ThreadController {

    private final ThreadService service;
    private final Session currentSession;

    @GetMapping("/portal/new")
    @PreAuthorize("isFullyAuthenticated()")
    public String renderNewThread(Model m) {
        m.addAttribute("form", new CreateThreadForm());

        return "threads/new";
    }

    @PostMapping("/portal/new")
    @PreAuthorize("isFullyAuthenticated()")
    public String createThread(@ModelAttribute("form") @Validated CreateThreadForm form, BindingResult bindingResult, Model m, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "threads/new";
        }

        try {
            var created = this.service.create(
                    this.currentSession.getIdAccount(),
                    form.getTitle(),
                    form.getText()
            );

            return "redirect:/threads/" + created.getIdThread().toString();
        } catch (Exception ex) {
            ThreadController.log.error("Failed to create thread", ex);
            m.addAttribute("errorMessage", ex.getMessage());
            return this.renderNewThread(m);
        }
    }

    @GetMapping("/threads/{idThread}")
    public String renderThread(@PathVariable UUID idThread, Model m) {
        m.addAttribute("thread", this.service.forId(idThread));
        m.addAttribute("form", new CreateCommentForm());

        return "threads/_id";
    }

    @PostMapping("/portal/threads/{idThread}/comments")
    public String createComment(@PathVariable UUID idThread, @ModelAttribute("form") @Validated CreateCommentForm form, BindingResult bindingResult, Model m) {
        if (bindingResult.hasErrors()) {
            return this.renderThread(idThread, m);
        }

        try {
            this.service.createComment(idThread, this.currentSession.getIdAccount(), form.getComment());
        } catch (Exception ex) {
            ThreadController.log.error("Failed to create a comment", ex);
            m.addAttribute("errorMessage", ex.getMessage());
            return this.renderThread(idThread, m);
        }

        return "redirect:/threads/" + idThread.toString();
    }

}