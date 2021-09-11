public class Password {
    String password;
    boolean valid;

    static int minLen = 6;
    static int maxLen = 32;

    static char[] abc = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static char[] ABC = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static char[] O12 = {'0','1','2','3','4','5','6','7','8','9'};
    static char[] SimpleChars = (abc.toString() + ABC.toString() + O12.toString()).toCharArray();

    /***
     *    Constructor checks if password is not too long or too short or contains any blank spaces.
     ***/
    public Password(String password) {
        this.password = password;
        valid  = (password.length() >= minLen);
        valid &= (password.length() <= maxLen);
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
    public double getScore(){
        if (!this.valid){
            return 0.d;
        } else {
            return (int) (100.*(getLenScore()+getDiversityScore()));
        }
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
