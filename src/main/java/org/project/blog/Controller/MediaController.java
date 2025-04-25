package org.project.blog.Controller;

import org.project.blog.Payload.Request.MediaRequest;
import org.project.blog.Payload.Response.MediaResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController extends GenericController<MediaRequest, MediaResponse> {
}
