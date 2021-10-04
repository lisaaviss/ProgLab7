package Commands;

import Elements.Coordinates;
import Elements.Movie;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс выводящий самое маленькое значения поля name
 * @author Артём
 */
public class CommandMinByName extends Command {
    public static Object action(ArrayList<Movie> list) {
        System.out.println(list.size());
        int minName = list.get(0).getName().hashCode();
        Movie o = list.get(0);
        int i;

        for (i=1; i<list.size(); i++){
            if (minName > list.get(i).getName().hashCode()){
                minName = list.get(i).getName().hashCode();
                o = list.get(i);
            }
            System.out.println(list.get(i));
        }
        Object message;
        message = "id: " + o.getId();
        message = message + "\n" + "name: " + o.getName();
        message = message + "\n" + "coordinates: X: " + o.getCoordinates().getX() + " Y: " + o.getCoordinates().getY();
        message = message + "\n" + "creationDate: " + o.getCreationDate().toString();
        message = message + "\n" + "OscarsCount: " + o.getOscarsCount();
        message = message + "\n" + "totalBoxOffice: " + o.getTotalBoxOffice();
        message = message + "\n" + "genre: " + o.getGenre();
        message = message + "\n" + "mpaarating: " + o.getMpaaRating();
        message = message + "\n" + "Screenwriter's name: " + o.getScreenwriter().getName() + ", Screenwriter's height: " + o.getScreenwriter().getHeight() + ", Screenwriter's eyecolor: " + o.getScreenwriter().getEyeColor() + ", Screenwriter's haircolor: " + o.getScreenwriter().getHairColor() + ", Screenwriter's nationality: " + o.getScreenwriter().getNationality();
        return message;
    }
    }