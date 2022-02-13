package com.frostbear.edu.kommunity.controller;

import com.frostbear.edu.kommunity.form.LoginForm;
import com.frostbear.edu.kommunity.form.RegisterForm;
import com.frostbear.edu.kommunity.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping("/login")
    public String renderLogin(Model m) {
        m.addAttribute("form", new LoginForm());
        return "login";
    }

    @GetMapping("/register")
    public String renderRegistration(Model m) {
        m.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("form") @Validated RegisterForm form, BindingResult bindingResult, Model m, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            var session = this.service.register(form.getUsername(), form.getPassword());
            var auth = new UsernamePasswordAuthenticationToken(session, null, session.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            redirectAttributes.addFlashAttribute("successMessage", "Welcome back %s".formatted(session.getUsername()));
            return "redirect:/";
        } catch (Exception ex) {
            AccountController.log.error("Failed to create account", ex);
            m.addAttribute("errorMessage", ex.getMessage());
            return this.renderRegistration(m);
        }
    }
}