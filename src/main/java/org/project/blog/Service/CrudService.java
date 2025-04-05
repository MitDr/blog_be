package org.project.blog.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.project.blog.Mapper.GenericMapper;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Ultis.SearchUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CrudService<ID, I, O> {
    ListResponse<O> findAll(int page, int size, String sort, String filter, String search, boolean all);

    O findById(ID id);

    O save(I request);

    O update(ID id, I request);

    void delete(ID id);

    void delete(List<ID> ids);

    default O save(JsonNode request, Class<I> requestType) {
        ObjectMapper mapper = new ObjectMapper();
        I typeRequest = mapper.convertValue(request, requestType);
        return save(typeRequest);
    }

    default O update(ID id, JsonNode request, Class<I> requestType) {
        ObjectMapper mapper = new ObjectMapper();
        I typeRequest = mapper.convertValue(request, requestType);
        return update(id, typeRequest);
    }

    default <E> ListResponse<O> defaultFindAll(int page, int size,
                                               String sort, String filter,
                                               String search, boolean all,
                                               List<String> searchField,
                                               JpaSpecificationExecutor<E> repository,
                                               GenericMapper<E, I, O> mapper) {
        Specification<E> sortable = RSQLJPASupport.toSort(sort);
        Specification<E> filterable = RSQLJPASupport.toSpecification(filter);
        Specification<E> searchable = SearchUtils.parse(search, searchField);
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<E> entities = repository.findAll(sortable.and(filterable).and(searchable), pageable);
        List<O> entityResponse = mapper.entityToResponse(entities.getContent());
        return new ListResponse<>(entityResponse, entities);
    }

    default <E> O defaultFindById(ID id, JpaRepository<E, ID> repository, GenericMapper<E, I, O> mapper, String resourceName) {
        E entity = repository.findById(id).orElseThrow(() -> new RuntimeException("???"));
        System.out.println(entity);
        return mapper.entityToResponse(entity);//Change Exception later
    }

    default <E> O defaultSave(I request, JpaRepository<E, ID> repository, GenericMapper<E, I, O> mapper) {
        E entity = mapper.requestToEntity(request);
        entity = repository.save(entity);
        System.out.println(entity);
        return mapper.entityToResponse(entity);
    }

    default <E> O defaultSave(ID id, I request,
                              JpaRepository<E, ID> repository,
                              GenericMapper<E, I, O> mapper,
                              String resourceName) {
        return repository.findById(id)
                .map(existingEntity -> mapper.partialUpdate(existingEntity, request))
                .map(repository::save)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new RuntimeException("???"));                                                                      //Change Exception later
    }
}
