package org.project.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.project.blog.Constant.Enum.TOKENTYPE;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "tbl_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private final TOKENTYPE tokenType = TOKENTYPE.REFRESH_TOKEN;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long id;
    @Column(name = "token_value")
    private String value;
    @Column(name = "token_revoked")
    private boolean revoked;
    @CreatedDate
    @Column(name = "token_create_at", updatable = false)
    private Date create_at;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
