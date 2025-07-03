package fr.hb.mlang.trainingforum.repository;

import fr.hb.mlang.trainingforum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Searches the database for a {@link User} with the corresponding <code>username</code>.
     *
     * @param username username of the user we are looking for
     * @return the <code>user</code> with the corresponding <code>username</code> or <code>null</code>
     */
    Optional<User> findByUsername(String username);
}
