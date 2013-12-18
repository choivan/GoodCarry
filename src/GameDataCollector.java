import java.util.ArrayList;
import java.util.Collections;

import static java.util.Collections.*;

/**
 * User: yifancai
 * Date: 12/16/13
 * Time: 6:33 PM
 */
public class GameDataCollector implements Runnable {
    private IReporter reporter;
    private String userID;
    private ArrayList<Match> matches;

    public GameDataCollector(IReporter r, String uid) {
        setReporter(r);
        setUserID(uid);
        matches = new ArrayList<Match>();
    }

    public void setReporter(IReporter r) {
        reporter = r;
    }

    public void setUserID(String id) {
        userID = id;
    }

    @Override
    public void run() {
        System.out.println("Collecting...");

        int pageNum = 1;
        String baseUrl = "http://dotabuff.com/players/" + userID + "/matches?page=";
        while (true) {
            System.out.println("Analyzing... page " + pageNum);
            DotaBuffPage page = new DotaBuffPage(baseUrl + pageNum, userID);
            if (page.hasMatch()) {
                System.out.println("Analyzed... page " + pageNum);
                matches.addAll(page.getMatches());
            }
            else {
                System.out.println("Done...");
                break;
            }

            ++pageNum;
        }
        Collections.sort(matches);

        reporter.report(matches);
    }
}
