package org.project.blog.Service;

import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.PostResponse;

public interface PostService extends CrudService<Long, PostRequest, PostResponse> {
}
