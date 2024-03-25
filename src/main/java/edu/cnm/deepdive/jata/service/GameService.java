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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final UserGameRepository userGameRepository;
  private final ShotRepository shotRepository;
  private final ShipLocationRepository shipLocationRepository;

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
    if (shot.getxCoord() > game.getBoardSizeX()
    || shot.getxCoord() < 1
    || shot.getyCoord() > game.getBoardSizeY()
    || shot.getyCoord() < 1)
    {
      throw new InvalidShotPlacementException("Invalid shot");
    }
  }

  @Override
  public List<ShipLocation> submitShips(UUID key, List<ShipLocation> ships, User currentUser) {
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

  private static void ValidateShipLocationAndBoardEdge(ShipLocation location, Game game) {
    if (location.getShipCoordX() > game.getBoardSizeX()
        || location.getShipCoordX() < 1
        || location.getShipCoordY() > game.getBoardSizeY()
        || location.getShipCoordY() < 1) {
      throw new InvalidShipLocationException("Ships must be placed on the board");
    }
  }

  private void ValidateShipLocationAndOtherShips(ShipLocation location) {
    int numberOfShips = location.getShipNumber();
    if (numberOfShips > 1) {
      for (int i = 1; i < numberOfShips; i++) {
        shipLocationRepository
            .findShipLocationByShipNumber(numberOfShips)
            .compareTo(
                shipLocationRepository.findShipLocationByShipNumber(i)
            );
      }
    }
  }

  @Override
  public Shot getShot(UUID key, UUID guessKey, User currentUser) {
    return null;
  }
}
