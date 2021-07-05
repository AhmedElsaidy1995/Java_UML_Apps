
package pyramidscsv;

import java.util.List;


public interface PyramidDAO {
    public List<Pyramid> getAllPyramids();
    public Pyramid createPyramid(String [] metadata);


}
