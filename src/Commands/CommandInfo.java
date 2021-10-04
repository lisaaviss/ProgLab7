package Commands;

import Elements.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Класс вывода информации по коллекции
 * @author Артём
 */
public class CommandInfo extends Command {
    public static Object action(ArrayList<Movie> list, LocalDateTime time_create){
        Object message= ("Тип коллекции: java.util.ArrayList" +
                "\nДата создания: " + time_create +
                "\nСтруктура элемента: {id, name, coordinates.x, coordinates.y, creationDate, " +
                "numberOfParticipants, albumsCount, establishmentDate, genre, frontMan.name, frontMan.weight, " +
                "frontMan.eyeColor, frontMan.hairColor, frontMan.nationality}" +
                "\nКолличество элементов: " + list.size() + "\n");
        return message;
    }
}
