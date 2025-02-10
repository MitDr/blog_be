package org.project.blog.Service;

import org.project.blog.Entity.Category;
import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Response.CategoryResponse;

public interface CategoryService extends CrudService<Long, CategoryRequest, CategoryResponse>{
}
