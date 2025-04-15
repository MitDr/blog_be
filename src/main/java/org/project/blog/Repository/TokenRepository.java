package org.project.blog.Repository;

import org.project.blog.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {
    Optional<List<Token>> findByUserUsername(String username);
}
