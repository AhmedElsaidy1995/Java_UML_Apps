
package betterstringlambda;

import java.util.function.BiPredicate;


public class BetterStringLambda {

    public static void main(String[] args) {
        String string1 = "fff" ;
        String string2 = "ffffffff";
        String longer = StringUtils.betterString(string1, string2, (s1, s2) -> s1.length() > s2.length());  
        String first = StringUtils.betterString(string1, string2, (s1, s2) -> true);
        System.out.println(longer);
        System.out.println(first);

    }

    private static class StringUtils {

        public StringUtils() {
        }
        public static String betterString(String s1, String s2,BiPredicate<String,String> p){
            if(p.test(s1, s2))
                return s1;
            return s2;
        }
    }
    
}
