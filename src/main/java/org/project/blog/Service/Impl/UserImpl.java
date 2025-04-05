package org.project.blog.Service.Impl;


import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Mapper.UserMapper;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.UserResponse;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ListResponse<UserResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.USER, userRepository, userMapper);
    }

    @Override
    public UserResponse findById(Long aLong) {
        return defaultFindById(aLong, userRepository, userMapper, ResourceName.USER);
    }

    @Override
    public UserResponse save(UserRequest request) {
        return defaultSave(request, userRepository, userMapper);
    }

    @Override
    public UserResponse update(Long aLong, UserRequest request) {
        //TODO: Check password when update
        return defaultSave(aLong, request, userRepository, userMapper, ResourceName.USER);
    }

    @Override
    public void delete(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        userRepository.deleteAllById(longs);
    }
}
