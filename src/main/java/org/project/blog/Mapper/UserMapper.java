package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.User;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends GenericMapper<User, UserRequest, UserResponse> {

    @Override
    @Mapping(source = "update_at", target = "update_at")
    @Mapping(source = "created_at", target = "created_at")
    UserResponse entityToResponse(User user);

    @Override
    List<UserResponse> entityToResponse(List<User> entities);
}
