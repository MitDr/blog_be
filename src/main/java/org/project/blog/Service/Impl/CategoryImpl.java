package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Mapper.CategoryMapper;
import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Response.CategoryResponse;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Repository.CategoryRepository;
import org.project.blog.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    @Override
    public ListResponse<CategoryResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.CATEGORY, categoryRepository, categoryMapper);
    }

    @Override
    public CategoryResponse findById(Long aLong) {
        return defaultFindById(aLong, categoryRepository, categoryMapper, ResourceName.CATEGORY);
    }

    @Override
    public CategoryResponse save(CategoryRequest request) {
        return defaultSave(request, categoryRepository, categoryMapper);
    }

    @Override
    public CategoryResponse update(Long aLong, CategoryRequest request) {
        return defaultSave(aLong, request, categoryRepository, categoryMapper, ResourceName.CATEGORY);
    }

    @Override
    public void delete(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        categoryRepository.deleteAllById(longs);
    }
}
