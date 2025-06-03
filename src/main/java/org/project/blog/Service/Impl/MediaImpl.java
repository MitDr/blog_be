package org.project.blog.Service.Impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Media;
import org.project.blog.Mapper.MediaMapper;
import org.project.blog.Payload.Request.MediaRequest;
import org.project.blog.Payload.Response.ListResponse;
import org.project.blog.Payload.Response.MediaResponse;
import org.project.blog.Repository.MediaRepository;
import org.project.blog.Service.GenericService;
import org.project.blog.Service.MediaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaImpl implements MediaService {
    private final GenericService<Media, MediaRequest, MediaResponse> genericService;

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    @PostConstruct
    public void init() {
        genericService.init(mediaRepository, mediaMapper, SearchFields.MEDIA, ResourceName.MEDIA);
    }

    @Override
    public ListResponse<MediaResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return genericService.findAll(page, size, sort, filter, search, all);
    }

    @Override
    public MediaResponse findById(Long aLong) {
        return genericService.findById(aLong);
    }

    @Override
    public MediaResponse save(MediaRequest request) {
        return genericService.save(request);
    }

    @Override
    public MediaResponse update(Long aLong, MediaRequest request) {
        return genericService.update(aLong, request);
    }

    @Override
    public void delete(Long aLong) {
        genericService.delete(aLong);
    }

    @Override
    public void delete(List<Long> longs) {
        genericService.delete(longs);
    }
}
