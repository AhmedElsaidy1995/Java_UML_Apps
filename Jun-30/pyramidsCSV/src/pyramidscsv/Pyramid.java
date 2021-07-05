
package pyramidscsv;

public class Pyramid {
    private String modern_name;
    private int dynasty;
    private String site;
    private String type;
    private Double lenght;
    private Double height;
    

    Pyramid(String name,int dynasty,String site,String type,Double lenght,Double height){
        this.modern_name = name;
        this.dynasty = dynasty;
        this.site = site;
        this.type = type;
        this.lenght = lenght;
        this.height = height;
    }
    
    @Override
    public String toString() {
        return " Modern Name: "+modern_name+" From Dynasty "+dynasty+" at "+site+" Height: "+height ;
    }
    
    public String getModern_name() {
        return modern_name;
    }

    public void setModern_name(String modern_name) {
        this.modern_name = modern_name;
    }

    public int getDynasty() {
        return dynasty;
    }

    public void setDynasty(int dynasty) {
        this.dynasty = dynasty;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    /**
     * @return the height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Double height) {
        this.height = height;
    }
        
}
