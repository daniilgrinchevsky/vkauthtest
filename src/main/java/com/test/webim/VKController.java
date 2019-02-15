package com.test.webim;

import com.test.webim.model.Role;
import com.test.webim.model.Token;
import com.test.webim.model.User;
import com.test.webim.service.TokenServiceImpl;
import com.test.webim.service.UserDetailsServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.test.webim.Util.send;
import static com.test.webim.Util.serialized;

@Controller
public class VKController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private TokenServiceImpl tokenService;


    @GetMapping("/")
    public String index() {
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("url", Util.authURL);
        return "login";
    }


    @GetMapping(value = "/callback/vk")
    public ModelAndView userInfo(@RequestParam String code, Model model, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {

        if(code != null) {
            String response;
            JSONObject serialized;
            response = send(String.format(Util.accessURL, code));
            serialized = serialized(response);
            String accessToken = serialized.get("access_token").toString();
            String expiresIn = serialized.get("expires_in").toString();
            String userId = serialized.get("user_id").toString();
            String getUserInfo = send(String.format(Util.GET_PROFILE_INFO, userId, accessToken));
            serialized = serialized(getUserInfo);
            String response1 = serialized.get("response").toString();
            String substring = response1.substring(1, response1.length() - 1);
            JSONObject serialized1 = serialized(substring);
            String firstName = serialized1.get("first_name").toString();
            String lastName = serialized1.get("last_name").toString();
            model.addAttribute("userName", firstName + " " +  lastName);
            model.addAttribute("friends", userService.getVkFriends(userId, accessToken));

            User user = userService.getByVkId(userId);

            if(user == null) {
                user = new User(firstName + " " + lastName, "password", userId, firstName, lastName, Collections.singleton(Role.ROLE_USER));
                user.setRoles(Collections.singleton(Role.ROLE_USER));
                userService.create(user);
            }

            Token token = new Token(accessToken, LocalDateTime.now().plusSeconds(Long.parseLong(expiresIn)), user);

            tokenService.save(token);



            //httpServletResponse.addCookie(new Cookie("VK_SESSION", accessToken));
           // httpServletResponse.sendRedirect("/userInfo");


            AuthorizedUser authUser = new AuthorizedUser(user);


        }
        return new ModelAndView( "userInfo", "model", model);
       // return httpServletResponse;

    }

    @GetMapping("/friends")
    public String friends(Model model) throws Exception {
        User user =  userService.getCurrentUser();
        String accessToken = user.getToken().getId();
        model.addAttribute("friends", userService.getVkFriends(user.getVkId(), accessToken));
        return "userInfo";
    }






}
