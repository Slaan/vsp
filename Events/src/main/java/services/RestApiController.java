package services;

import entities.Event;
import entities.Exceptions.EventIdNotFoundException;
import entities.Exceptions.GameIdNotFoundException;
import entities.Subscription;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController public class RestApiController {

  private EventManager eventManager;
  private SubscriptionManager subscriptionManager;

  public RestApiController() {
    subscriptionManager = new SubscriptionManager();
    eventManager = new EventManager(subscriptionManager);
    System.out.println("RestApiController started");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/events") @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Event>> getEvents(@RequestParam(value = "gameid") String gameid) {
    List<Event> result = null;
    HttpStatus status = HttpStatus.BAD_REQUEST;
    try {
      result = eventManager.getEvents(gameid);
      status = HttpStatus.OK;
    } catch (GameIdNotFoundException e) {
      System.out.println("Gameid not on this service");
    }
    return new ResponseEntity<>(result, status);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/events") @ResponseStatus(HttpStatus.OK)
  public void deleteEvents(@RequestParam(value = "gameid") String gameid) {
    eventManager.deleteEvents(gameid);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/events")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> createEvent(@RequestParam(value = "gameid") String gameid,
      @RequestBody Event event, UriComponentsBuilder b) {
    int id = eventManager.postEvent(gameid, event);
    String uri = "";
    UriComponents uriComponents = b.path(uri).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<>("/events/" + id, headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/events/{id}") @ResponseStatus(HttpStatus.OK)
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

  @RequestMapping(method = RequestMethod.GET, value = "/events/subscriptions")
  @ResponseStatus(HttpStatus.OK) public ResponseEntity<List<Subscription>> getAllSubscriptions() {
    List<Subscription> result = subscriptionManager.getAllSubscriptions();
    return new ResponseEntity<List<Subscription>>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/events/subscriptions")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity postForSubscription(@RequestBody Subscription sub, UriComponentsBuilder b) {
    subscriptionManager.addSubscription(sub);
    String uri = "";
    UriComponents uriComponents = b.path(uri).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/events/subscriptions/{id}")
  @ResponseStatus(HttpStatus.OK) public void deleteSubscription(@PathVariable String sub) {
    subscriptionManager.deleteSubscription(sub);
  }

}
