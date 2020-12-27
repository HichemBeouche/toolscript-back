package ToolScript.Server;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ToolScript.Server.api.users.User;
import ToolScript.Server.api.users.UserRepository;

@Controller
@RequestMapping(path="/")
public class MainController {
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
  
  /* ****************** MODULES ****************** */
  
  
  /* ****************** SUBMODULES ****************** */
  
  
  /* ****************** NOTES ****************** */
}
