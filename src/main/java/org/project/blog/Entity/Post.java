package org.project.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.blog.Constant.Enum.POSTSTATUS;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_post")
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "post_status")
    private POSTSTATUS status;
    @Column(name = "post_created_at")
    private Date created_at;
    @Column(name = "post_updated_at")
    private Date updated_at;
}
