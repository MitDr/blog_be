package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Mapper.TagMapper;
import org.project.blog.Payload.Request.TagRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.TagResponse;
import org.project.blog.Repository.TagRepository;
import org.project.blog.Service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagImpl implements TagService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @Override
    public ListResponse<TagResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.TAG, tagRepository, tagMapper);
    }

    @Override
    public TagResponse findById(Long aLong) {
        return defaultFindById(aLong, tagRepository, tagMapper, ResourceName.TAG);
    }

    @Override
    public TagResponse save(TagRequest request) {
        return defaultSave(request, tagRepository, tagMapper);
    }

    @Override
    public TagResponse update(Long aLong, TagRequest request) {
        return defaultSave(aLong, request, tagRepository, tagMapper, ResourceName.TAG);
    }

    @Override
    public void delete(Long aLong) {
        tagRepository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        tagRepository.deleteAllById(longs);
    }
}
