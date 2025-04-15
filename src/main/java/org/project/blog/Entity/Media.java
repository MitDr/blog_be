package org.project.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;
    @Column(name = "media_url")
    private String url;
    @Column(name = "media_type")
    private String type;
    @CreatedDate
    @Column(name = "media_create_at", updatable = false)
    private Date create_at;
}
