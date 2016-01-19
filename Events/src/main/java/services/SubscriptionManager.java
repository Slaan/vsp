package services;

import entities.Event;
import entities.Subscription;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slaan on 16.12.15.
 */
public class SubscriptionManager {

  // Mapping GameId -> List of Subscriptions
  private Map<String,List<Subscription>> subscriptionsMap;
  private int counter=0;

  public SubscriptionManager() {
    subscriptionsMap = new HashMap<>();
  }

  public void deleteSubscription(String sub) {
    //TODO Somehow delete a sub
  }

  /**
   * Adds a Subscription for an interested party towards an Event
   * @param sub
   */
  public void addSubscription(Subscription sub) {
    if (counter==Integer.MAX_VALUE) {
      counter = 0;
    }
    sub.setId(counter);
    counter ++;
    if (!subscriptionsMap.containsKey(sub.getGameid())) {
      List<Subscription> subs = new ArrayList<>();
      subs.add(sub);
      subscriptionsMap.put(sub.getGameid(),subs);
    } else {
      subscriptionsMap.get(sub.getGameid()).add(sub);
    }
  }

  /**
   * Returns all currently saved subscriptions.
   * @return List of all currently saved subscriptions
   */
  public List<Subscription> getAllSubscriptions() {
    List<Subscription> result = new ArrayList<>();
    for (String s : subscriptionsMap.keySet()) {
      result.addAll(subscriptionsMap.get(s));
    }
    return result;
  }

  /**
   * Informs all Subscriber interested in the Incoming Event
   * @param gameid Id of the Game the Event happened in
   * @param event Event that took place
   */
  public void informAboutEvent(String gameid, Event event) {
    List<Subscription> subs = subscriptionsMap.get(gameid);
    for (Subscription s : subs) {
      if (matchEvents(event, s.getEvent())) {
        informSubscriber(s.getCallbackuri(),event);
      }
    }
  }

  /**
   * Informs a Subscriber that an Event that he is interested in took place.
   * @param callbackuri Uri of the subscriber.
   * @param event Event that took place.
   */
  private void informSubscriber(String callbackuri, Event event) {
    RestTemplate template = new RestTemplate();
    template.postForEntity(callbackuri, null, null, event);
  }

  /**
   * Confirms if an incoming event matches the Event defined as regEx in the Subscription Event.
   * @param event Incoming Event
   * @param subEvent Description of an Event the subscriber is interested in.
   * @return
   */
  private boolean matchEvents(Event event, Event subEvent) {
    boolean result = false;
    //TODO do matching for all fields!

    return result;
  }
}
