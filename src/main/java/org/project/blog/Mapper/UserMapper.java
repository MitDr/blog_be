package org.project.blog.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.project.blog.Entity.User;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.UserResponse;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends GenericMapper<User, UserRequest, UserResponse>{
}
