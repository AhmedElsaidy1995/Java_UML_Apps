
package pyramidscsv;

import java.util.List;

public class PyramidsCSV {


    public static void main(String[] args) {
        PyramidCSVDAO pDAO = new PyramidCSVDAO();
        List <Pyramid> pyramids = pDAO.readPyramidsFromCSV("src/pyramids.csv");
        
        int i=0;
        for(Pyramid p:pyramids){
            System.out.println("#"+(i++)+p);
        }
    }
    
}
