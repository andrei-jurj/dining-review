package com.aj.diningreview.controller;

import com.aj.diningreview.model.User;
import com.aj.diningreview.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register_form";
        }

        userService.postUser(user);
        return "register_success";
    }

    @GetMapping("/users")
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all_users";
    }

    @GetMapping("/users/{pageNo}/{pageSize}")
    public List<User> getPaginatedUsers(@PathVariable int pageNo,
                                        @PathVariable int pageSize) {

        return userService.findPaginated(pageNo, pageSize);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public String listUsers(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<User> userPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "paginated_users.html";
    }
}