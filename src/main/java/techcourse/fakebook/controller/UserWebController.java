package techcourse.fakebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import techcourse.fakebook.service.LoginService;
import techcourse.fakebook.service.TotalService;
import techcourse.fakebook.service.UserService;
import techcourse.fakebook.service.dto.UserResponse;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private static final Logger log = LoggerFactory.getLogger(UserWebController.class);

    private final UserService userService;
    private final LoginService loginService;
    private final TotalService totalService;

    public UserWebController(UserService userService, LoginService loginService, TotalService totalService) {
        this.userService = userService;
        this.loginService = loginService;
        this.totalService = totalService;
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable Long userId, Model model) {
        log.debug("begin");

        UserResponse userResponse = userService.findById(userId);
        model.addAttribute("user", userResponse);
        model.addAttribute("articles", totalService.findArticlesByUser(userId));
        return "profile";
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Long userId) {
        log.debug("begin");

        userService.deleteById(userId);

        return "redirect:/";
    }
}
