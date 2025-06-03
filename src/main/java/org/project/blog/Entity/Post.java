package org.project.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.project.blog.Constant.Enum.POSTSTATUS;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_post")
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private POSTSTATUS status;
    @CreatedDate
    @Column(name = "post_created_at", updatable = false)
    private Date created_at;
    @LastModifiedDate
    @Column(name = "post_updated_at")
    private Date updated_at;
    @Column(name = "post_schedule_at")
    private Date scheduled_at;
    @Column(name = "post_publish_at")
    private Date published_at;

    @Version
    @Column(name = "post_version")
    private long version;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
