package edu.cnm.deepdive.jata.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.jata.model.entity.User;
import edu.cnm.deepdive.jata.service.AbstractUserService;
import edu.cnm.deepdive.jata.view.UserView;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the REST Controller that makes and processes HTTP requests to and from the cloud.
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final AbstractUserService userService;

  /**
   * This auto-wired constructor constructs an instance of the {@link AbstractUserService} that
   * will send these queries to the server.
   *
   * @param userService {@link AbstractUserService}
   */
  @Autowired
  public UserController(AbstractUserService userService) {
    this.userService = userService;
  }

  /**
   * This method sends a GET request to the server requesting the current {@link User} instance.
   *
   * @return user
   */
  @GetMapping(path = "/me")
  @JsonView(UserView.Internal.class)
  public User get() {
    return userService.getCurrentUser();
  }

  /**
   * This method sends a PUT request to the server updating the current {@link User} instance.
   *
   *
   * @param user The {@link User} instance.
   * @return user The updated {@link User} instance.
   */
  @PutMapping(path = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Internal.class)
  public User put(@RequestBody User user) {
    return userService.updateUser(user, userService.getCurrentUser());
  }

  /**
   * This method processes a GET request to the server requesting a {@link User} instance by
   * {@link UUID} key
   *
   * @param key The user's {@link UUID} key
   * @return user The {@link User} instance.
   */
  @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.External.class)
  public User getUser(@PathVariable UUID key) {
    return userService.getUser(key);
  }



}
