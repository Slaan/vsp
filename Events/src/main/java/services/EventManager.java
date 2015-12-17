package services;

import entities.Event;
import entities.Exceptions.EventIdNotFoundException;
import entities.Exceptions.GameIdNotFoundException;
import entities.Subscription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
public class EventManager {


  private Map<String, List<Event>> eventMap;
  private List<Event> allEvents;
  private int counter = 0;
  private SubscriptionManager subscriptionManager;

  public EventManager(SubscriptionManager subscriptionManager) {
    eventMap = new HashMap<>();
    allEvents = new ArrayList<>();
    this.subscriptionManager = subscriptionManager;
  }

  /**
   * Adds an event to a GameId.
   *
   * @param gameid gameId the event shall be added
   * @param event  event to be added
   * @return returns id of event
   */
  public int postEvent(String gameid, Event event) {
    List<Event> events;
    if (eventMap.containsKey(gameid)) {
      events = eventMap.get(gameid);
    } else {
      events = new ArrayList<>();
    }
    if (counter == Integer.MAX_VALUE) {
      counter = 0;
    }
    event.setId(++counter);
    events.add(event);
    allEvents.add(event);
    eventMap.put(gameid, events);
    return counter;
  }

  /**
   * Grabs all Events beloning to a Game.
   *
   * @param gameid gameid of the game from which we grab the Events
   * @return List of Events belonging to the gameid
   */
  public List<Event> getEvents(String gameid) throws GameIdNotFoundException {
    if (eventMap.containsKey(gameid)) {
      return eventMap.get(gameid);
    } else {
      throw new GameIdNotFoundException("gameId not on this server");
    }
  }

  /**
   * Removes all Events belonging to a game.
   *
   * @param gameid gameid of the Game that will be removed with its Events
   */
  public void deleteEvents(String gameid) {
    if (eventMap.containsKey(gameid)) {
      List<Event> events = eventMap.get(gameid);
      for (Event e : events) {
        allEvents.remove(e);
      }
    }
    eventMap.remove(gameid);
  }


  /**
   * returns event information belonging to given eventid
   *
   * @param eventId id of the event
   * @return
   */
  public Event getEventById(int eventId) throws EventIdNotFoundException {
    for (Event e : allEvents) {
      if (e.getId() == eventId) {
        return e;
      }
    }
    throw new EventIdNotFoundException("Event Id not present on this service");
  }
}
