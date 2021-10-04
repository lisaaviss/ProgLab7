package Commands;

import Elements.Movie;
import Manager.DateBase;

import java.util.ArrayList;

public class CommandSave {
    public static String action(ArrayList<Movie> collection) {
        Object[] elements = collection.toArray();
        DateBase.executeCommand("TRUNCATE TABLE Movie");
        for (Object element : elements) {
            String[] fields = element.toString().split(",");
            System.out.println(element.toString());
            String command = "INSERT INTO Movie " +
                    "(id, " +
                    "name, " +
                    "coordinate_X, " +
                    "coordinate_Y, " +
                    "creationDate, " +
                    "oscarsCount, " +
                    "totalBoxOffice, " +
                    "genre, " +
                    "mpaaRating, " +
                    "screenwriter_name, " +
                    "screenwriter_height, " +
                    "screenwriter_eyeColor, " +
                    "screenwriter_hairColor, " +
                    "screenwriter_nationality, " +
                    "\"user\") VALUES (" +
                    fields[0] + ", '" +
                    fields[1] + "', " +
                    fields[2] + ", " +
                    fields[3] + ", '" +
                    fields[4] + "', " +
                    fields[5] + ", " +
                    fields[6] + ", '" +
                    fields[7] + "', '" +
                    fields[8] + "', '" +
                    fields[9] + "', " +
                    fields[10] + ", '" +
                    fields[11] + "', '" +
                    fields[12] + "', '" +
                    fields[13] + "', '" +
                    fields[14] + "');";
            System.out.println(command);
        }
        return "Дайте баллов, а я вам соль";
    }
}
