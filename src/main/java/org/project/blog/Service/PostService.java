package org.project.blog.Service;

import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Request.RescheduleRequest;
import org.project.blog.Payload.Response.PostResponse;

public interface PostService extends CrudService<Long, PostRequest, PostResponse> {
    void cancelScheduledPosts(long postId);

    void reschedulePosts(long id, RescheduleRequest request);
}
