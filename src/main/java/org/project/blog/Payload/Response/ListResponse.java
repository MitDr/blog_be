package org.project.blog.Payload.Response;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class ListResponse<T> {
    List<T> content;
    int page;
    int size;
    int totalElements;
    int totalPages;
    boolean last;

    public <E> ListResponse(List<T> content, Page<E> page) {
        this.content = content;
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = (int) page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }

    public static <T, E> ListResponse<T> of(List<T> content, Page<E> page) {
        return new ListResponse<T>(content, page);
    }
}
