package Commands;

/**
 * Класс выводящий последние 12 комманд
 *
 * @author Артём
 */
public class CommandHistory extends Command {
    public static String[] storage = new String[12];
    public static int Index;


    public static Object action() {
        Object message = "";
        int i;
        message = "Последние 12 комманд";
        for (i = 0; i < Index; i++) {
            message = message + "\n" + storage[i];
        }
        message = message + "\n";
        return message;

    }

    public static void save(String command) {
        if (Index < storage.length) {
            storage[Index] = command;
            Index++;
        } else {
            int i;
            for (i = 0; i + 1 < storage.length; i++) {
                storage[i] = storage[i + 1];
            }
            storage[11] = command;
        }
    }
}