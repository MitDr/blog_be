package org.project.blog.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.project.blog.Aspect.Annotation.CheckRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    @Before("@annotation(checkRole)")
    public void checkRole(JoinPoint joinPoint, CheckRole checkRole) {
        String requiredRole = checkRole.value();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasRole = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(requiredRole));
        if (!hasRole) {
            throw new RuntimeException("Access denied");
        }
    }

}
