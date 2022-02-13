package com.frostbear.edu.kommunity;

import com.frostbear.edu.kommunity.domain.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ActiveSessionFactory {

    /**
     * Returns the currently active session from the security context
     *
     * @return Active session
     */
    @Bean
    @RequestScope
    public Session currentSession() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (null == auth) {
            return null;
        }

        return (Session) auth.getPrincipal();
    }
}