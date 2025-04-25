package org.project.blog.Controller;

import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Response.CategoryResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController extends GenericController<CategoryRequest, CategoryResponse> {
}
