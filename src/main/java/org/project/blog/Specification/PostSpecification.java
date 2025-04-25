package org.project.blog.Specification;

import jakarta.persistence.criteria.Predicate;
import org.project.blog.Constant.Enum.POSTSTATUS;
import org.project.blog.Constant.Enum.ROLE;
import org.project.blog.Entity.Post;
import org.project.blog.Ultis.AuthUtils;
import org.springframework.data.jpa.domain.Specification;


public class PostSpecification {
    public static Specification<Post> accessControl() {
        return (root, query, cb) -> {
            String currentUser = AuthUtils.getCurrentUser();
            boolean isAdmin = AuthUtils.hasRole(ROLE.ROLE_ADMIN);

            if (isAdmin) {
                return cb.conjunction();
            }
            Predicate isPublic = cb.equal(root.get("status"), POSTSTATUS.PUBLISHED);
            Predicate isOwner = cb.equal(root.get("user").get("username"), currentUser);

            return cb.or(isPublic, isOwner);
        };

    }
}
