package org.project.blog.Repository;

import org.project.blog.Constant.Enum.POSTSTATUS;
import org.project.blog.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    void deleteByIdAndUser_Username(Long postId, String username);

    void deleteByUser_UsernameAndIdIn(String username, List<Long> ids);

    boolean existsByIdAndUser_UsernameAndStatus(Long postId, String username, POSTSTATUS status);
}
