package org.project.blog.Ultis;

import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class SearchUtils {
    public static <T> Specification<T> parse(String search, List<String> searchField) {
        if (search == null || search.isBlank() || searchField == null || searchField.isEmpty()) {
            return RSQLJPASupport.toSpecification((String) null);
        }

        return searchField.stream()
                .map(field -> field + "=like='" + search.trim() + "'")
                .collect(Collectors.collectingAndThen(Collectors.joining(","), RSQLJPASupport::toSpecification));
    }
}
