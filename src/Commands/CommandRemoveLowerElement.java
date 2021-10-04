package Commands;

import Elements.Movie;
import Manager.Manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * Класс удаляющий из коллекции все элементы, меньшие, чем заданный
 * @author Артём
 */

public class CommandRemoveLowerElement extends Command{
    public static Object action(String command, ArrayList<Movie> collection, String user){
        Manager manager = new Manager();
        Object message = "";
        String[] field;
        int size = collection.size();
        int index;
        String element;
        field = command.split(" ");
        if (field.length == 1){
            message = "Элемент отсутствует";
            message = message + "\n";
        } else  if (field.length >= 2){
            element = field[1];
            if (field.length > 2){
                for (index = 2; index<field.length; index++) {
                    element = element + " " + field[index];
                }
            }
            if (element.split(",").length == 12) {
                String elementPrepared = element;
                collection.removeAll(collection.stream().filter((mb) -> mb.getUser().equals(user)).filter((mb) -> mb.hashCode() == (manager.set(elementPrepared, user).hashCode())).collect(Collectors.toSet()));
                if (size != collection.size()) message = "Наименьшии элементы удалены";
                else message = "Не найденно элементов меньше заданного";
                message = message + "\n";
            } else {
                message = "Неверный формат ввода данных";
                message = message + "\n";
            }
        } else {
            message = "Неверный формат ввода данных";
            message = message + "\n";
        }
        return message;
    }
}
