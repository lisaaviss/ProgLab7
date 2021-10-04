package Manager;

import Elements.Movie;

import javax.swing.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Класс который работает с коллекцией
 */
public class Manager {

    /**
     * Заполнение коллекции при первом запуске
     *
     * @param element    - элемент из БД
     * @param collection - коллекция
     */
    public void fill(String element, ArrayList<Movie> collection) throws NumberFormatException{
        String[] fields;
        int index;
        Movie movie = new Movie();
        fields = element.split(",");

        for (index = 0; index < fields.length; index++) {
            if (fields[index].equals("null")) {
                fields[index] = null;
            }
        }
        movie.setId(fields[0]);
        movie.setName(fields[1]);
        movie.setCoordinates(fields[2], fields[3]);
        movie.setCreationDate(fields[4]);
        movie.setOscarsCount(fields[5]);
        movie.setTotalBoxOffice(fields[6]);
        movie.setGenre(fields[7]);
        movie.setMpaaRating(fields[8]);
        movie.setScreenwriter(fields[9], fields[10], fields[11], fields[12], fields[13]);
        movie.setUser(fields[14]);
        collection.add(movie);
    }

    /**
     * Добавляет новый файл в коллекцию
     *
     * @param element    - элемент который вводят с консоли
     * @param collection - коллекция
     */
    public Object add (String element, ArrayList<Movie> collection){
        Object message = "";
        ZonedDateTime zona = ZonedDateTime.now();
        String[] fields;
        int index;
        int max = 2147483647;
        int min = 0;
        int aid = (int) Math.round(Math.random() * (max - min + 1) + min);
        fields = element.split(",");
            for (index = 0; index < fields.length; index++) {
                if (fields[index].equals("null")) {
                    fields[index] = null;
                }
            }
        System.out.println(element);
            String command = ("INSERT INTO Movie (id, name, coordinate_X, coordinate_Y, creationDate, oscarsCount, " +
                    "totalBoxOffice, genre, mpaaRating, screenwriter_name, screenwriter_height, screenwriter_eyeColor, screenwriter_hairColor, " +
                    "screenwriter_nationality, \"user\") VALUES (nextval('randomid'), '" + fields[1] + "', " + fields[2] + ", " + fields[3] + ", '" +
                    zona + "', " + fields[4] + ", " + fields[5] + ", '" + fields[6] + "', '" + fields[7] + "', '" + fields[8] +
                    "', " + fields[9] + ", '" + fields[10] + "', '" + fields[11] + "', '" + fields[12] + "', '"+ fields[13] + "');");
        System.out.println(command);
            message = DateBase.executeCommand(command);
            DateBase.load(collection);
        return message;
    }

    /**
     * Устанавливает id и CreationDate элементу коллекции
     *
     * @param element - неполный элемент коллекции
     * @return - полный элемент коллекции
     */
    public Movie set (String element, String user) {
        Movie movie = new Movie();
        String[] fields;
        fields = element.split(",");
        ZonedDateTime zona = ZonedDateTime.now();
        String id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
        movie.setId(id);
        movie.setName(fields[0]);
        movie.setCoordinates(fields[1], fields[2]);
        movie.setCreationDate(fields[3]);
        movie.setOscarsCount(fields[4]);
        movie.setTotalBoxOffice(fields[5]);
        movie.setGenre(fields[6]);
        movie.setMpaaRating(fields[7]);
        movie.setScreenwriter(fields[8], fields[9], fields[10], fields[11], fields[12]);
        movie.setUser(user);
        return (movie);
    }
}
