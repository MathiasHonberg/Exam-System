package com.example.demo.Services;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService {

    //EVENTS
    List<Event> getEvents();
    Event addEvent(Event event);
    Event readEvent(int id);
    Event editEvent(int id, Event event);
    boolean deleteEvent(int id);

    //KITCHENS
    List<Kitchen> getKitchens();
    Kitchen addKitchen(Kitchen kitchen);
    Kitchen readKitchen(int id);
    Kitchen editKitchen(Kitchen kitchen);
    boolean deleteKitchen(int id);
    void addKitchenToEvent(int id);

    //JUDGE
    List<Judge> getJudges();
    Judge addJudge(Judge judge);
    Judge readJudge(int id);
    Judge editJudge(Judge judge);
    boolean deleteJudge(int id);

    //LOGGEDIN USER
    boolean loginMatch(User user);
    User loggedIn(User user);

    //CONFIRM
    void confirmKitchen(int id);
    boolean confirmJudge(Judge judge);
}
