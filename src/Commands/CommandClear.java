    package Commands;
    /**
     * Класс отчистки коллекции
     * @author Артём
     */

    import Elements.Movie;

    import java.util.ArrayList;
    import java.util.stream.Collectors;

    public class CommandClear extends Command {
        public static Object action(ArrayList<Movie> list, String user){
            Object message = "";
            list.removeAll(list.stream().filter((mb) -> mb.getUser().equals(user)).collect(Collectors.toList()));
            message = "Элементы принадлежащие пользователю " + user + " успешно удалены";
            message = message + "\n";
            return message;
        }
    }
