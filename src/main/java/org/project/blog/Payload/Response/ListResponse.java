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
        this.page = 0;
        this.size = 0;
        this.totalElements = 0;
        this.totalPages = 0;
        this.last = false;
    }

    public static <T, E> ListResponse<T> of(List<T> content, Page<E> page) {
        return new ListResponse<T>(content, page);
    }
}
