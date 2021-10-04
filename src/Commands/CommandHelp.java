package Commands;
/**
 * Класс с командой help
 * @author Артём
 */
public class CommandHelp extends Command {
    public static Object action(){
        Object messsage = ("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update_id : обновить значение элемента коллекции, id которого равен заданному\n" +
                "shuffle : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "history : вывести последние 12 команд (без их аргументов)\n" +
                "min_by_name : вывести любой объект из коллекции, значение поля name которого является минимальным\n" +
                "group_counting_by_genre : сгруппировать элементы коллекции по значению поля genre, вывести количество элементов в каждой группе\n" +
                "count_by_mpaa_rating mpaaRating : вывести количество элементов, значение поля mpaaRating которых равно заданному");
        return messsage;
    }
}