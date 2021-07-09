import tech.tablesaw.api.Table;

import java.io.IOException;

public class TablesawTitanic {
    public static void main(String[] args) throws IOException {
        Table titanicData = Table.read ().csv("src/main/resources/titanic.csv");
        Table summary = titanicData.summary ();
        System.out.println(summary);

    }
}
