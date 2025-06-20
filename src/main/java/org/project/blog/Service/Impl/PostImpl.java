package org.project.blog.Service.Impl;


import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.Enum.POSTSTATUS;
import org.project.blog.Constant.Enum.ROLE;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Post;
import org.project.blog.Entity.User;
import org.project.blog.Mapper.PostMapper;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Request.RescheduleRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.PostResponse;
import org.project.blog.Repository.PostRepository;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.GenericService;
import org.project.blog.Service.PostSchedulerService;
import org.project.blog.Service.PostService;
import org.project.blog.Specification.PostSpecification;
import org.project.blog.Ultis.AuthUtils;
import org.project.blog.Ultis.SanitizerUtils;
import org.project.blog.Ultis.SearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostImpl.class);

    private final GenericService<Post, PostRequest, PostResponse> genericService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final PostSchedulerService postSchedulerService;

    @PostConstruct
    public void init() {
        genericService.init(postRepository, postMapper, SearchFields.POST, ResourceName.POST);
    }

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

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    public PostResponse save(PostRequest request) {
        String username = AuthUtils.getCurrentUser();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("this is for later"));
        request.setContent(SanitizerUtils.sanitize(request.getContent()));
        Post post = postMapper.requestToEntity(request);
        post.setUser(user);
        validateAndAssignStatus(post, request);
        Post savedPost = postRepository.save(post);
        if (post.getStatus() == POSTSTATUS.SCHEDULED) {
            postSchedulerService.schedulePosts(post);
        }
        logger.info("Post: {}", post);
        return postMapper.entityToResponse(savedPost);
    }

    @Transactional
    @Override
    public PostResponse update(Long aLong, PostRequest request) {
        if (request.getStatus() == POSTSTATUS.SCHEDULED || request.getScheduled_at() != null) {
            throw new RuntimeException("this is for later");
        }
        return defaultSave(aLong, request, postRepository, postMapper, ResourceName.POST);
    }

    @Transactional
    @Override
    public void delete(Long aLong) {
        String username = AuthUtils.getCurrentUser();
        postSchedulerService.cancelScheduledPosts(aLong);
        postRepository.deleteByIdAndUser_Username(aLong, username);
//        genericService.delete(aLong);
//        postRepository.deleteById(aLong);
    }

    @Transactional
    @Override
    public void delete(List<Long> longs) {
        String username = AuthUtils.getCurrentUser();
        for (Long id : longs) {
            postSchedulerService.cancelScheduledPosts(id);
        }
        postRepository.deleteByUser_UsernameAndIdIn(username, longs);
//        genericService.delete(longs);
//        postRepository.deleteAllById(longs);
    }

    private void validateAndAssignStatus(Post post, PostRequest request) {
        POSTSTATUS poststatus = request.getStatus();
        Date now = new Date();

        post.setStatus(poststatus);

        switch (poststatus) {
            case DRAFT:
                post.setUpdated_at(null);
                post.setPublished_at(null);
                break;

            case PUBLISHED:
                post.setScheduled_at(null);
                post.setPublished_at(now);
                break;

            case SCHEDULED:
                Date scheduled_at = request.getScheduled_at();
                if (scheduled_at == null || !scheduled_at.after(now)) {
                    throw new RuntimeException("This is for later");
                }
                post.setPublished_at(null);
                post.setScheduled_at(scheduled_at);
                break;
        }
    }

    @Override
    public void cancelScheduledPosts(long postId) {
        String username = AuthUtils.getCurrentUser();
        if (postRepository.existsByIdAndUser_UsernameAndStatus(postId, username, POSTSTATUS.SCHEDULED) || AuthUtils.hasRole(ROLE.ADMIN)) {
            postSchedulerService.cancelScheduledPosts(postId);
        } else {
            throw new RuntimeException("this is for later");
        }
    }

    @Override
    public void reschedulePosts(long id, RescheduleRequest request) {
        String username = AuthUtils.getCurrentUser();
        if (postRepository.existsByIdAndUser_UsernameAndStatus(id, username, POSTSTATUS.SCHEDULED) || AuthUtils.hasRole(ROLE.ADMIN)) {
            postSchedulerService.reschedulePosts(id, request.getDate());
        } else {
            throw new RuntimeException("this is for later");
        }
    }

}
