package me.yamakaja.irc.client.network.util;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * Joins part of an array of strings into a string
     *
     * @param array The array of which to take the strings
     * @param delim What to put between the strings
     * @param from  From where to start (inclusive)
     * @param to    Until where to go (inclusive)
     * @return The joined array elements as a string
     */
    public static String join(String[] array, String delim, int from, int to) throws ArrayIndexOutOfBoundsException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = from; i < to; i++) {
            stringBuilder.append(array[i]);
            stringBuilder.append(delim);
        }
        stringBuilder.append(array[to]);
        return stringBuilder.toString();
    }

}
