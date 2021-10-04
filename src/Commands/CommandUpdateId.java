package Commands;

import Elements.Movie;
import Manager.Manager;
import java.util.ArrayList;

public class CommandUpdateId {
    public static Object action(String command, ArrayList<Movie> collection, String user){
        Object message = "";
        Manager manager = new Manager();
        int id = 0;
        String[] field;
        Movie[] arr;
        boolean work;
        work = false;
        int index;
        field = command.split(",");
        arr = collection.toArray(new Movie[0]);
        if (field.length == 1){
            message ="id отсутствует";
            System.out.println(message);
            message = message + "\n";
        } else try {
            id = Integer.parseInt(field[1]);
            for (index = 0; index < collection.size(); index++) {
                if (id == arr[index].getId() && user.equals(arr[index].getUser())) {
                    String element = arr[index].toString();
                    collection.remove((arr[index]));
                    manager.add(element, collection);
                    message = "id успешно обновлён";
                    System.out.println(message);
                    message = message + "\n";
                    work = true;
                    break;
                }
            }
            if (!work) {
                message = "Такого элемента не существует или у вас нет к нему доступа";
                System.out.println(message);
                message = message + "\n";
            }
        } catch (NumberFormatException e) {
            message = "id неверный формат строки!";
            System.out.println(message);
            message = message + "\n";
        }
        return message;
    }
}
