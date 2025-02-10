package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Mapper.MediaMapper;
import org.project.blog.Payload.Request.MediaRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.MediaResponse;
import org.project.blog.Repository.MediaRepository;
import org.project.blog.Service.MediaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaImpl implements MediaService {
    private MediaRepository mediaRepository;
    private MediaMapper mediaMapper;
    @Override
    public ListResponse<MediaResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFields.MEDIA, mediaRepository, mediaMapper);
    }

    @Override
    public MediaResponse findById(Long aLong) {
        return defaultFindById(aLong, mediaRepository, mediaMapper, ResourceName.MEDIA);
    }

    @Override
    public MediaResponse save(MediaRequest request) {
        return defaultSave(request, mediaRepository, mediaMapper);
    }

    @Override
    public MediaResponse update(Long aLong, MediaRequest request) {
        return defaultSave(aLong, request, mediaRepository, mediaMapper, ResourceName.MEDIA);
    }

    @Override
    public void delete(Long aLong) {
        mediaRepository.deleteById(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        mediaRepository.deleteAllById(longs);
    }
}
