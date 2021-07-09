import joinery.DataFrame;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class JoineryTitanic {
    public static void main(String args[]) throws IOException {
        DataFrame<Object> df1 = DataFrame.readCsv("src/main/resources/titanic.csv")
                .retain ("pclass", "survived", "sex" , "age")
                .groupBy (row -> row.get (2))
                .describe();
        System.out.println ("Stats about data grouped by sex");
        System.out.println (df1.toString ());
        System.out.println ("=========================================================================================");
        DataFrame<Object> df2 = DataFrame.readCsv("src/main/resources/titanic.csv")
                .retain ("home.dest")
                .dropna()
                .groupBy(row -> row.get (0))
                .count()
                .sortBy("home.dest");

        System.out.println (df2.toString ());


    }
}
