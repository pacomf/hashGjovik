package hashfunction.hig.ull.com.gjoviksimilarhash.utils;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Paco on 11/11/2014.
 */
public class DataSet {

    public static ArrayList<String> gen;
    public static int hd;

    public static String[] comp1 = {"1110001001011100010011010101",
                                    "0100011101010101101011101111",
                                    "1111111101010101000001101111",
                                    "0011001100110011111100101010",
                                    "0001110011010101010100010101"};

    public static String[] comp2 = {"1110001001011100010011010101",
                                    "0101011100010101001011101111",
                                    "1110111101010111000001101011",
                                    "0011000100110011111100101010",
                                    "1110100010101010100011101010"};


    public static void generateUpToHD(String or, String cu, int position, int k, boolean upTo){
        char[] original = or.toCharArray();
        char[] current = cu.toCharArray();
        if (position == original.length){
            String newV = SpongeConstruction.XOR(String.copyValueOf(original), String.copyValueOf(current));
            if (!newV.equals(or)){
                if (upTo){
                    gen.add(newV);
                } else if (SpongeConstruction.getHammingDistance(newV, or) == DataSet.hd) {
                    gen.add(newV);
                }
            }
            return;
        }

        if (k > 0){
            current[position] = '1';
            generateUpToHD(String.copyValueOf(original), String.copyValueOf(current), position + 1, k - 1, upTo);
        }

        current[position] = '0';
        generateUpToHD(String.copyValueOf(original), String.copyValueOf(current), position + 1, k, upTo);
    }
}
