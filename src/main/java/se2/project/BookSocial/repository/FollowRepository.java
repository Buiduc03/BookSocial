package se2.project.BookSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se2.project.BookSocial.model.Follow;
import se2.project.BookSocial.model.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("SELECT f FROM Follow f WHERE f.follower = :follower AND f.followed = :followed")
    Follow findByFollowerAndFollowed(@Param("follower") User follower, @Param("followed") User followed);
}
