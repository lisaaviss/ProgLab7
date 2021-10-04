package Manager;

import Elements.Movie;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DateBase {
    private static String url = "jdbc:postgresql://localhost:5432/Movie";
    //private static String url = "jdbc:postgresql://pg:5432/studs";
    private static Statement statement;
    private static Connection connection;

    /**
     * Подключение к базе данных
     *
     * @param collection - коллекция
     */
    public void connect(ArrayList<Movie> collection){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, "macbookprolisaaviss", "admin");
            System.out.println(("Соединение с базой данных установлено"));
            statement = connection.createStatement();
            load(collection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выгружает коллекцию из БД в программу
     *
     * @param - коллекция
     */
    public static void creattables(){
        try {
            String query = "DROP TABLE IF EXISTS usersbaza CASCADE;\n" +
                    "DROP TABLE IF EXISTS Movie;\n" +
                    "DROP SEQUENCE IF EXISTS randomid;\n" +
                    "CREATE TABLE usersbaza\n" +
                    "(\n" +
                    "    \"user\" VARCHAR PRIMARY KEY,\n" +
                    "    password VARCHAR\n" +
                    ");\n" +
                    "CREATE TABLE Movie\n" +
                    "(\n" +
                    "    id int,\n" +
                    "    name VARCHAR NOT NULL CHECK (name NOT LIKE ''),\n" +
                    "    coordinate_X int NOT NULL,\n" +
                    "\tcoordinate_Y int CHECK (coordinate_Y < 92),\n" +
                    "    creationDate VARCHAR NOT NULL,\n" +
                    "    oscarsCount int CHECK(oscarsCount > 0),\n" +
                    "    totalBoxOffice Bigint NOT NULL CHECK (totalBoxOffice > 0),\n" +
                    "\tgenre VARCHAR CHECK (genre IN ('WESTERN', 'DRAMA','THRILLER', 'HORROR', 'SCIENCE_FICTION')),\n" +
                    "\tmpaaRating VARCHAR CHECK (mpaaRating IN ('G', 'PG', 'R', 'NC_17')),\n" +
                    "\tscreenwriter_name VARCHAR NOT NULL CHECK (screenwriter_name NOT LIKE ''),\n" +
                    "\tscreenwriter_height int CHECK (screenwriter_height > 0),\n" +
                    "\tscreenwriter_eyeColor VARCHAR NOT NULL CHECK (screenwriter_eyeColor IN ('BLACK', 'BLUE', 'YELLOW', 'WHITE', 'BROWN')),\n" +
                    "\tscreenwriter_hairColor VARCHAR CHECK(screenwriter_HairColor IN ('BLACK', 'BLUE', 'YELLOW', 'WHITE', 'BROWN')),\n" +
                    "\tscreenwriter_nationality VARCHAR NOT NULL CHECK (screenwriter_nationality IN ('UNITED_KINGDOM', 'GERMANY', 'FRANCE', 'NORTH_KOREA')),\n" +
                    "\t\"user\" character varying COLLATE pg_catalog.\"default\" References usersbaza (\"user\")\n" +
                    ");\n" +
                    "\n" +
                    "CREATE SEQUENCE randomid\n" +
                    "START 1\n" +
                    "INCREMENT 1\n" +
                    "OWNED BY Movie.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Таблицы созданы");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка при создании таблиц");
        }
    }
    public static void load(ArrayList<Movie> collection){
        try {
            Manager manager = new Manager();
            collection.clear();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie");
            while (rs.next()) {
                String element = rs.getString(1);
                for (int i = 2; i < 16; i++) {
                    element = element + "," + rs.getString(i);
                }
                System.out.println(element);
                manager.fill(element, collection);
            }
            rs.close();
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Отключение от базы данных
     */
    public static void disconnect(){
        try {
            statement.close();
            connection.close();
            System.out.println("Соединение с базой данных разорвано");
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

    /**
     * Исполнение команд на sql
     *
     * @param command - команда
     * @return - результат работы
     */
    public static String executeCommand(String command){
        String answer = "Команда выполнена\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (Exception e) {
            answer = e.getMessage() + "\n";
            System.out.println(e.getMessage());
        }
        return answer;
    }

    /**
     * Добавление нового пользователя
     *
     * @param login - логин
     * @param password - пароль
     * @return - результат работы
     */
    public static boolean addUser(String login, String password) {
        System.out.println("Добавляем пользователя...");
        try {
            String query = "INSERT INTO usersbaza (\"user\", password) VALUES ('" + login + "', '" + MD_5(password) + "');";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Пользователь добавлен");
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка, пользователь не создан");
            return false;
        }
    }

    /**
     * Авторизация существующего пользователя
     *
     * @param login - логин
     * @param password - пароль
     * @return - результат работы
     */
    public static boolean login(String login, String password) {
        try {
            String query = "SELECT * FROM usersbaza\n" +
                    "WHERE \"user\" = '" + login + "';";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                if (rs.getString(2).equals(MD_5(password))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка входа");
            return false;
        }
    }

    /**
     * Хэширование пароля по алгоритму md5
     *
     * @param password - пароль
     * @return - хэшированный пароль
     */
    private static String MD_5(String password){
        MessageDigest m = null;
        password += "O87&Dob76D_PB7pD^*&b6DBO*^%DBDI%B^75"; // СОЛЬ
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("oschibka :)");
        }
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;
    }
}