package pp_3_1_4.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp_3_1_4.model.User;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @GetMapping()
    public ResponseEntity<User> getUser(Authentication auth) {
        return new ResponseEntity<>((User) auth.getPrincipal(), HttpStatus.OK);
    }
}
