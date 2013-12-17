
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 12/16/13
 * Time: 7:11 PM
 */
public class DotaBuffPage {
    private ArrayList<Match> matches;

    public DotaBuffPage(String urlStr, String uid) {
        matches = new ArrayList<Match>();
        URL url;
        try {
            url = new URL(urlStr);

            String oneLine; // matches table codes are in the same line

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((oneLine = br.readLine()) != null) {
//                System.out.println(oneLine);
                int startIndex = 0;
                if (oneLine.contains("class=\"matchid\">")) {
                    while (true) {
                        int idStart = oneLine.indexOf("class=\"matchid\">", startIndex);
                        if (idStart == -1) break;

                        idStart += 16; // 16: length of "class=\"matchid\">"
                        int idEnd = oneLine.indexOf("<", idStart);
                        startIndex = idEnd;
                        String matchId = oneLine.substring(idStart, idEnd);

                        matches.add(new Match(matchId, uid));
                    }
                    break;
                }
            }

            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean hasMatch() {
        return !matches.isEmpty();
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }
//Test
//    public static void main(String[] args) {
//        DotaBuffPage page = new DotaBuffPage("http://dotabuff.com/players/99541693/matches?page=36", "99541693");
//    }

}
