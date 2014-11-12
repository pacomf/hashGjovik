package hashfunction.hig.ull.com.gjoviksimilarhash.utils;

import java.math.BigInteger;

/**
 * Created by Paco on 11/11/2014.
 */
public class SpongeConstruction {

    public static String initState(int r, int c){
        int dim = r+c;
        String state="";
        for (int i=0; i<dim; i++){
            state += "0";
        }
        return state;
    }

    public static String[] getArrayR (String input, int r){
        if ((input.length()%r) != 0){
            input = addPadding(input, input.length()%r);
        }
        return splitStringEvery(input, r);
    }

    public static String addPadding (String input, int extra){
        for (int i=0; i<extra; i++){
            input += input + "0";
        }
        return input;
    }

    public static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    private static boolean bitOf(char in) {
        return (in == '1');
    }

    private static char charOf(boolean in) {
        return (in) ? '1' : '0';
    }

    public static String XOR (String s1, String s2){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s1.length(); i++) {
            sb.append(charOf(bitOf(s1.charAt(i)) ^ bitOf(s2.charAt(i))));
        }

        return sb.toString();
    }

    public static String function (String input, int total){
        Integer value = Integer.parseInt(new StringBuilder(input).reverse().toString(), 2);
        value = (value+1)%(int) Math.pow((double) 2, (double) total);
        return new StringBuilder(String.format("%"+total+"s", Integer.toBinaryString(value)).replace(' ', '0')).reverse().toString();
    }

    // i -> i+1
    /*public static String function (String input, int total){
        Integer value = Integer.parseInt(new StringBuilder(input).reverse().toString(), 2);
        value = (value+1)%(int) Math.pow((double) 2, (double) total);
        return new StringBuilder(String.format("%"+total+"s", Integer.toBinaryString(value)).replace(' ', '0')).reverse().toString();
    }*/

    public static String getCfromState (String state, int r){
        return state.substring(0, state.length()-r);
    }

    public static String getRfromState (String state, int r){
        return state.substring(state.length()-r, state.length());
    }

    public static String updateStateInput (String state, String input){
        String rState = getRfromState(state, input.length());
        String cState = getCfromState(state, input.length());
        String rXored = XOR(rState, input);
        return cState+rXored;
    }

    public static String absorbing (String input, int r, int c){
        String state = initState(r, c);
        String [] inputR = getArrayR(input, r);
        for (String rInput: inputR){
            state = updateStateInput(state, rInput);
            state = function(state, r+c);
        }
        return state;
    }

    public static String squeezing (String state, int r, int c, int n){
        String output = "";
        output += getRfromState(state, r);
        while (output.length()<n){
            state = function(state, r+c);
            output += getRfromState(state, r);
        }
        output = output.substring(0, n);
        return output;
    }

    public static int getHammingDistance(String s1, String s2){
        if (s1.length() != s2.length()){
            return -1;
        }

        int counter = 0;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                counter++;
        }

        return counter;
    }


}
