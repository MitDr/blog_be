package org.project.blog.Service.Impl;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Aspect.Annotation.CheckRole;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.User;
import org.project.blog.Mapper.UserMapper;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.UserResponse;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.GenericService;
import org.project.blog.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final GenericService<User, UserRequest, UserResponse> genericService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostConstruct
    public void init() {
        genericService.init(userRepository, userMapper, SearchFields.USER, ResourceName.USER);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public ListResponse<UserResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return genericService.findAll(page, size, sort, filter, search, all);
    }

    @Override
    public UserResponse findById(Long aLong) {

        return genericService.findById(aLong);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public UserResponse save(UserRequest request) {

        return genericService.save(request);
    }

    @Override
    public UserResponse update(Long aLong, UserRequest request) {
        //TODO: Check password when update
        return defaultSave(aLong, request, userRepository, userMapper, ResourceName.USER);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(Long aLong) {

        genericService.delete(aLong);
    }

    @CheckRole(value = "ADMIN")
    @Override
    public void delete(List<Long> longs) {

        genericService.delete(longs);
    }
}
