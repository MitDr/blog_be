package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.User;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.AuthResponse;
import org.project.blog.Payload.Response.RefreshResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {
    @Mapping(target = "password", ignore = true)
    User userRequestToUser(UserRequest userRequest);


    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    AuthResponse userToAuthResponse(User user);

    
    RefreshResponse refreshRequestToRefreshResponse(RefreshRequest refreshRequest);
}
