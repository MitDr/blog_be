package org.project.blog.Payload.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class ListResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private boolean last;

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
