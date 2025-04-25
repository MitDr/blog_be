package org.project.blog.Service.Impl;


import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Post;
import org.project.blog.Entity.User;
import org.project.blog.Mapper.PostMapper;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.PostResponse;
import org.project.blog.Repository.PostRepository;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.PostService;
import org.project.blog.Specification.PostSpecification;
import org.project.blog.Ultis.AuthUtils;
import org.project.blog.Ultis.SanitizerUtils;
import org.project.blog.Ultis.SearchUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImpl implements PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public ListResponse<PostResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        Specification<Post> spec = Specification
                .<Post>where(RSQLJPASupport.toSort(sort))
                .and(RSQLJPASupport.toSpecification(filter))
                .and(SearchUtils.parse(search, SearchFields.POST))
                .and(PostSpecification.accessControl());

        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findAll(spec, pageable);
        List<PostResponse> responses = postMapper.entityToResponse(posts.getContent());

        return new ListResponse<>(responses, posts);
    }

    @Override
    public PostResponse findById(Long aLong) {
        Specification<Post> spec = Specification.<Post>where((root, query, cb) ->
                cb.equal(root.get("id"), aLong)
        ).and(PostSpecification.accessControl());
        Post post = postRepository.findOne(spec)
                .orElseThrow(() -> new RuntimeException("this is for later"));

        return postMapper.entityToResponse(post);
    }

    @Override
    public PostResponse save(PostRequest request) {
        request.setContent(SanitizerUtils.sanitize(request.getContent()));
        Post post = postMapper.requestToEntity(request);

        String username = AuthUtils.getCurrentUser();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("this is for later"));
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return postMapper.entityToResponse(savedPost);
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
