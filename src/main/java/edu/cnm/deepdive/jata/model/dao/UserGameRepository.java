package edu.cnm.deepdive.jata.model.dao;

import edu.cnm.deepdive.jata.model.entity.Game;
import edu.cnm.deepdive.jata.model.entity.User;
import edu.cnm.deepdive.jata.model.entity.UserGame;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository interface is for managing and retrieving data from UserGame
 * as needed.
 */
public interface UserGameRepository extends JpaRepository<UserGame, Long> {

  /**
   * This searches for a user object relative to this UserGame
   * @param user  User object relating to this UserGame
   * @return userGame
   */
  List<UserGame> findUserGameByUser(User user);

  /**
   * This searches for this User/game by the game object it points to
   * @param game  Game object relating to this UserGame
   * @return userGame
   */
  List<UserGame> findUserGameByGame(Game game);

  /**
   * This searches for this UserGame by the game id it points to
   * @param id  Game id relative to this UserGame
   * @return userGame
   */
  Optional<UserGame> findUserGameById(Long id);

  Optional<UserGame> findUserGameByKeyAndGame(UUID key, Game game);

  Optional<UserGame> findUserGameByGameKeyAndUser(UUID key, User user);

  Optional<UserGame> findUserGameByGameAndUser(Game game, User user);

}
