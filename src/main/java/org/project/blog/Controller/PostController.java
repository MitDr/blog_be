package org.project.blog.Controller;

import lombok.RequiredArgsConstructor;
import org.project.blog.Payload.ClientResponse;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Request.RescheduleRequest;
import org.project.blog.Payload.Response.PostResponse;
import org.project.blog.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController extends GenericController<PostRequest, PostResponse> {

    private final PostService postService;

    @PostMapping("/auth/posts/cancel/{id}")
    public ResponseEntity<?> cancelPost(@PathVariable Long id) {
        postService.cancelScheduledPosts(id);
        return ResponseEntity.ok().body(new ClientResponse(null, "Post canceled"));
    }

    @PostMapping("/auth/posts/reschedule/{id}")
    public ResponseEntity<?> reschedulePost(@RequestBody RescheduleRequest request, @PathVariable Long id) {
        postService.reschedulePosts(id, request);
        return ResponseEntity.ok().body(new ClientResponse(null, "Post rescheduled"));
    }
}
