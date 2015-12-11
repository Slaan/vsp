package services;

import entities.Event;
import entities.Exceptions.EventIdNotFoundException;
import entities.Exceptions.GameIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController
public class RestApiController {

    private EventManager eventManager = new EventManager();

    public RestApiController() {
        System.out.println("RestApiController started");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Event>> getEvents(@RequestParam(value = "gameid") String gameid) {
        List<Event> result = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            result = eventManager.getEvents(gameid);
            status = HttpStatus.OK;
        } catch (GameIdNotFoundException e) {
            System.out.println("Gameid not on this service");
        }
        return new ResponseEntity<>(result,status);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/events")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvents(@RequestParam(value="gameid") String gameid) {
        eventManager.deleteEvents(gameid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createEvent(@RequestParam(value = "gameid") String gameid, @RequestBody Event event) {
        int id = eventManager.addEvent(gameid,event);
        return new ResponseEntity<>("/events/" + id, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Event> getEventById(@PathVariable("id") int eventid) {
        Event result = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            result = eventManager.getEventById(eventid);
            status = HttpStatus.OK;
        } catch (EventIdNotFoundException e) {
            System.out.println("Event not found on this service");
        }
        return new ResponseEntity<>(result, status);
    }
}
