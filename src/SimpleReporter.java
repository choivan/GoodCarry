import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 12/18/13
 * Time: 3:21 PM
 */
public class SimpleReporter implements IReporter {

    public void report(ArrayList<Match> matches) {
        System.out.println(matches);
    }
}
