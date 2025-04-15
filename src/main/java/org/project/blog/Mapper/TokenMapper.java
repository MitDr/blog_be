package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.Token;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Response.RefreshResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface TokenMapper extends GenericMapper<Token, RefreshRequest, RefreshResponse> {

    @Override
    default RefreshResponse entityToResponse(Token entity) {
        RefreshResponse response = new RefreshResponse();
        response.setRefreshToken(entity.getValue());
        return response;
    }
}

