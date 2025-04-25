package org.project.blog.Controller;

import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.PostResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController extends GenericController<PostRequest, PostResponse> {
}
