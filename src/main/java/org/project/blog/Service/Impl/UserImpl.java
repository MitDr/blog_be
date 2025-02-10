package org.project.blog.Service.Impl;


import lombok.RequiredArgsConstructor;
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
        return null;
    }

    @Override
    public UserResponse findById(Long aLong) {
        return null;
    }

    @Override
    public UserResponse save(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse update(Long aLong, UserRequest request) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(List<Long> longs) {

    }
}
