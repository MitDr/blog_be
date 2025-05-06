package org.project.blog.Service.Impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Aspect.Annotation.CheckRole;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Category;
import org.project.blog.Mapper.CategoryMapper;
import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Response.CategoryResponse;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Repository.CategoryRepository;
import org.project.blog.Service.CategoryService;
import org.project.blog.Service.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final GenericService<Category, CategoryRequest, CategoryResponse> genericService;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @PostConstruct
    public void init() {
        genericService.init(categoryRepository, categoryMapper, SearchFields.CATEGORY, ResourceName.CATEGORY);
    }

    @Override
    public ListResponse<CategoryResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return genericService.findAll(page, size, sort, filter, search, all);
    }

    @Override
    public CategoryResponse findById(Long aLong) {
        return genericService.findById(aLong);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public CategoryResponse save(CategoryRequest request) {
        return genericService.save(request);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public CategoryResponse update(Long aLong, CategoryRequest request) {
        return genericService.update(aLong, request);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(List<Long> longs) {
        categoryRepository.deleteAllById(longs);
    }
}
