package org.project.blog.Controller;

import org.project.blog.Payload.Request.TagRequest;
import org.project.blog.Payload.Response.TagResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController extends GenericController<TagRequest, TagResponse> {

}
