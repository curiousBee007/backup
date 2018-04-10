/**
 * Created by Anna on 5/23/17.
 */
public class ASCIICode {

    public static boolean letterWritten(String newspaper, String letter){
        if(newspaper == null || newspaper.length() == 0)
            return false;

        if(letter == null || letter.length() == 0)
            return true;

        int newLen = newspaper.length();
        int letterLen = letter.length();

        if(letterLen > newLen)
            return false;

        int asciiTable[] = new int[256];
        for(int i = 0 ; i< newLen; i++){
            int x = (int)(newspaper.charAt(i));
            asciiTable[x] = asciiTable[x] +1;
        }

        for(int j = 0 ; j < letterLen; j++){
            int y = (int)(letter.charAt(j));
            asciiTable[y] = asciiTable[y] - 1;
            if(asciiTable[y] < 0)
                return false;
        }
        return true;
    }

    static public void main( String args[] ) {
        System.out.println( "Hello Pramp!" );
        String letter = "anu";
        String newsPaper = "Nikhilan";
        boolean flag = letterWritten(newsPaper,letter);
        System.out.println(flag);

    }
}
