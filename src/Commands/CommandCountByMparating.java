package Commands;

import Elements.Movie;
import Elements.MpaaRating;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Класс подсчета количества фильмов с опрделенным ограничением по возрасту
 * @author Артём
 */
public class CommandCountByMparating extends Command{
    public static Object action(ArrayList<Movie> list, String line){
        Object message = "";
        String[] mpArray = line.split(",");
        Scanner in = new Scanner(System.in);
        String slobo = mpArray[1];
        MpaaRating mpaaRating;
        mpaaRating = MpaaRating.valueOf(slobo);
        Iterator value = list.iterator();
        int i = 0;
        while (value.hasNext()) {
            if (((Movie) value.next()).getMpaaRating().equals(mpaaRating)){
                i++;
            }
        }
        message = i + "";
        return message;
    }
}
