package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Tag;
import org.project.blog.Payload.Request.TagRequest;
import org.project.blog.Payload.Response.TagResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper extends GenericMapper<Tag, TagRequest, TagResponse> {
    @Override
    @Mapping(source = "created_at", target = "created_at")
    TagResponse entityToResponse(Tag entity);
}
