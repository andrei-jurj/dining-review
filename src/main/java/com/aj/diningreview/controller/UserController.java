package com.aj.diningreview.controller;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.exporter.UserCsvExporter;
import com.aj.diningreview.exporter.UserExcelExporter;
import com.aj.diningreview.exporter.UserPdfExporter;
import com.aj.diningreview.model.User;
import com.aj.diningreview.service.UserService;
import com.aj.diningreview.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listFirstPage(Model model) {

        return listByPage(1, model, "name", "asc", null);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword
                             ) {
        Page<User> page = userService.listByPage(pageNum, sortField, sortDir, keyword);
        List<User> users = page.getContent();

        long startCount = (pageNum - 1) * UserServiceImpl.USERS_PER_PAGE + 1;
        long endCount = startCount + UserServiceImpl.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        User user = new User();
        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Create new user");

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        System.out.println(user);
        userService.save(user);

        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

        return getRedirectURLtoAffectedUser(user);
    }

    private static String getRedirectURLtoAffectedUser(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
    }

    @GetMapping("/users/edit/{name}")
    public String editUser(@PathVariable(name = "name") String name, RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            User user = userService.getUserByName(name);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user (" + name + ")");

            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/users";
        }
    }

    @GetMapping("/users/{name}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("name") String name,
                                          @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        userService.updateUserEnabledStatus(name, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user name " + name + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String updateUserEnabledStatus(@PathVariable("id") Long id,
                                          Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    "The user ID " + id + " has been deleted");

        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }

        return "redirect:/users";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> userList = userService.findAll();
        UserCsvExporter userCsvExporter = new UserCsvExporter();
        userCsvExporter.export(userList, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> userList = userService.findAll();
        UserExcelExporter userExcelExporter = new UserExcelExporter();
        userExcelExporter.export(userList, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        List<User> userList = userService.findAll();
        UserPdfExporter userPdfExporter = new UserPdfExporter();
        userPdfExporter.export(userList, response);
    }
}