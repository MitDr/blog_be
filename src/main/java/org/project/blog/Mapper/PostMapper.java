package org.project.blog.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Post;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Response.PostResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper extends GenericMapper<Post, PostRequest, PostResponse> {
}
