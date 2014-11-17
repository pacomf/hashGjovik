package hashfunction.hig.ull.com.gjoviksimilarhash.utils;

/**
 * Created by Paco on 17/11/2014.
 */
public class MerkleDamgard {

    private static boolean bitOf(char in) {
        return (in == '1');
    }

    private static char charOf(boolean in) {
        return (in) ? '1' : '0';
    }

    public static String addPadding (String input, int extra){
        for (int i=0; i<extra; i++){
            input += input + "0";
        }
        return input;
    }

    public static String XOR (String s1, String s2){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s1.length(); i++) {
            sb.append(charOf(bitOf(s1.charAt(i)) ^ bitOf(s2.charAt(i))));
        }

        return sb.toString();
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

    public static String function (String input, int total){
        Integer value = Integer.parseInt(new StringBuilder(input).reverse().toString(), 2);
        value = (value+1)%(int) Math.pow((double) 2, (double) total);
        return new StringBuilder(String.format("%"+total+"s", Integer.toBinaryString(value)).replace(' ', '0')).reverse().toString();
    }

    public static String apply (String input, String IV){
        int tamSplit = IV.length();
        if ((input.length()%tamSplit) != 0){
            input = addPadding(input, input.length()%tamSplit);
        }
        String[] splitted = splitStringEvery(input, tamSplit);
        String in, out=IV;
        for (String block: splitted){
            in = XOR(out, block);
            out = function(in, tamSplit);
        }
        return out.substring(0, tamSplit);
    }
}
