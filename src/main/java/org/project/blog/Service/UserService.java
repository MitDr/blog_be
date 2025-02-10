package org.project.blog.Service;

import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.UserResponse;

public interface UserService extends CrudService<Long, UserRequest, UserResponse>{
}
