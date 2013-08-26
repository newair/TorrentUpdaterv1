package newair.org.torrentupdaterandfilter.DistanceCalculator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by newair on 7/21/13.
 */
public class StringManager {

   private static String separateChar = "~";
    public static String constructString(String[] torrents) {


        StringBuilder builder = new StringBuilder();
        for (String s : torrents) {

            builder.append(s);
            builder.append(separateChar);

        }

        String appendedString =builder.toString();
        return appendedString.substring(0,appendedString.length()-1);
    }

    public static ArrayList<String> breakString(String monitoringTorrentsAsString) {

       ArrayList<String> monitoringTorrents = new ArrayList<String>();
        if (!monitoringTorrentsAsString.equals("")) {

            String[] torrentsArray = monitoringTorrentsAsString.split(separateChar);
            Collections.addAll(monitoringTorrents, torrentsArray);
        }

        return monitoringTorrents;
    }

}
