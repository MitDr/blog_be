package org.project.blog.Service;

import lombok.Setter;
import org.project.blog.Mapper.GenericMapper;
import org.project.blog.Payload.Response.ListResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
@Scope("prototype")
public class GenericService<E, I, O> implements CrudService<Long, I, O> {

    private JpaRepository<E, Long> repository;

    private JpaSpecificationExecutor<E> specificationExecutor;
    private GenericMapper<E, I, O> mapper;

    private List<String> searchFields;
    private String resourceName;

    public <R extends JpaRepository<E, Long> & JpaSpecificationExecutor<E>> GenericService<E, I, O> init(
            R repository,
            GenericMapper<E, I, O> mapper,
            List<String> searchFields,
            String resourceName) {
        this.setRepository(repository);
        this.setSpecificationExecutor(repository);
        this.setMapper(mapper);
        this.setSearchFields(searchFields);
        this.setResourceName(resourceName);
        return this;
    }

    @Override
    public ListResponse<O> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, searchFields, specificationExecutor, mapper);
    }

    @Override
    public O findById(Long aLong) {
        return defaultFindById(aLong, repository, mapper, resourceName);
    }

    @Override
    public O save(I request) {
        return defaultSave(request, repository, mapper);
    }

    @Override
    public O update(Long aLong, I request) {
        return defaultSave(aLong, request, repository, mapper, resourceName);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        repository.deleteAllById(longs);
    }
}
