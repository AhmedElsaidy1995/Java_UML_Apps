import org.apache.commons.csv.CSVFormat;
import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.plot.swing.Histogram;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;

public class TitanicSMART {
    public static void main(String[] args) throws Exception {
        DataFrame trainingData = readCSV("src/main/resources/titanic.csv");

        trainingData = trainingData.select("survived","pclass","sex","age");

        trainingData = dataFrameEncoded(trainingData,"sex");

        EDA(trainingData);

        RandomForest model = RandomForest.fit(Formula.lhs("survived"), trainingData);
        System.out.println("Features' importance:");
        System.out.println(Arrays.toString(model.importance()));
        System.out.println(model.metrics ());

    }

    public static DataFrame readCSV(String path) throws IOException, URISyntaxException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        return Read.csv (path, format);
    }

    public static DataFrame dataFrameEncoded(DataFrame df, String columnName) {
        String[] values = df.stringVector(columnName).distinct().toArray (new String[]{});
        int[] pclassValues = df.stringVector (columnName).factorize (new NominalScale(values)).toIntArray ();
        df = df.merge (IntVector.of (columnName+" Encoded", pclassValues));
        df = df.drop(columnName);
        return df;
    }

    public static void EDA(DataFrame df) throws InterruptedException, InvocationTargetException, IOException {
        DataFrame titanicSurvived = DataFrame.of (df.stream ().filter (t -> t.get ("survived").equals (1)));
        DataFrame titanicNotSurvived = DataFrame.of (df.stream ().filter (t -> t.get ("survived").equals (0)));

        titanicSurvived = titanicSurvived.omitNullRows();
        titanicNotSurvived = titanicNotSurvived.omitNullRows();

        System.out.println(titanicSurvived.summary());
        System.out.println(titanicNotSurvived.summary());

        System.in.read();
        Histogram.of (titanicSurvived.doubleVector ("age").toDoubleArray (), 15, false)
                .canvas ().setAxisLabels ("Age", "Count")
                .setTitle ("Age among surviving passengers")
                .window ();
        System.in.read();
        Histogram.of (titanicSurvived.intVector ("pclass").toIntArray (), 4, true)
                .canvas ().setAxisLabels ("Class", "Count")
                .setTitle ("Class among Surviving Passengers")
                .window ();
        System.in.read();
        Histogram.of (titanicNotSurvived.doubleVector ("age").toDoubleArray (), 15, false)
                .canvas ().setAxisLabels ("Age", "Count")
                .setTitle ("Age Not Surviving Passengers")
                .window ();
        System.in.read();
        Histogram.of (titanicNotSurvived.intVector ("pclass").toIntArray (), 4, true)
                .canvas ().setAxisLabels ("Classes", "Count")
                .setTitle ("Class among Not Surviving Passengers")
                .window ();

        DataFrame titanicFemale = DataFrame.of (df.stream ().filter (t -> t.get ("sex Encoded").equals (0)));
        DataFrame titanicMale = DataFrame.of (df.stream ().filter (t -> t.get ("sex Encoded").equals (1)));

        titanicFemale = titanicFemale.omitNullRows();
        titanicMale = titanicMale.omitNullRows();
        System.in.read();
        Histogram.of (titanicFemale.intVector ("survived").toIntArray (), 2, true)
                .canvas ().setAxisLabels ("survived", "Count")
                .setTitle ("Surviving Frequency Female Passengers")
                .window ();
        System.in.read();
        Histogram.of (titanicMale.intVector ("survived").toIntArray (), 2, true)
                .canvas ().setAxisLabels ("survived", "Count")
                .setTitle ("Surviving Frequency Male Passengers")
                .window ();


    }
}
