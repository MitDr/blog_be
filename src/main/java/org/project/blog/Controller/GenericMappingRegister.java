package org.project.blog.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.ResourceName;
import org.project.blog.Constant.SearchFields;
import org.project.blog.Entity.Category;
import org.project.blog.Entity.Media;
import org.project.blog.Entity.Tag;
import org.project.blog.Mapper.CategoryMapper;
import org.project.blog.Mapper.MediaMapper;
import org.project.blog.Mapper.TagMapper;
import org.project.blog.Payload.Request.CategoryRequest;
import org.project.blog.Payload.Request.MediaRequest;
import org.project.blog.Payload.Request.PostRequest;
import org.project.blog.Payload.Request.TagRequest;
import org.project.blog.Payload.Response.CategoryResponse;
import org.project.blog.Payload.Response.MediaResponse;
import org.project.blog.Payload.Response.PostResponse;
import org.project.blog.Payload.Response.TagResponse;
import org.project.blog.Repository.CategoryRepository;
import org.project.blog.Repository.MediaRepository;
import org.project.blog.Repository.TagRepository;
import org.project.blog.Service.CrudService;
import org.project.blog.Service.GenericService;
import org.project.blog.Service.PostService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenericMappingRegister {
    private final ApplicationContext context;
    private final RequestMappingHandlerMapping handlerMapping;

    //Controller;
    private final GenericController<TagRequest, TagResponse> tagController;
    private final GenericController<CategoryRequest, CategoryResponse> categoryController;
    private final GenericController<MediaRequest, MediaResponse> mediaController;
    private final GenericController<PostRequest, PostResponse> postController;

    //Service;
    private final GenericService<Tag, TagRequest, TagResponse> tagService;
    private final GenericService<Category, CategoryRequest, CategoryResponse> categoryService;
    private final GenericService<Media, MediaRequest, MediaResponse> mediaService;

    private final PostService postService;
//    private final GenericService<Post, PostRequest, PostResponse> postService;


    @PostConstruct
    public void registerControllers() throws NoSuchMethodException {

        //Tags
        register("tags", tagController, tagService.init(
                context.getBean(TagRepository.class),
                context.getBean(TagMapper.class),
                SearchFields.TAG,
                ResourceName.TAG
        ), TagRequest.class);

        //Categories
        register("categories", categoryController, categoryService.init(
                context.getBean(CategoryRepository.class),
                context.getBean(CategoryMapper.class),
                SearchFields.CATEGORY,
                ResourceName.CATEGORY
        ), CategoryRequest.class);

        //Medias
        register("medias", mediaController, mediaService.init(
                context.getBean(MediaRepository.class),
                context.getBean(MediaMapper.class),
                SearchFields.MEDIA,
                ResourceName.MEDIA
        ), MediaRequest.class);

        //Posts
        register("posts", postController, postService, PostRequest.class);
//        register("posts", postController, postService.init(
//                context.getBean(PostRepository.class),
//                context.getBean(PostMapper.class),
//                SearchFields.POST,
//                ResourceName.POST
//        ), PostRequest.class);
    }


    private <I, O> void register(
            String resource,
            GenericController<I, O> controller,
            CrudService<Long, I, O> service,
            Class<I> requestType
    ) throws NoSuchMethodException {
        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
        options.setPatternParser(new PathPatternParser());
        controller.setRequestType(requestType);
        controller.setService(service);

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource)
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("getAllResource", int.class, int.class, String.class, String.class, String.class, boolean.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource + "/{id}")
                        .methods(RequestMethod.GET)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("getResourceById", Long.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource)
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("createResource", JsonNode.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource + "/{id}")
                        .methods(RequestMethod.PUT)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .produces(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("updateResource", Long.class, JsonNode.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource + "/{id}")
                        .methods(RequestMethod.DELETE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("deleteResource", long.class)
        );

        handlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/v1/" + resource)
                        .methods(RequestMethod.DELETE)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(options)
                        .build(),
                controller,
                controller.getClass().getMethod("deleteResource", List.class)
        );
    }
}
