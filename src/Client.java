import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client implements Serializable{
    private static String user_name;
    private static Socket clientSocket; // сокет для общения
    private static BufferedReader reader; // ридер читающий с консоли
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static final int port = 5678; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final String serializedDate = "/Users/macbookprolisaaviss/Desktop/ИТМО/Програмирование/Lab7.1/src/Files/serializedDate.txt"; // файл для передачи сериализованных сообщений

    /**
     * Это main)
     *
     * @param args - что-то
     */
    public static void main(String[] args){
        try {
            boolean kip = true;
            connection(true);
            authorisation();
            while (kip){
                System.out.print("Введите команду: ");
                String message = write(commandPreparation());
                read();
                if (message.equals("exit")){
                    connection(false);
                    kip = false;
                }
                read();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public static String write(String message) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        serialization.SerializeObject((Object) message, serializedDate);
        out.write(message + "\n");
        out.flush();
        return message;
    }

    /**
     * Модуль принятия сообщений от сервера
     *
     * @throws IOException - ошибка принятия сообщений
     * @return
     */
    public static String read() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String serverWord = in.readLine();
        serverWord = serialization.DeserializeObject(serializedDate);
        System.out.println(serverWord);
        return serverWord;
    }

    /**
     * Модуль соединения с сервером
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect) throws IOException {
        if (connect) {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Соединение с сервером установлено");
        }
        if (!connect) {
            System.out.println("Клиент был закрыт...");
            clientSocket.close();
            in.close();
            out.close();
        }
    }
    public static void authorisation() {
        System.out.println("Для подключения к базе данных необходимо авторизоваться");
        while (true){
            try {
                System.out.println("1 - Вход || 2 - Регистрация");
                System.out.print("Введите команду: ");
                String message = reader.readLine();
                String password;
                String login;
                if (message.equals("1") || message.equals("Вход")){
                    System.out.print("Введите логин: ");
                    login = reader.readLine();
                    login = login.toLowerCase();
                    System.out.print("Введите пароль: ");
                    password = reader.readLine();
                    write(login + " " + password + " 1");
                    String messageFromServer = read();
                    System.out.println(messageFromServer + "\n");
                    if (messageFromServer.equals("\nВы успешно авторизировались")) {
                        user_name = login;
                        break;
                    }
                } else if (message.equals("2") || message.equals("Регистрация")){
                    System.out.print("Введите логин: ");
                    login = reader.readLine();
                    login = login.toLowerCase();
                    System.out.print("Введите пароль: ");
                    password = reader.readLine();
                    write(login + " " + password + " 2");
                    String messageFromServer = read();
                    System.out.println(messageFromServer + "\n");
                    if (messageFromServer.equals("\nВы успешно авторизировались")) {
                        user_name = login;
                        break;
                    }

                } else {
                    System.err.print("Неизвестная команда\n\n");
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                System.err.print("Ошибка авторизации\n");
            }
        }
    }

    public static String commandPreparation() throws IOException{
        String result;
        String command = reader.readLine();
        String command1 =command.trim();
        if (command1.equals("add") || command1.equals("remove_lower")) {
            System.out.print("Введите значение поля name: ");
            StringBuilder name = new StringBuilder(reader.readLine());
            while (name.toString().isEmpty()){
                System.out.println("У вас пустая строка!");
                System.out.print("Введите значение поля name: ");
                name.replace(0, name.length(), reader.readLine());
            }
            System.out.print("Введите значение поля coordinates_x: ");
            StringBuilder coordinates_x = new StringBuilder("");
            boolean xcheck = true;
            while(xcheck) {
                coordinates_x.replace(0, coordinates_x.length(), reader.readLine());
                try {
                    Integer com3 = Integer.parseInt(coordinates_x.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Введите значение поля coordinates_x в диапозоне от [-2147483648;2147483647]");
                }
                xcheck = false;
            }
            System.out.print("Введите значение поля coordinates_y: ");
            StringBuilder coordinates_y = new StringBuilder("");
            boolean ycheck = true;
            while(ycheck){
                coordinates_y.replace(0, coordinates_y.length(), reader.readLine());
                if(coordinates_y.toString().isEmpty()){
                    ycheck = false;
                }else {
                    try{
                        if(Integer.parseInt(coordinates_y.toString())<=92){
                            ycheck = false;
                        }else {
                            System.out.println("Число должно быть меньше 92 \nВведите значение поля coordinates_y в диапозоне от [-2147483648;92)");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Введите значение поля coordinates_y в диапозоне от [-2147483648;92)");
                    }
                }
            }
            System.out.print("Введите значение поля oscarsCount: ");
            boolean oscarsCountCheck = true;
            StringBuilder oscarsCount = new StringBuilder("");
            while (oscarsCountCheck){
               oscarsCount.replace(0, oscarsCount.length(), reader.readLine());
                if (oscarsCount.toString().isEmpty()){
                    oscarsCountCheck = false;
                }else {
                    try {
                        if (Integer.parseInt(oscarsCount.toString()) > 0) {
                            oscarsCountCheck = false;
                        } else
                            System.out.println("Значение поля oscarsCount должно быть больше 0");
                    } catch (NumberFormatException e) {
                        System.out.println("Введите значение поля oscarsCount в диапозоне от [-2147483648;2147483647]");
                    }
                }
            }
            System.out.print("Введите значение поля totalBoxOffice: ");
            boolean totalBoxOfficeCheck = true;
            StringBuilder totalBoxOffice = new StringBuilder("");
            while(totalBoxOfficeCheck){
                totalBoxOffice.replace(0, totalBoxOffice.length(), reader.readLine());
                if (totalBoxOffice.toString().isEmpty()){
                    System.out.println("Значение поля totalBoxOffice не может быть null");;
                }else {
                    try {
                        if (Long.parseLong(totalBoxOffice.toString())<Long.MAX_VALUE && Long.parseLong(totalBoxOffice.toString())>0) {
                            totalBoxOfficeCheck = false;
                        } else
                            System.out.println("Значение поля totalBoxOffice должно быть больше 0");
                    } catch (NumberFormatException e) {
                        System.out.println("Введите значение поля totalBoxOffice в диапозоне от [0;9223372036854775807]");
                    }
                }
            }
            System.out.print("Введите значение поля genre (WESTERN, DRAMA, THRILLER, HORROR, SCIENCE_FICTION): ");
           StringBuilder genre = new StringBuilder();
            boolean genreCheck = true;
            while(genreCheck){
                genre.replace(0, genre.length(), reader.readLine());
                switch (genre.toString()){
                    case("WESTERN"):
                        genre.replace(0, genre.length(),"WESTERN");
                        genreCheck = false;
                        break;
                    case ("DRAMA"):
                        genre.replace(0, genre.length(),"DRAMA");
                        genreCheck = false;
                        break;
                    case ("THRILLER"):
                        genre.replace(0, genre.length(),"THRILLER");
                        genreCheck = false;
                        break;
                    case ("HORROR"):
                        genre.replace(0, genre.length(),"HORROR");
                        genreCheck = false;
                        break;
                    case ("SCIENCE_FICTION"):
                        genre.replace(0, genre.length(),"SCIENCE_FICTION");
                        genreCheck = false;
                        break;
                    case (""):
                        genre.replace(0, genre.length(),"");
                        genreCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля genre, должно соответствовать одному из списка (WESTERN, DRAMA, THRILLER, HORROR, SCIENCE_FICTION)");
                }
            }
            System.out.print("Введите значение поля mpaaRating (G, PG, R, NC_17): ");
            StringBuilder mpaaRating = new StringBuilder("");
            boolean mpaaRatingCheck = true;
            while(mpaaRatingCheck){
                mpaaRating.replace(0, mpaaRating.length(), reader.readLine());
                switch (mpaaRating.toString()){
                    case(""):
                        mpaaRating.replace(0, mpaaRating.length(), "");
                        mpaaRatingCheck = false;
                        break;
                    case("G"):
                        mpaaRating.replace(0, mpaaRating.length(), "G");
                        mpaaRatingCheck = false;
                        break;
                    case("PG"):
                        mpaaRating.replace(0, mpaaRating.length(), "PG");
                        mpaaRatingCheck = false;
                        break;
                    case("R"):
                        mpaaRating.replace(0, mpaaRating.length(), "R");
                        mpaaRatingCheck = false;
                        break;
                    case("NC_17"):
                        mpaaRating.replace(0, mpaaRating.length(), "NC_17");
                        mpaaRatingCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля mpaaRating, должно соответствовать одному из списка (G, PG, R, NC_17)");
                }
            }
            System.out.print("Введите значение поля Person_name: ");
            StringBuilder Person_name = new StringBuilder(reader.readLine());
            while (Person_name.toString().isEmpty()){
                System.out.println("Значение поля Person_name не может быть пустой строкой");
                System.out.print("Введите значение поля name: ");
                Person_name.replace(0, Person_name.length(), reader.readLine());
            }
            System.out.print("Введите значение поля height: ");
            StringBuilder height = new StringBuilder();
            boolean heightCheck = true;
                    while(heightCheck){
                        height.replace(0, height.length(), reader.readLine());
                            try {
                                if (Integer.parseInt(height.toString()) > 0) {
                                    heightCheck = false;
                                } else
                                    System.out.println("Значение поля height должно быть больше 0");
                            } catch (NumberFormatException e) {
                                System.out.println("Введите значение поля height в диапозоне от [-2147483648;2147483647]");
                            }
                        }
            System.out.print("Введите значение поля Person_eyeColor (BLACK, BLUE, YELLOW, WHITE, BROWN): ");
                    StringBuilder Person_eyeColor = new StringBuilder();
            boolean Person_eyeColorChech = true;
            while(Person_eyeColorChech){
                Person_eyeColor.replace(0, Person_eyeColor.length(), reader.readLine());
                switch (Person_eyeColor.toString()){
                    case("BLACK"):
                        Person_eyeColor.replace(0, Person_eyeColor.length(), "BLACK");
                        Person_eyeColorChech = false;
                        break;
                    case("BLUE"):
                        Person_eyeColor.replace(0, Person_eyeColor.length(), "BLUE");
                        Person_eyeColorChech = false;
                        break;
                    case("YELLOW"):
                        Person_eyeColor.replace(0, Person_eyeColor.length(), "YELLOW");
                        Person_eyeColorChech = false;
                        break;
                    case("WHITE"):
                        Person_eyeColor.replace(0, Person_eyeColor.length(), "WHITE");
                        Person_eyeColorChech = false;
                        break;
                    case("BROWN"):
                        Person_eyeColor.replace(0, Person_eyeColor.length(), "BROWN");
                        Person_eyeColorChech = false;
                        break;
                    default:
                        System.out.println("Значение поля Person_eyeColor, должно соответствовать одному из списка (BLACK, BLUE, YELLOW, WHITE, BROWN)");
                }
            }
            System.out.print("Введите значение поля Person_hairColor (BLACK, BLUE, YELLOW, WHITE, BROWN): ");
            StringBuilder Person_hairColor = new StringBuilder();
            boolean Person_hairColorCheck = true;
            while (Person_hairColorCheck){
                Person_hairColor.replace(0, Person_hairColor.length(), reader.readLine());
                switch (Person_hairColor.toString()){
                    case("BLACK"):
                        Person_hairColor.replace(0, Person_hairColor.length(), "BLACK");
                        Person_hairColorCheck = false;
                        break;
                    case("BLUE"):
                        Person_hairColor.replace(0, Person_hairColor.length(), "BLUE");
                        Person_hairColorCheck = false;
                        break;
                    case("YELLOW"):
                        Person_hairColor.replace(0, Person_hairColor.length(), "YELLOW");
                        Person_hairColorCheck = false;
                        break;
                    case("WHITE"):
                        Person_hairColor.replace(0, Person_hairColor.length(), "WHITE");
                        Person_hairColorCheck = false;
                        break;
                    case("BROWN"):
                        Person_hairColor.replace(0, Person_hairColor.length(), "BROWN");
                        Person_hairColorCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля Person_hairColor, должно соответствовать одному из списка (BLACK, BLUE, YELLOW, WHITE, BROWN)");
                }
            }
            System.out.print("Введите значение поля Person_nationality: (UNITED_KINGDOM, GERMANY, FRANCE, NORTH_KOREA)");
            StringBuilder Person_nationality = new StringBuilder();
            boolean Person_nationalityCheck = true;
            while(Person_nationalityCheck) {
                Person_nationality.replace(0, Person_nationality.length(), reader.readLine());
                switch (Person_nationality.toString()){
                    case("UNITED_KINGDOM"):
                        Person_nationality.replace(0, Person_nationality.length(), "UNITED_KINGDOM");
                        Person_nationalityCheck = false;
                        break;
                    case("GERMANY"):
                        Person_nationality.replace(0, Person_nationality.length(), "GERMANY");
                        Person_nationalityCheck = false;
                        break;
                    case("FRANCE"):
                        Person_nationality.replace(0, Person_nationality.length(), "FRANCE");
                        Person_nationalityCheck = false;
                        break;
                    case("NORTH_KOREA"):
                        Person_nationality.replace(0, Person_nationality.length(), "NORTH_KOREA");
                        Person_nationalityCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля Person_nationality, должно соответствовать одному из списка (UNITED_KINGDOM, GERMANY, FRANCE, NORTH_KOREA)");
                    }
                }
            result = command1 + "," + name.toString() + "," + coordinates_x.toString() + "," + coordinates_y.toString() + "," + oscarsCount.toString() + "," + totalBoxOffice.toString() + "," +
                    genre.toString() + "," + mpaaRating.toString() + "," + Person_name.toString() + "," + height.toString() + "," + Person_eyeColor.toString() + "," + Person_hairColor.toString() + "," + Person_nationality.toString() + "," + user_name;
            System.out.println(result);
        } else if (command1.equals("count_by_mpaa_rating")) {
            System.out.println("Введите один из рейтингов (G, PG, R, NC_17):");
            StringBuilder count_by_mpaa_rating = new StringBuilder();
            boolean count_by_mpaa_ratingCheck = true;
            while (count_by_mpaa_ratingCheck) {
                count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), reader.readLine());
                switch (count_by_mpaa_rating.toString()) {
                    case (""):
                        count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), "");
                        count_by_mpaa_ratingCheck = false;
                        break;
                    case ("G"):
                        count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), "G");
                        count_by_mpaa_ratingCheck = false;
                        break;
                    case ("PG"):
                        count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), "PG");
                        count_by_mpaa_ratingCheck = false;
                        break;
                    case ("R"):
                        count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), "R");
                        count_by_mpaa_ratingCheck = false;
                        break;
                    case ("NC_17"):
                        count_by_mpaa_rating.replace(0, count_by_mpaa_rating.length(), "NC_17");
                        count_by_mpaa_ratingCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля genre, должно соответствовать одному из списка (G, PG, R, NC_17)");
                }
            }
            result = command1 + "," + count_by_mpaa_rating.toString();
                System.out.println(result);
        }else if (command1.equals("group_counting_by_genre")){

            System.out.println("Введите значение поля genre (WESTERN, DRAMA, THRILLER, HORROR, SCIENCE_FICTION): ");
            StringBuilder group_counting_by_genre = new StringBuilder("");
            boolean group_counting_by_genreCheck = true;
            while(group_counting_by_genreCheck){
                group_counting_by_genre.replace(0, group_counting_by_genre.length(), reader.readLine());
                switch (group_counting_by_genre.toString()){
                    case("WESTERN"):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "WESTERN");
                        group_counting_by_genreCheck = false;
                        break;
                    case ("DRAMA"):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "DRAMA");
                        group_counting_by_genreCheck = false;
                        break;
                    case ("THRILLER"):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "THRILLER");
                        group_counting_by_genreCheck = false;
                        break;
                    case ("HORROR"):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "HORROR");
                        group_counting_by_genreCheck = false;
                        break;
                    case ("SCIENCE_FICTION"):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "SCIENCE_FICTION");
                        group_counting_by_genreCheck = false;
                        break;
                    case (""):
                        group_counting_by_genre.replace(0, group_counting_by_genre.length(), "");
                        group_counting_by_genreCheck = false;
                        break;
                    default:
                        System.out.println("Значение поля genre, должно соответствовать одному из списка (WESTERN, DRAMA, THRILLER, HORROR, SCIENCE_FICTION)");
                }
            }
            result = command1 + "," + group_counting_by_genre.toString();
        }else if (command1.equals("update_id")) {
           boolean idcheck = true;
            StringBuilder id1 = new StringBuilder();
            while(idcheck){
                System.out.println("Введите номер id, которого вы хотите заменить");
                id1.replace(0, id1.length(), reader.readLine());
                if (id1.toString().isEmpty()){
                    System.out.println("Значение id и не может быть null");
                }else {
                    try {
                        if (Integer.parseInt(id1.toString()) >0) {
                            idcheck = false;
                        } else
                            System.out.println("Значение id1 должно быть больше 0");
                    } catch (NumberFormatException e) {
                        System.out.println("Введите значение поля id в диапозоне от [0;2147483647]");
                    }
                }
            }
            result = command1 + "," + id1.toString();
        }else if (command1.equals("remove_by_id")) {
            boolean remove_by_idCheck = true;
            StringBuilder remove_by_id = new StringBuilder();
            while(remove_by_idCheck){
                System.out.println("Введите номер id, которого вы хотите удалить");
                remove_by_id.replace(0, remove_by_id.length(), reader.readLine());
                if (remove_by_id.toString().isEmpty()){
                    System.out.println("Значение id1 не может быть null");
                }else {
                    try {
                        if (Integer.parseInt(remove_by_id.toString())>0) {
                            remove_by_idCheck = false;
                        } else
                            System.out.println("Значение id1 должно быть больше 0");
                    } catch (NumberFormatException e) {
                        System.out.println("Введите значение id в диапозоне от [0;2147483647]");
                    }
                }
            }
            result = command1 + "," + remove_by_id.toString();
        }
        else result = command1;
        return result;
    }
}