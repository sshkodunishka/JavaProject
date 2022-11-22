package kristina.document.security;

import kristina.document.models.User;
import kristina.document.security.jwt.JwtUser;
import kristina.document.security.jwt.JwtUserFactory;
import kristina.document.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public JwtUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User with username: " + username + " not found");
    }

    JwtUser jwtUser = JwtUserFactory.create(user);
    log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
    return jwtUser;
  }
}
