package Commands;

import Elements.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Класс, который выводит коллекцию
 * @author Артём
 */
public class CommandShow extends Command{
    public static Object action (ArrayList<Movie> list) {
        Object message= "";
        Movie[] arr;
        arr = list.toArray(list.toArray(new Movie[0]));
        for (int i =0; i < list.size(); i++){
            if (i == 0 && i == list.size()-1){
                message = (arr[i].show() + "\n");
            } else if (i == 0) {
                message = (arr[i].show());
            } else if (i == list.size()-1) {
                message = (message + "\n" +  arr[i].show() + "\n");
            } else message = (message + "\n" + arr[i].show());
        }
        if (message.equals("")) message = "Коллекция пустая\n";
        return message;
    }
}