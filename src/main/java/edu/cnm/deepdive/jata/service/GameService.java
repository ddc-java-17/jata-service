package edu.cnm.deepdive.jata.service;

import edu.cnm.deepdive.jata.model.dao.GameRepository;
import edu.cnm.deepdive.jata.model.dao.ShipLocationRepository;
import edu.cnm.deepdive.jata.model.dao.ShotRepository;
import edu.cnm.deepdive.jata.model.dao.UserGameRepository;
import edu.cnm.deepdive.jata.model.entity.Game;
import edu.cnm.deepdive.jata.model.entity.ShipLocation;
import edu.cnm.deepdive.jata.model.entity.Shot;
import edu.cnm.deepdive.jata.model.entity.User;
import edu.cnm.deepdive.jata.model.entity.UserGame;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  public static final int MAX_SHIPS_PER_PLAYER = 3;
  private final GameRepository gameRepository;
  private final UserGameRepository userGameRepository;
  private final ShotRepository shotRepository;
  private final ShipLocationRepository shipLocationRepository;
  private static boolean[][] hits;

  @Autowired
  public GameService(
      GameRepository gameRepository, UserGameRepository userGameRepository,
      ShotRepository shotRepository, ShipLocationRepository shipLocationRepository) {
    this.gameRepository = gameRepository;
    this.userGameRepository = userGameRepository;
    this.shotRepository = shotRepository;
    this.shipLocationRepository = shipLocationRepository;
  }

  @Override
  public Game startJoinGame(Game game, User user) {
    UserGame userGame = new UserGame();
    userGame.setGame(game);
    userGame.setUser(user);
    game.getUserGames().add(userGame);
//    game.setUser(user);
    return gameRepository.save(game);
  }


  @Override
  public Game getGame(UUID key, User user) {
    return gameRepository
        .findGameByKeyAndUserGamesUser(key, user)
        .orElseThrow();
  }

  @Override
  public List<Shot> submitShots(UUID key, List<Shot> shots, User currentUser) {
    return gameRepository.findGameByKeyAndUserGamesUser(key, currentUser)
        .map((game) -> {
          shots.forEach((shot) -> {
            ValidateShot(game, shot);
            shot.setToUser(
                userGameRepository.findUserGameByKeyAndGame(shot.getToUser().getKey(), game)
                    .orElseThrow());
            shot.setFromUser(
                userGameRepository.findUserGameByGameAndUser(game, currentUser).orElseThrow());
          });
          return shotRepository.saveAll(shots);
        })
        .orElseThrow();
  }

  private static void ValidateShot(Game game, Shot shot) throws InvalidShotPlacementException {
    if (shot.getLocation().getX() > game.getBoardSizeX()
    || shot.getLocation().getY() > game.getBoardSizeY())
    {
      throw new InvalidShotPlacementException("Invalid shot");
    }
  }

  /**
   * This method validates the placement of each ship, checking for ships partially, or entirely
   * off of the board, intersecting ships, or users trying to place ships once a fleet has
   * already been placed.  If the validation shows the placement is valid and legal,
   * the ships are saved in the ShipLocation table
   * @param key
   * @param ships
   * @param currentUser
   * @return
   */
  @Override
  public List<ShipLocation> submitShips(UUID key, List<ShipLocation> ships, User currentUser) {
    hits = new boolean[getGame(key, currentUser).getBoardSizeX()][getGame(key,
        currentUser).getBoardSizeY()];
    if (shipLocationRepository
        .findShipLocationByUserGame(userGameRepository
            .findUserGameByGameKeyAndUser(key, currentUser)
            .orElseThrow())
        .getCount() > 0) {
      throw new FleetAlreadyExistsException("You have already placed your ships");
    }

    return gameRepository.findGameByKeyAndUserGamesUser(key, currentUser)
        .map((game) -> {
          ships.forEach((shipLocation) -> {
            ValidateShipLocationAndBoardEdge(shipLocation,
                gameRepository.findGameByKey(key).orElseThrow());
            shipLocation.setUserGame(
                userGameRepository.findUserGameByGameAndUser(game, currentUser).orElseThrow());
          });
          return shipLocationRepository.saveAll(ships);
        })
        .orElseThrow();


  }

  /**
   * This is the validation method for board edge detection and ship intersection detection
   * @param location
   * @param game
   */
  private static void ValidateShipLocationAndBoardEdge(ShipLocation location, Game game) {
    // test versus board edges
    if (location.getLocation().getX() > game.getBoardSizeX()
        || location.getLocation().getY() > game.getBoardSizeY()) {
      throw new InvalidShipLocationException("Ships must be placed on the board");
    }
    // test versus other ships
    if (hits[location.getLocation().getX()][location.getLocation().getY()]) {
      throw new InvalidShipLocationException("Ships must not intersect each other");
    } else {
      hits[location.getLocation().getX()][location.getLocation().getY()] = true;
    }
  }

  /**
   * This will validate that a particular ship conforms to legal pattern for ships
   * namely one unit wide, several contiguous units long, and with orientation
   * of either horizontal or vertical
   * @param location
   * @param userGame
   * @param game
   */
  private static void ValidateLegalShip(ShipLocation location, UserGame userGame, Game game) {
  }

  @Override
  public Shot getShot(UUID key, UUID guessKey, User currentUser) {
    return null;
  }
}
