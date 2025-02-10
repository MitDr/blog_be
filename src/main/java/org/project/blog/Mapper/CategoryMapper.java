package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Category;
import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends GenericMapper<Category, CategoryRequest, CategoryResponse> {
}
