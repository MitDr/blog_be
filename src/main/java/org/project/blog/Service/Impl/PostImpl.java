package org.project.blog.Service.Impl;


import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Mapper.PostMapper;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.PostResponse;
import org.project.blog.Repository.PostRepository;
import org.project.blog.Service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public ListResponse<PostResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.POST, postRepository, postMapper);
    }

    @Override
    public PostResponse findById(Long aLong) {
        return defaultFindById(aLong, postRepository, postMapper, ResourceName.POST);
    }

    @Override
    public PostResponse save(PostRequest request) {
        return defaultSave(request, postRepository, postMapper);
    }

    @Override
    public PostResponse update(Long aLong, PostRequest request) {
        return defaultSave(aLong, request, postRepository, postMapper, ResourceName.POST);
    }

    @Override
    public void delete(Long aLong) {
        postRepository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        postRepository.deleteAllById(longs);
    }
}
