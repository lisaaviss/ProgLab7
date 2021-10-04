package Commands;

import Elements.Movie;

import java.util.ArrayList;
import java.util.Iterator;

import static Elements.MovieGenre.*;

/**
 * Класс подсчета количества фильмов по каждой категории жанров
 * @author Артём
 */
public class CommandGroupByGenre extends Command{
    public static Object action(ArrayList<Movie> list){
        Object message = "";
        Iterator value = list.iterator();
        int WESTERN1 = 0;
        int DRAMA1 = 0;
        int THRILLER1 = 0;;
        int HORROR1 = 0;;
        int SCIENCE_FICTION1 = 0;
        while (value.hasNext()){
            Object genre = ((Movie) value.next()).getGenre();
            if (WESTERN.equals(genre)) {
                WESTERN1++;
            } else if (DRAMA.equals(genre)) {
                DRAMA1++;
            } else if (THRILLER.equals(genre)) {
                THRILLER1++;
            } else if (HORROR.equals(genre)) {
                HORROR1++;
            } else if (SCIENCE_FICTION.equals(genre)) {
                SCIENCE_FICTION1++;
            }
        }
        message = "Количество WESTERN: " + WESTERN1 + "\nКоличество DRAMA: " + DRAMA1 + "\nКоличество THRILLER: " + THRILLER1 + "\nКоличество HORROR: " + HORROR1 + "\nКоличество SCIENCE_FICTION " + SCIENCE_FICTION1;
        return message;
    }

}
