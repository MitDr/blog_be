package org.project.blog.Constant;

import java.util.List;

public interface SearchFields {
    List<String> TAG = List.of(
            "name"
    );

    List<String> CATEGORY = List.of(
            "name"
    );

    List<String> MEDIA = List.of(
            "url",
            "type"
    );

    List<String> POST = List.of(
        "title",
        "content",
        "status"
    );

}
