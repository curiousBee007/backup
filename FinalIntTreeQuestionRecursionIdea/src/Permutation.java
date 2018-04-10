import java.util.Arrays;

/**
 * Created by Anna on 5/24/17.
 */
public class Permutation {

    public void printPermutation(String s){
        char s1[] = s.toCharArray();
        perm1(s1,0,s.length()-1);
    }

    public void swap(char s[], int i , int j){

        char temp =s[i];
        s[i] = s[j];
        s[j] = temp;


    }

    public char[] sort(char s[],int x,int y){

        int len = y - x;
        if(len <= 1)
            return s;

        char temp1[] = new char[len];
        char temp2[] = new char[x-1];

        int j = 0;
        for(int k = 0; k < x;k++){
            temp2[k] = s[k];
        }


        for(int i = x; i <= y ;i++){
            temp1[j] = s[i];
            j++;
        }

        Arrays.sort(temp1);

        String newString = temp2.toString() +temp1.toString();
        s = newString.toCharArray();

        return s;

    }

   //Lexicographic
    public void perm1(char s[], int start,int end){

        if(start == end) {
            System.out.println(s);
            return;
        }

        for(int i = start; i <= end;i++){
            swap(s,start,i);
            //char temp[] =s;
            //sort(s,start+1,end);
            //System.out.println(s);
            // sort bw start + 1 and end
            perm1(s,start +1,end);
            // restore
            //s = temp;
            swap(s,start,i);

        }

    }

    public static void main(String[] args){

        String s = "123";
        Permutation obj = new Permutation();
        obj.printPermutation(s);

    }
}
