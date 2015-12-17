package services;

import entities.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slaan on 16.12.15.
 */
public class SubscriptionManager {

  private Map<String,List<Subscription>> subscriptionsMap;
  private int counter=0;

  public SubscriptionManager() {
    subscriptionsMap = new HashMap<>();
  }

  public void deleteSubscription(String sub) {
    //TODO Somehow delete a sub
  }

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

  public List<Subscription> getAllSubscriptions() {
    List<Subscription> result = new ArrayList<>();
    for (String s : subscriptionsMap.keySet()) {
      result.addAll(subscriptionsMap.get(s));
    }
    return result;
  }
}
