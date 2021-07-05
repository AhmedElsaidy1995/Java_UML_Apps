
package alphabetonly;


public class AlphabetOnly {

    public static void main(String[] args) {
        String s1 = "asdd666";
        String s2 = "asddassss";
        
        System.out.println(isAlphabetOnly(s2));
    }
    
    public static boolean isAlphabetOnly(String s){
        if(s.isEmpty() || s.equals(null))
            return false;
        if(s.chars().allMatch(Character::isLetter))
            return true;
        return false;
    }
    
}
