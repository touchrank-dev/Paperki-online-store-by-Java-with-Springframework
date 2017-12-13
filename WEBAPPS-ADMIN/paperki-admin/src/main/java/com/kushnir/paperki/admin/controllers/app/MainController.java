package com.kushnir.paperki.admin.controllers.app;

import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.user.UserType;

import org.h2.util.IOUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public String mainPage() {
        return "index";
    }

    @PostMapping()
    public String login(@RequestParam HashMap<String, String> attributes, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        login(attributes.get("login"), attributes.get("password"), user);
        return "redirect:/";
    }

    @GetMapping("/favicon.ico")
    public void favicon (HttpServletResponse response) throws IOException {
        String filePathToBeServed =
                "/papsource/WEBAPPS-SHOP/paperki-shop/src/main/webapp/WEB-INF/view/resources/img/favicons/favicon.ico";
        InputStream is = new FileInputStream(new File(filePathToBeServed));
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
        is.close();
    }

    @ModelAttribute("user")
    public User setUser(HttpSession httpSession, HttpServletRequest req) {
        if (req.getRequestedSessionId() != null) {
            User user = (User) httpSession.getAttribute("user");
            if (user == null) {
                user = new User();
                httpSession.setAttribute("user", user);
            }
            return user;
        } else return null;
    }

    private void login(String login, String password, User user) {
        if (login.equalsIgnoreCase("admin")
                && password.equalsIgnoreCase("Pap111111")) {
            user.setUserType(UserType.ADMIN);
        }
    }
}
