import sun.awt.image.OffScreenImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * User: yifancai
 * Date: 12/16/13
 * Time: 6:42 PM
 */
public class Match implements Comparable<Match> {
    private String matchId;
    private String userId;
    private double hgRatio;

    public Match(String mid, String uid, String gold, String heroDamage) {
        matchId = mid;
        userId = uid;
        double g = 0,
               hd = 0;
        g = castValue(gold);
        hd = castValue(heroDamage);
        hgRatio = 0;
        if (g != 0) {
            hgRatio = hd / g;
        }
    }

    public Match(String mid, String uid) {
        matchId = mid;
        userId = uid;
        URL url;
        double g = 0,
               hd = 0;
        try {
            url = new URL("http://dotabuff.com/matches/" + mid);

            String oneLine; // matches table codes are in the same line

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String searchKey = "<td class=\"cell-extramedium\"><a href=\"/players/" + uid;
            while ((oneLine = br.readLine()) != null) {
                int start = oneLine.indexOf(searchKey);
                if (start != -1) {
                    String key = "<td class=\"cell-centered\">";
                    int keyLen = key.length();

                    int colIndex = 1;
                    while (true) {
                        int valueStart = oneLine.indexOf(key, start);
                        if (valueStart == -1) break;

                        valueStart += keyLen;
                        int valueEnd = oneLine.indexOf("<", valueStart);
                        start = valueEnd;
                        String value = oneLine.substring(valueStart, valueEnd);
                        if (colIndex == 5) { // gold
                            g = castValue(value);
                        }
                        else if (colIndex == 10) { // hero damage
                            hd = castValue(value);
                        }
                        ++colIndex;
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

        hgRatio = 0;
        if (g != 0) {
            hgRatio = hd / g;
        }
    }

    private double castValue(String s) {
        char lastChar = s.charAt(s.length() - 1);
        if (lastChar == 'k') { // k === 1000
            return 1000 * Double.parseDouble(s.substring(0, s.length() - 1));
        }
        else {
            return Double.parseDouble(s);
        }
    }

    @Override
    public String toString() {
        return  "\n" + "Match: " + matchId + " Usr: " + userId + " H/G: " + hgRatio;
    }

    @Override
    public int compareTo(Match o) {
        if (this.hgRatio < o.hgRatio)
            return 1;
        else if (this.hgRatio == o.hgRatio)
            return 0;
        else
            return -1;
    }
//Test
//    public static void main(String[] args) {
//        ArrayList<Match> ms = new ArrayList<Match>();
//        ms.add(new Match("1", "111", "123", "123"));
//        ms.add(new Match("2", "111", "12.3k", "123"));
//        ms.add(new Match("3", "111", "1.23k", "12.3k"));
//        Collections.sort(ms);
//        System.out.println(ms);
//    }
}
