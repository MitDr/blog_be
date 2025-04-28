package org.project.blog.Service;

import org.project.blog.Entity.Post;

public interface PostSchedulerService {
    void schedulePosts(Post post);

    void cancelScheduledPosts(long postId);

    void publishPost(long postId);
}
