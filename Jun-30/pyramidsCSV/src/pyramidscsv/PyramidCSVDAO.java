
package pyramidscsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class PyramidCSVDAO implements PyramidDAO {
    List<Pyramid> pyramids = new ArrayList<Pyramid>(); 
    public PyramidCSVDAO(){
    
    }
    public List<Pyramid> readPyramidsFromCSV(String filename){
        
        try {   
            BufferedReader br=new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while(line != null){
                line = br.readLine();
                if(line != null){
                    String[] attributtes = line.split(",");
                    pyramids.add(createPyramid(attributtes));
                }
            };
            pyramids.sort(Comparator.comparing(Pyramid::getHeight));
                
            Map<String,Integer> map = countFrequencies(pyramids);
            
            for(Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(PyramidCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return pyramids;
    }
    
    public static Map<String,Integer> countFrequencies(List<Pyramid> list)
    {
        Map<String,Integer> map = new HashMap<>();
        for (Pyramid s : list){
            if(!map.containsKey(s.getSite())){
                int c = 0 ;
                for(Pyramid p : list){
                    if(p.getSite().equals(s.getSite())){
                        c++;
                    }
                }
                map.put(s.getSite(), c);
            }   
        }     
        return map;   
    }
    
    @Override
    public Pyramid createPyramid(String [] metadata){
        Double height = 0.0;
        if(!metadata[7].equals("")){
            height = Double.parseDouble(metadata[7]);
        }
 
        return new Pyramid(metadata[2],Integer.parseInt(metadata[3]),metadata[4],metadata[12], Double.parseDouble(metadata[5]), height);   
    }

    @Override
    public List<Pyramid> getAllPyramids() {
        return pyramids;
    }
    
    

}
