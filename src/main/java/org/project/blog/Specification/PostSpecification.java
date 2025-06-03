package org.project.blog.Specification;

import jakarta.persistence.criteria.*;
import org.project.blog.Constant.Enum.POSTSTATUS;
import org.project.blog.Constant.Enum.ROLE;
import org.project.blog.Entity.Post;
import org.project.blog.Entity.User;
import org.project.blog.Ultis.AuthUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostSpecification {
    public static Specification<Post> accessControl() {
        return (root, query, cb) -> {
            String currentUser = AuthUtils.getCurrentUser();
            boolean isAdmin = AuthUtils.hasRole(ROLE.ADMIN);

            if (isAdmin) {
                return cb.conjunction();
            }
            Predicate isPublic = cb.equal(root.get("status"), POSTSTATUS.PUBLISHED);
            Predicate isOwner = cb.equal(root.get("user").get("username"), currentUser);

            return cb.or(isPublic, isOwner);
        };
    }

    public static Specification<Post> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Post> hasStatus(POSTSTATUS status) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Post> getPostByUserAndStatus(String username, POSTSTATUS status) {
        return (root, query, criteriaBuilder) -> {
            Predicate user = criteriaBuilder.equal(root.get("user").get("username"), username);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
            return criteriaBuilder.and(user, statusPredicate);
        };
    }

    public static Specification<Post> publishedByAdminWithTitleKeyword(String title) {
        return (root, query, cb) -> {
            Join<Post, User> userJoin = root.join("user", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("status"), POSTSTATUS.PUBLISHED));
            predicates.add(cb.equal(userJoin.get("role"), ROLE.USER));
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Post> postsByUsersWithMinPostsAndPublishedDateRange(long minPosts, Date startDate, Date endDate) {
        return (root, query, cb) -> {
            assert query != null;
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Post> subRoot = subquery.from(Post.class);

            subquery.select(cb.count(subRoot))
                    .where(cb.equal(subRoot.get("user"), root.get("user")))
                    .groupBy(subRoot.get("user"))
                    .having(cb.greaterThan(cb.count(subRoot), minPosts));

            List<Predicate> list = new ArrayList<>();

            list.add(cb.exists(subquery));

            if (startDate != null && endDate != null) {
                list.add(cb.between(root.get("published_at"), startDate, endDate));
            } else if (startDate != null) {
                list.add(cb.greaterThanOrEqualTo(root.get("published_at"), startDate));
            } else if (endDate != null) {
                list.add(cb.lessThanOrEqualTo(root.get("published_at"), endDate));
            }

            return cb.and(list.toArray(new Predicate[0]));
        };
    }
}
