package Commands;

import Elements.Movie;

import java.util.ArrayList;
import java.util.Collections;

public class CommandShuffle {
    public static Object action(ArrayList<Movie> list) {
        Collections.shuffle(list);
        return "";
    }
}
