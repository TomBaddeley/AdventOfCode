
import java.util.HashSet;
import java.util.Set;

public class PasswordGenerator {

    public static Set<String> generatePasswords(int min,int max){
        Set<String> passwords = new HashSet<>();

        for (int i = min; i <= max; i++){
            String num = i +"";

            boolean hasDoube = false;
            for(int j =0; j < num.length()-1; j++){
                //check there is 2 repeated digits but not 3
                if(num.charAt(j) == num.charAt(j+1) && !(j < num.length()-2 && num.charAt(j) == num.charAt(j+2))
                && !(j > 0 && num.charAt(j-1) == num.charAt(j+1))) {
                    hasDoube = true;
                    break;
                }
            }
            if(!hasDoube) continue;

            boolean increasingDigits = true;
            for(int j =0; j < num.length()-1; j++){
                if(num.charAt(j) > num.charAt(j+1)) {
                    increasingDigits = false;
                    break;
                }
            }
            if (increasingDigits) passwords.add(num);
        }

        return passwords;


    }



    public static void main(String[] args){
        System.out.println(generatePasswords(146810,612564).size());
    }
}
