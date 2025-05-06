package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.Enum.POSTSTATUS;
import org.project.blog.Entity.Post;
import org.project.blog.Repository.PostRepository;
import org.project.blog.Service.PostSchedulerService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class PostSchedulerImpl implements PostSchedulerService {
    private final PostRepository postRepository;
    private final TaskScheduler taskScheduler;

    private final Map<Long, ScheduledFuture<?>> scheduledTask = new ConcurrentHashMap<>();

    @Override
    public void schedulePosts(Post post) {
        if (post.getScheduled_at() == null || post.getStatus() != POSTSTATUS.SCHEDULED) {
            return;
        }

        Runnable task = () -> publishPost(post.getId());
        long delay = post.getScheduled_at().toInstant().toEpochMilli() - Instant.now().toEpochMilli();

        if (delay > 0) {
            ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, new Date(System.currentTimeMillis() + delay));
            scheduledTask.put(post.getId(), scheduledFuture);
        } else {
            publishPost(post.getId());
        }
    }

    @Override
    public void cancelScheduledPosts(long postId) {
        ScheduledFuture<?> scheduledFuture = scheduledTask.get(postId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledTask.remove(postId);
        }
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("this is for later"));
        post.setStatus(POSTSTATUS.DRAFT);
        post.setScheduled_at(null);
        postRepository.save(post);
    }

    @Override
    public void publishPost(long postId) {
        Optional<Post> originalPost = postRepository.findById(postId);
        if (originalPost.isPresent()) {
            Post post = originalPost.get();
            if (post.getStatus() == POSTSTATUS.SCHEDULED) {
                post.setStatus(POSTSTATUS.PUBLISHED);
                post.setPublished_at(new Date());
                postRepository.save(post);
            }
        }
        scheduledTask.remove(postId);
    }

    @Override
    public void reschedulePosts(long id, Date date) {
        cancelScheduledPosts(id);
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("this is for later"));
        post.setScheduled_at(date);
        post.setStatus(POSTSTATUS.SCHEDULED);
        schedulePosts(post);
        postRepository.save(post);
    }
}
