package aprendendo.api.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import aprendendo.api.blog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    @Query(value = "SELECT * FROM posts WHERE user_id = ?1",nativeQuery=true)
    List<Post> findAllByUser(Long id);
}
