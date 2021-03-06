package com.webcheckers.ui.login;

import com.webcheckers.appl.Constants;
import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements TemplateViewRoute {

    private GameCenter gameCenter;

    public LoginHandler(GameCenter gameCenter) {
        this.gameCenter = gameCenter;
    }

    /**
     * Handles posts to /login, sends them to home page on success and back to login on failure
     * @param request - spark request
     * @param response - spark response
     * @return - model and view based on result of login
     */
    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        String userName = request.queryParams("userName");
        if (gameCenter.login(userName, request.session())) {
            vm.put(Constants.TITLE_ATTR, "Welcome!");
            vm.put(Constants.WELCOME_MESSAGE_ATTR, "Success! You are now logged in " + userName);
            vm.put(Constants.CURRENT_PLAYERS_ATTR, gameCenter.getCurrentPlayers());
            vm.put(Constants.CHALLENGE_ERROR_ATTR, false);
        } else {
            vm.put(Constants.TITLE_ATTR, "Login");
            vm.put(Constants.FAILED_LOGIN_ATTR, true);
            vm.put(Constants.FAILED_LOGIN_MESSAGE_ATTR, "Error: User already exists, please choose a new user name.");
            return new ModelAndView(vm, Constants.LOGIN_VIEW);
        }
        return new ModelAndView(vm , Constants.HOME_VIEW);
    }
}
