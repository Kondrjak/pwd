import java.io.IOException;

/***
 * Class Password
 */
public class Password {
    // Object variables
    String password;
    boolean valid;

    // Class variables (start with static)
    static int minLen = 6;
    static int maxLen = 32;

    static char[] abc = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static char[] ABC = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    // next Variable starts with the Letter O, can't start with a number of course!
    static char[] O12 = {'0','1','2','3','4','5','6','7','8','9'};
    // special characters are all non-Number and non-Letter character
    static char[] SimpleChars = (abc.toString() + ABC.toString() + O12.toString()).toCharArray();

    /***
     *    Constructor checks if password is not too long or too short or contains any blank spaces.
     ***/
    public Password(String password) {
        this.password = password;
        // invalidate if too short
        valid  = (password.length() >= minLen);
        // invalidate if too long
        valid &= (password.length() <= maxLen);
        // invalidate if blanks were used
        valid &= !(this.password.contains(" "));
    }

    /**
     * Shows if the password is well defined (no blankets, not shorter than minLen, not larger than maxLen.
     */
    public boolean isValid(){ return valid; }

    /**
     *
     **/
    public static boolean[] elementwiseValidityArray(Password[] pwArray){
        boolean[] out = new boolean[pwArray.length];
        for(int i=0; i< pwArray.length; i++){
            out[i] = pwArray[i].isValid();
        }
        return out;
    }

    /**
     * Der Score (von bis zu 100%) setzt sich zusammen aus 50% für die Länge des Passwortes
     * und 50% danach ob Groß-, Kleinbuchstaben, Zahlen oder Sonderzeichen enthalten sind.
     * @return
     */
    public int getScore(){
        if (!this.valid){
            return 0;
        }else if(this.isOneOfTheMostCommonPasswords()){
            return -1;
        } else {
            return (int) (100.*(getLenScore()+getDiversityScore()));
        }
    }

    private boolean isOneOfTheMostCommonPasswords() {
        String[] mostCommonPasswords = new String[0];
        // webscrapping might fail
        try {
            mostCommonPasswords = MostCommonPasswordsWebScrapper.getMostCommonPasswords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String commonPassword: mostCommonPasswords){
            if (password==commonPassword) return true;
        }
        return false;
    }


    /**
     * Der LenScore steigt linear mit der Passwortlänge, ist 0 bei einer Lönge von minLen
     * und maximal 0.5 bei einer Länge von maxLen
     * @return
     */
    private double getLenScore() {
        double len = password.length();
        if (len - minLen < 0){
            return 0.;
        }
        return 0.5*(len - minLen)/(maxLen - minLen);
    }


    /**
     * Es gibt 5 Punkte jeweils für Groß/Kleinbuchstaben, 8 Punkte für Sonderzeichen und 2 Punkte für Zahlen
     * mit maximal 20 Punkten liegt der LenScore dann bei 0.5
     * @return
     */
    private double getDiversityScore(){
        return (5*this.hasUpperCase()+5*this.hasLowerCase()+2*this.hasNumbers()+8*this.hasSpecial())/40.;
    }

    /**
     * Checks if one of the given chars is contained in the password
     * @param charArray - given chars
     * @return double - 1. if contained, 0. if not
     */
    private double hasOneOf(char[] charArray) {
        for(char c: password.toCharArray()) {
            for(char d: charArray){
                if(c==d){
                    // char found
                    return 1.;
                }
            }
        }
        return 0.;
    }

    /**
     * Checks if the password contains any number.
     * @return double - 1. if number is present, 0. if no number is present
     */
    private double hasNumbers() {
        return this.hasOneOf(O12);
    }

    /**
     * Checks if the password contains any upper case letter.
     * @return double - 1. if upper case letter is present, 0. if no upper case letter is present
     */
    private double hasUpperCase() {
        return this.hasOneOf(ABC);
    }

    /**
     * Checks if the password contains any lower case letter.
     * @return double - 1. if upper case letter is present, 0. if no upper case letter is present
     */
    private double hasLowerCase() {
        return this.hasOneOf(abc);
    }


    /**
     * Checks if the password contains any special character.
     * @return double - 1. if special character is present, 0. if no special character is present
     */
    private double hasSpecial(){
        for(char c: password.toCharArray()){
            boolean charIsSimple = false;
            for(char d: SimpleChars){
                if (c==d){
                    charIsSimple = true;
                    break;
                };
            }
            if (charIsSimple == false){
                // Special Character found!
                return 1.;
            }
        }
        return 0.;
    }



}
