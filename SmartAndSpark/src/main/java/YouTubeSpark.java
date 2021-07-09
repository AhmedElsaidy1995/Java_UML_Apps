import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YouTubeSpark {
    private static final String COMMA_DELIMITER = ",";
    public static void main(String[] args) throws IOException {
        Logger.getLogger ("org").setLevel (Level.ERROR);
        SparkConf conf = new SparkConf ().setAppName ("wordCounts").setMaster ("local[2]");
        JavaSparkContext sparkContext = new JavaSparkContext (conf);
        JavaRDD<String> videos = sparkContext.textFile ("src/main/resources/USvideos.csv");
        processTitle(videos);
        processTag(videos);

    }

    private static void processTitle(JavaRDD<String> videos) {
        LocalTime start= LocalTime.now();
        titleCount(videos);
        LocalTime end= LocalTime.now ();
        Duration duration= Duration.between (start,end);
        System.out.println("processing time for titles is :"+duration.getNano ());
    }
    private static void processTag(JavaRDD<String> videos) {
        LocalTime start= LocalTime.now();
        tagCount(videos);
        LocalTime end= LocalTime.now ();
        Duration duration= Duration.between (start,end);
        System.out.println("processing time for tag is :"+duration.getNano ());
    }

    private static void titleCount(JavaRDD<String> videos) {
        JavaRDD<String> titles = videos
                .map (YouTubeSpark::extractTitle)
                .filter (StringUtils::isNotBlank);
        JavaRDD<String> words = titles.flatMap (title -> Arrays.asList (title
                .toLowerCase ()
                .trim ()
                .replaceAll ("\\p{Punct}", " ")
                .split (" ")).iterator ());
        System.out.println(words.toString ());
        Map<String, Long> wordCounts = words.countByValue ();
        List<Map.Entry> sorted = wordCounts.entrySet ().stream ()
                .sorted (Map.Entry.comparingByValue ()).collect (Collectors.toList ());
        for (Map.Entry<String, Long> entry : sorted) {
            System.out.println (entry.getKey () + " : " + entry.getValue ());
        }
    }

    private static void tagCount(JavaRDD<String> videos) {
        JavaRDD<String> titles = videos
                .map (YouTubeSpark::extractTag)
                .filter (StringUtils::isNotBlank);
        JavaRDD<String> words = titles.flatMap (title -> Arrays.asList (title
                .toLowerCase ()
                .trim ()
                .replaceAll ("\\p{Punct}", " ")
                .split (" ")).iterator ());
        System.out.println(words.toString ());
        Map<String, Long> wordCounts = words.countByValue ();
        List<Map.Entry> sorted = wordCounts.entrySet ().stream ()
                .sorted (Map.Entry.comparingByValue ()).collect (Collectors.toList ());
        for (Map.Entry<String, Long> entry : sorted) {
            System.out.println (entry.getKey () + " : " + entry.getValue ());
        }
    }

    public static String extractTitle(String videoLine) {
        try {
            return videoLine.split (COMMA_DELIMITER)[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }
    public static String extractTag(String videoLine) {
        try {
            return videoLine.split (COMMA_DELIMITER)[6];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }
}
