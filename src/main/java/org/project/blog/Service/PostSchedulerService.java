package org.project.blog.Service;

import org.project.blog.Entity.Post;

import java.util.Date;

public interface PostSchedulerService {
    void schedulePosts(Post post);

    void cancelScheduledPosts(long postId);

    void publishPost(long postId);

    void reschedulePosts(long postId, Date date);
}
