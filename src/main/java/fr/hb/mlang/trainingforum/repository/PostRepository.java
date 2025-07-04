package fr.hb.mlang.trainingforum.repository;

import fr.hb.mlang.trainingforum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.hb.mlang.trainingforum.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

  /**
   * Finds all the {@link Post} entries that were written by a {@link User}.
   *
   * @param author   The <code>user</code> whose <code>post</code> entries we want to retrieve
   * @param pageable Manages pagination for our results
   * @return A collection of <code>post</code> entries that were written by a given <code>user</code>,
   * or an empty array.
   */
  Page<Post> findByAuthor(User author, Pageable pageable);

}
