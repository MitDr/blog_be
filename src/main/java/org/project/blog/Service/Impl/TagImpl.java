package org.project.blog.Service.Impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Aspect.Annotation.CheckRole;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Tag;
import org.project.blog.Mapper.TagMapper;
import org.project.blog.Payload.Request.TagRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.TagResponse;
import org.project.blog.Repository.TagRepository;
import org.project.blog.Service.GenericService;
import org.project.blog.Service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagImpl implements TagService {

    private final GenericService<Tag, TagRequest, TagResponse> genericService;

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @PostConstruct
    public void init() {
        genericService.init(tagRepository, tagMapper, SearchFields.TAG, ResourceName.TAG);
    }

    @Override
    public ListResponse<TagResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return genericService.findAll(page, size, sort, filter, search, all);
    }

    @Override
    public TagResponse findById(Long aLong) {
        return genericService.findById(aLong);
    }

    @Override
    public TagResponse save(TagRequest request) {

        return genericService.save(request);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public TagResponse update(Long aLong, TagRequest request) {
        return genericService.update(aLong, request);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(Long aLong) {

        genericService.delete(aLong);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(List<Long> longs) {

        genericService.delete(longs);
    }
}
