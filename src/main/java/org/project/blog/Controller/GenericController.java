package org.project.blog.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import lombok.Setter;
import org.project.blog.Constant.AppConstant;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Service.CrudService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@Setter
@Scope("prototype")
public class GenericController<I, O> {

    private CrudService<Long, I, O> service;

    private Class<I> requestType;

    public ResponseEntity<ListResponse<O>> getAllResource(
            @RequestParam(name = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstant.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter,
            @RequestParam(name = "search", required = false) @Nullable String search,
            @RequestParam(name = "all", defaultValue = "false") boolean all
    ) {
        return ResponseEntity.ok(service.findAll(page, size, sort, filter, search, all));
    }

    public ResponseEntity<O> getResourceById(
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    public ResponseEntity<O> createResource(
            @RequestBody JsonNode request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request, requestType));
    }

    public ResponseEntity<O> updateResource(
            @PathVariable(name = "id") Long id,
            @RequestBody JsonNode request
    ) {
        return ResponseEntity.ok(service.update(id, request, requestType));
    }

    public ResponseEntity<Void> deleteResource(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Void> deleteResource(@RequestBody List<Long> ids) {
        service.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
