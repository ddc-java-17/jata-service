package edu.cnm.deepdive.jata.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.jata.model.entity.Shot;
import edu.cnm.deepdive.jata.model.entity.User;
import edu.cnm.deepdive.jata.model.entity.UserGame;
import edu.cnm.deepdive.jata.service.AbstractGameService;
import edu.cnm.deepdive.jata.service.AbstractUserService;
import edu.cnm.deepdive.jata.view.ShotViews;
import edu.cnm.deepdive.jata.view.UserGameView;
import edu.cnm.deepdive.jata.view.UserView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{key}/shot")
public class ShotController {

  private final AbstractGameService gameService;
  private final AbstractUserService userService;

  @Autowired
  public ShotController(AbstractGameService gameService, AbstractUserService userService) {
    this.gameService = gameService;
    this.userService = userService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(ShotViews.Summary.class)
  public List<Shot> post(@PathVariable UUID key, @Valid @RequestBody List<Shot> shots) {
    return gameService.submitShots(key, shots, userService.getCurrentUser());
//    URI location = WebMvcLinkBuilder.linkTo(
//        WebMvcLinkBuilder.methodOn(ShotController.class)
//            .get(key, newShot.)
//    )
//        .toUri();
//    return ResponseEntity.created(location)
//        .body(newShot);
  }

  @GetMapping(path = "/{shotKey}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Shot get(@PathVariable UUID key, @PathVariable UUID shotKey) {
    return gameService.getShot(key, shotKey, userService.getCurrentUser());
  }

}
