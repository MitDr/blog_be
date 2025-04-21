package org.project.blog.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Post;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.PostResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper extends GenericMapper<Post, PostRequest, PostResponse> {
    @Override
    @Mapping(source = "created_at", target = "created_at")
    @Mapping(source = "updated_at", target = "updated_at")
    PostResponse entityToResponse(Post entity);
}