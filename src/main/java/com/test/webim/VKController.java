package com.test.webim;

import com.test.webim.model.Role;
import com.test.webim.model.User;
import com.test.webim.service.UserServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.test.webim.Util.send;
import static com.test.webim.Util.serialized;

@Controller
public class VKController {

    @Autowired
    private UserServiceImpl userService;




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
    public String userInfo(@RequestParam String code, Model model, HttpServletRequest request) throws Exception {

        if(code != null) {
            String response;
            JSONObject serialized;
            response = send(String.format(Util.accessURL, code));
            serialized = serialized(response);
            String accessToken = serialized.get("access_token").toString();
            String userId = serialized.get("user_id").toString();
            String getUserInfo = send(String.format(Util.GET_PROFILE_INFO, userId, accessToken));
            serialized = serialized(getUserInfo);
            String response1 = serialized.get("response").toString();
            String substring = response1.substring(1, response1.length() - 1);
            JSONObject serialized1 = serialized(substring);
            String firstName = serialized1.get("first_name").toString();
            String lastName = serialized1.get("last_name").toString();
            model.addAttribute("userName", firstName + " " +  lastName);
            String getFriends = send(String.format(Util.GET_FRIENDS, userId, accessToken));
            serialized = serialized(getFriends);
            String response2 = serialized.get("response").toString();
            JSONObject serialized2 = serialized(response2);
            JSONArray items = (JSONArray) serialized2.get("items");
            List<String> friends = new ArrayList<>();
            items.forEach(obj -> friends.add(((JSONObject)obj).get("first_name").toString() + " "
                    + ((JSONObject)obj).get("last_name").toString()));
            model.addAttribute("friends", friends);

            User user = userService.getByVkId(userId);
            User newUser = null;

            if(user == null) {
                newUser =new User(firstName + " " + lastName, "password", userId, firstName, lastName, Collections.singleton(Role.ROLE_USER));
                newUser.setRoles(Collections.singleton(Role.ROLE_USER));
                userService.create(newUser);
                user = newUser;
            }

            HttpSession session = request.getSession();
            model.addAttribute("session",session.getAttributeNames());

            AuthorizedUser authUser = new AuthorizedUser(user);



        }

        return "userInfo";

    }








}
