package ToolScript.Server.api.users;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/")
public class UsersController {
  @Autowired
  private UserRepository userRepository;
  
  
  /* ****************** USERS ****************** */

  @GetMapping(path="user/{id_user}")
  public @ResponseBody Optional<User> getUserById(@PathVariable Integer id_user) {
    return userRepository.findById(id_user);
  }
  @GetMapping(path="users")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
  
  @PutMapping("/user/{id_user}/change-password")
  public Optional<Object> replaceUser(@RequestBody User newUser, @PathVariable Integer id_user) {

    return userRepository.findById(id_user).map(user -> {
		user.setPassword(newUser.getPassword());
        return userRepository.save(user);
      });
  }
  
  /* ****************** MODULES ****************** */
  
  
  /* ****************** SUBMODULES ****************** */
  
  
  /* ****************** NOTES ****************** */
}
