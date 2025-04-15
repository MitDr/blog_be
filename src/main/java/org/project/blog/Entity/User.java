package org.project.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.project.blog.Constant.Enum.ROLE;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_name", unique = true)
    private String username;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.ROLE_USER;
    @Column(name = "user_avatar_url")
    private String avatar_url;
    @CreatedDate
    @Column(name = "user_created_at", updatable = false)
    private Date created_at;
    @LastModifiedDate
    @Column(name = "user_update_at")
    private Date update_at;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens;
}
