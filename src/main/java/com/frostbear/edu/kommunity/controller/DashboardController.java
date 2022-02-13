package com.frostbear.edu.kommunity.controller;

import com.frostbear.edu.kommunity.service.ThreadService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class DashboardController {

    private final ThreadService threadService;

    @GetMapping
    public String renderDashboard(Authentication auth, Model m, @RequestParam(required = false) Object logout, Pageable pageable) {
        m.addAttribute("loggedOut", null != logout);
        m.addAttribute("threads", this.threadService.getPage(pageable));

        return "index";
    }
}