package kristina.document.controllers;

import kristina.document.dto.AdminUserDto;
import kristina.document.models.User;
import kristina.document.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

  private final UserService userService;

  @Autowired
  public AdminRestControllerV1(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "users/{id}")
  public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
    User user = userService.findById(id);

    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    AdminUserDto result = AdminUserDto.fromUser(user);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
