package org.project.blog.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.blog.Payload.Request.*;
import org.project.blog.Payload.Response.*;
import org.project.blog.Service.*;
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
    private final GenericController<UserRequest, UserResponse> userController;

    //Service;
//    private final GenericService<Tag, TagRequest, TagResponse> tagService;
    private final TagService tagService;
    //    private final GenericService<Category, CategoryRequest, CategoryResponse> categoryService;
    private final CategoryService categoryService;
    //    private final GenericService<Media, MediaRequest, MediaResponse> mediaService;
    private final MediaService mediaService;

    private final PostService postService;
    //    private final GenericService<Post, PostRequest, PostResponse> postService;
    private final UserService userService;


    @PostConstruct
    public void registerControllers() throws NoSuchMethodException {

        //Authenticated
        //posts
        register("auth", "posts", postController, postService, PostRequest.class, true, true, true, true);
        //tags
        register("auth", "tags", tagController, tagService, TagRequest.class, true, true, true, true);
        //categories
        register("auth", "categories", categoryController, categoryService, CategoryRequest.class, true, true, true, true);
        //medias
        register("auth", "medias", mediaController, mediaService, MediaRequest.class, true, true, true, true);
        //users
        register("auth", "users", userController, userService, UserRequest.class, true, true, true, true);

        //PUBLIC
        register("public", "posts", postController, postService, PostRequest.class, true, false, false, false);

        //ADMIN
        //USER

//        register("admin", "posts", postController, postService, PostRequest.class, true, true, true, true);
//        register("posts", postController, postService.init(
//                context.getBean(PostRepository.class),
//                context.getBean(PostMapper.class),
//                SearchFields.POST,
//                ResourceName.POST
//        ), PostRequest.class);
    }


    private <I, O> void register(
            String area,
            String resource,
            GenericController<I, O> controller,
            CrudService<Long, I, O> service,
            Class<I> requestType,
            boolean allowGetAll,
            boolean allowCreate,
            boolean allowUpdate,
            boolean allowDelete
    ) throws NoSuchMethodException {
        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
        options.setPatternParser(new PathPatternParser());
        controller.setRequestType(requestType);
        controller.setService(service);

        String path = "/api/v1/" + area + "/" + resource;
        if (allowGetAll) {
            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path)
                            .methods(RequestMethod.GET)
                            .produces(MediaType.APPLICATION_JSON_VALUE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("getAllResource", int.class, int.class, String.class, String.class, String.class, boolean.class)
            );

            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path + "/{id}")
                            .methods(RequestMethod.GET)
                            .produces(MediaType.APPLICATION_JSON_VALUE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("getResourceById", Long.class)
            );
        }

        if (allowCreate) {
            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path)
                            .methods(RequestMethod.POST)
                            .consumes(MediaType.APPLICATION_JSON_VALUE)
                            .produces(MediaType.APPLICATION_JSON_VALUE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("createResource", JsonNode.class)
            );
        }
        if (allowUpdate) {
            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path + "/{id}")
                            .methods(RequestMethod.PUT)
                            .consumes(MediaType.APPLICATION_JSON_VALUE)
                            .produces(MediaType.APPLICATION_JSON_VALUE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("updateResource", Long.class, JsonNode.class)
            );

        }
        if (allowDelete) {
            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path + "/{id}")
                            .methods(RequestMethod.DELETE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("deleteResource", long.class)
            );

            handlerMapping.registerMapping(
                    RequestMappingInfo
                            .paths(path)
                            .methods(RequestMethod.DELETE)
                            .consumes(MediaType.APPLICATION_JSON_VALUE)
                            .options(options)
                            .build(),
                    controller,
                    controller.getClass().getMethod("deleteResource", List.class)
            );
        }

    }
}
