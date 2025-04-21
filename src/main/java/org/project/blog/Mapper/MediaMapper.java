package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Media;
import org.project.blog.Payload.Request.MediaRequest;
import org.project.blog.Payload.Response.MediaResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MediaMapper extends GenericMapper<Media, MediaRequest, MediaResponse> {
    @Override
    @Mapping(source = "created_at", target = "created_at")
    MediaResponse entityToResponse(Media entity);
}
