package fr.hb.mlang.trainingforum.repository;

import fr.hb.mlang.trainingforum.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, String> {
}
