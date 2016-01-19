import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 19.01.2016.
 */
public class testMain {

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(0);
    list.add(1);
    list.add(2);
    list.add(3);
    System.out.println(list.get(0));
    list.remove(0);
    System.out.println(list.get(0));
  }
}
