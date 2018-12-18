package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Services.EventService;
import com.example.demo.Services.RatingService;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    RatingService ratingService;

    //Logger
    private Logger log = Logger.getLogger(UserController.class.getName());

    //Current User logged in
    private User currentUser = new User();
    private Kitchen currentKitchen = new Kitchen();

//RETURN STRINGS

    //Redirect
    private final String REDIRECT = "redirect:/";

    //LOGIN:
    private final String LOGIN = "Login/login";
    private final String SIGNUP = "Login/signup";

    //INDEX
    private final String INDEX_LOGGED = "index_logged";

    //EVENT
    private final String EVENT = "event";

    //KITCHEN
    private final String KITCHEN = "Kitchen/kitchen";
    private final String EDIT_KITCHEN = "Kitchen/edit_kitchen";

    //JUDGE
    private final String JUDGE = "Judge/judge";
    private final String EDIT_JUDGE = "Judge/edit_judge";

    //RATING
    private final String GIVE_RATING = "rating/give_rating";

    //OTHER
    private final String FORM = "form";
    private final String ACCEPT = "accept";
    private final String VERIFY = "Admin/verify";
    private final String ACCOUNT = "account";


//LOGIN

    //LOGIN
    //MATHIAS
    @GetMapping("/login")
    public String login(Model model) {
        log.info("Get Login called...");

        model.addAttribute("users", new User());
        model.addAttribute("isLogin", true);

        userService.loginStatus(model);

        return LOGIN;
    }

    //MATHIAS
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, RedirectAttributes redirAttr) {
        log.info("Post Login called..");

        boolean loginMatch = false;
        loginMatch = userService.loginMatch(user);

        if (loginMatch == true) {
            redirAttr.addFlashAttribute("loginsuccess", true);
            redirAttr.addFlashAttribute("username", user.getUsername());

            currentUser = userService.loggedIn(user);

            model.addAttribute("role", currentUser.getRole());

            return REDIRECT + INDEX_LOGGED;

        } else {

            redirAttr.addFlashAttribute("loginError", true);

            return LOGIN;
        }
    }

    //LOGOUT
    //TOBIAS
    @GetMapping("/logout")
    public String logout(Model model){
        log.info("Logout called..");

        currentUser = new User();

        return REDIRECT;
    }

    //SIGN UP
    //TOBIAS
    @GetMapping("/signup")
    public String signup(Model model){
        log.info("Get Signup called...");

        model.addAttribute("user", new User());

        return SIGNUP;
    }

    //TOBIAS
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model,RedirectAttributes redirAttr) {
        log.info("Post Signup called..");

        boolean signUpMatch = false;
        signUpMatch = userService.signUpMatch(user);

        if (signUpMatch == true) {
            redirAttr.addFlashAttribute("loginsuccess", true);
            userService.addUser(user);
            log.info("User created...");
        } else {

            redirAttr.addFlashAttribute("loginError", true);
            log.info("User failed to create...");

            return  SIGNUP;
        }
        return REDIRECT;
    }

//Index logged In

    @GetMapping("/index_logged")
    public String indexLogged(Model model) {
        log.info("IndexLogged called ...");

        List<Event> e = eventService.getEvents();
        model.addAttribute("events", e);
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("role", currentUser.getRole());

        return INDEX_LOGGED;
    }

//Account informations

   //Simon
    @GetMapping("/account")
    public String Account(Model model){

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("user", currentUser);
        model.addAttribute("users", userService.getUsers());

        return ACCOUNT;
    }

//Events logged In
    //Simon
    @GetMapping("/event")
    public String event(Model model) {
        log.info("Event called..");

        List<Kitchen> k = userService.getKitchens();
        model.addAttribute("kitchens", k);

        List<Judge> j = userService.getJudges();
        model.addAttribute("judges", j);

        List<Event> e = eventService.getEvents();
        model.addAttribute("events", e);

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("username", currentUser.getUsername());

        return EVENT;
    }

//READ Kitchen n Judge
    //MATHIAS
    @GetMapping("/kitchen/kitchen/{id}")
    public String readKitchen(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        model.addAttribute("kitchen", userService.readKitchen(id));
        model.addAttribute("rating", ratingService.readRating(id));
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("kitchenid", id);

        return KITCHEN;
    }
    //MATHIAS
    @GetMapping("/judge/judge/{id}")
    public String readJudge(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        model.addAttribute("judge", userService.readJudge(id));
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("judgeid", id);

        return JUDGE;
    }

//FORMS
    //TOBIAS
    //Kitchen
    @GetMapping("/form")
    public String kitchenForm(Model model) {
        log.info("Get kitchenForm called...");

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("kitchen", new Kitchen());
        model.addAttribute("judge", new Judge());

        return FORM;
    }
    //TOBIAS
    @PostMapping("/form/{id}")
    public String kitchenForm(@PathVariable("id") int id, @ModelAttribute Kitchen kitchen, Model model){
        log.info("Post kitchenForm called + id: " + id);

        kitchen.setIduser(currentUser.getId());
        currentKitchen = userService.addKitchen(kitchen);
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("kitchens", userService.getKitchens());

        return ACCEPT;
    }
    //SIMON
    @GetMapping("/kitchen/accept/{id}")
    public String kitchenAccept(@PathVariable("id") int id,  Model model){
        log.info("Get kitchenAccept called + id: " + id);

        model.addAttribute("role", currentUser.getRole());
        userService.addKitchenToEvent(currentKitchen.getId());

        return ACCEPT;
    }
    //SIMON
    @GetMapping("/judge/accept/{id}")
    public String judgeAccept(@PathVariable("id") int id, Model model){
        log.info("Post judgeAccept called + id: " + id);

        model.addAttribute("role", currentUser.getRole());
        userService.addKitchenToEvent(id);

        return ACCEPT;
    }


//JUDGE EDIT
    //ALEX
    @GetMapping("/judge/edit_judge/{id}")
    public String editJudgeJudge(@PathVariable("id") int id, Model model){
        log.info("Get Edit Judge called as judge with id: " + id);

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("judge", userService.readJudge(id));

        return EDIT_JUDGE;
    }
    //ALEX
    @PostMapping("/judge/edit_judge/{id}")
    public String editJudgeJudge(@PathVariable("id") int id, @ModelAttribute Judge judge, Model model){
        log.info("Put Edit Judge called as Judge with id: " + id);

        userService.editJudge(judge);
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("judge", userService.getJudges());

        return EDIT_JUDGE;
    }
//KITCHEN EDIT

    //TOBIAS
    @GetMapping("/kitchen/edit_kitchen/{id}")
    public String editKitchen(@PathVariable("id") int id, Model model){
        log.info("Get Edit Judge called as judge with id: " + id);

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("kitchen", userService.readKitchen(id));

        return EDIT_KITCHEN;
    }

//VERIFY
    //MATHIAS
    @GetMapping("/admin/verify")
    public String verify(Model model){
        log.info("Verify action called...");

        model.addAttribute("kitchens", userService.getKitchens());
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("username", currentUser.getUsername());

        return VERIFY;
    }
    //MATHIAS
    @PutMapping("/admin/verify/{id}")
    public String verify(@PathVariable("id") int id, Model model) {
        log.info("Verify put action called...");

        userService.confirmKitchen(id);

        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("username", currentUser.getUsername());

        return VERIFY;
    }

//RATINGS
    //Simon
    @GetMapping("/rating/give_rating/{id}")
    public String giveRating(@PathVariable Integer id, Model model){
        log.info("Get rating action called with id: " + id);

        model.addAttribute("rating", new Rating());
        model.addAttribute("kitchen", userService.readKitchen(id));
        model.addAttribute("role", currentUser.getRole());

        return GIVE_RATING;
    }
    //SIMON
    @PostMapping("/rating/give_rating/{id}")
    public String giveRating(@PathVariable Integer id, @ModelAttribute Rating rating, Model model){

        ratingService.giveRating(rating);
        model.addAttribute("role", currentUser.getRole());

        return EVENT;
    }

}
