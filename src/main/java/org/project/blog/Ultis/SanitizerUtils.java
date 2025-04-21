package org.project.blog.Ultis;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizerUtils {
    private static final PolicyFactory POLICY_FACTORY = Sanitizers.BLOCKS
            .and(Sanitizers.LINKS)
            .and(Sanitizers.FORMATTING)
            .and(Sanitizers.IMAGES)
            .and(Sanitizers.TABLES);

    public static String sanitize(String string) {
        return POLICY_FACTORY.sanitize(string);
    }
}
