package Commands;

import Elements.Movie;
import com.sun.xml.internal.ws.api.model.MEP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * Класс enum с удалением элемента из коллекции по его id
 * @author Артём
 */
public class CommandRemoveById extends Command {
    public static Object action(String command, ArrayList<Movie> collection, String user){
        Object message = "";
        String[] fields;
        fields = command.split(",");
        int size = collection.size();
        if (fields.length == 2){
            try {
                collection.removeAll(collection.stream().filter((mb) -> mb.getUser().equals(user)).filter((mb) -> mb.getId() == (Long.parseLong(fields[1]))).collect(Collectors.toList()));
                if (size != collection.size()) message = "Элементы с такм id были успешно удалены";
                else message = "Не найдено элементов с таким id";
                message = message + "\n";
            } catch (NumberFormatException e) {
                message = "id неверный формат строки!";
                message = message + "\n";
            }
        } else {
            message = "Неверный формат ввода данных";
            message = message + "\n";
        }
        return message;
    }
}
