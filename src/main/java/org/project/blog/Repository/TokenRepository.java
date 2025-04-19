package org.project.blog.Repository;

import org.project.blog.Entity.Token;
import org.project.blog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {

    boolean existsByUserAndDeviceIdAndRevokedFalse(User user, String deviceId);

    Optional<Token> findByValueAndDeviceId(String value, String deviceId);
}
