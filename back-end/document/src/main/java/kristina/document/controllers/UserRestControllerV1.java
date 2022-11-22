package kristina.document.controllers;

import kristina.document.dto.UserDto;
import kristina.document.models.User;
import kristina.document.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
  private final UserService userService;

  @Autowired
  public UserRestControllerV1(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
    User user = userService.findById(id);

    if(user == null){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    UserDto result = UserDto.fromUser(user);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
