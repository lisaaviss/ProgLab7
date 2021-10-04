package Commands;

import Elements.Coordinates;
import Elements.Movie;
import Manager.Manager;

import java.util.ArrayList;

/**
 * Класс команды которая добавляет новый элемент в коллекцию
 */
public class CommandAdd extends Command{
    /**
     * Метод который добавляет новый элемент в коллекцию
     *
     * @param  - строка котрую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(ArrayList<Movie> collection, String line){
        Movie movie = new Movie();
        Coordinates coordinates = new Coordinates();
        String[] strok = line.split(",");
        movie.setName(strok[1]);
        movie.setCoordinates(strok[2], strok[3]);
        movie.setOscarsCount(strok[4]);
        movie.setTotalBoxOffice(strok[5]);
        movie.setGenre(strok[6]);
        movie.setMpaaRating(strok[7]);
        movie.setScreenwriter(strok[8], strok[9], strok[10], strok[11], strok[12]);
        movie.setUser(strok[13]);
        collection.add(movie);
        Manager manager = new Manager();
        return manager.add(line, collection);
    }
}