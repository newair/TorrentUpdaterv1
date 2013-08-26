package newair.org.torrentupdaterandfilter.DistanceCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by newair on 7/20/13.
 */
public class DistanceMeasurer {


    public static String measure(String titleFromRSS, String savedListUnBroken) {

         titleFromRSS = titleFromRSS.toLowerCase();
        savedListUnBroken = savedListUnBroken.toLowerCase();

       ArrayList<String> savedList= StringManager.breakString(savedListUnBroken);

        List<String> wordsInRSSTitle = Arrays.asList(titleFromRSS.split(" "));
        HashMap<String, Double> scoreMap = new HashMap<String, Double>();
        HashMap<String, Boolean> result = new  HashMap<String, Boolean>();

        double maxValue=0;
        String savedSentence = null;
        for (String s : savedList) {


            String[] wordsInSavedTitle = s.split(" ");
            int numOfWordsInSavedTitle = wordsInSavedTitle.length;
            int numOfWordsMatchedInSavedTitle = 0;
            for (String wordInSavedTitle : wordsInSavedTitle) {

                //check saved title word is in wordsInRSSTitle and if so mark it

                if (wordsInRSSTitle.contains(wordInSavedTitle)) {
                    numOfWordsMatchedInSavedTitle++;
                }


            }

            double pcntgMapped = (numOfWordsMatchedInSavedTitle / numOfWordsInSavedTitle) * 100;
            scoreMap.put(s, pcntgMapped);

            if(pcntgMapped > maxValue)
            {
                maxValue=pcntgMapped;
                savedSentence=s;

            }

        }


        if(maxValue >= 50) {


            return savedSentence;

        }
     else return null;



    }

   /* public static void main(String[] args) {


        String titleFromRSS="Pixies Where Is My Mind";
        String[] savedList= {"where"};

        System.out.println(measure(titleFromRSS,savedList).toString());



    }*/





}
