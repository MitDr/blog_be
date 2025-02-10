package org.project.blog.Payload.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.blog.Constant.Enum.POSTSTATUS;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private POSTSTATUS status;
    private Date create_at;
    private Date update_at;
}
