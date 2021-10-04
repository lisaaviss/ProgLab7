import Commands.CommandsExecution;
import Elements.Movie;
import Manager.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static String clientLogin; // имя пользователя
    private static final ArrayList<Movie> collection = new ArrayList<Movie>(); // коллекция
    private static final int port = 5678; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final LocalDateTime today = LocalDateTime.now(); //
    private static final String file = "/Users/macbookprolisaaviss/Desktop/ИТМО/Програмирование/lab6.1/src/Files/oop.json"; // файл с коллекцией
    private static final String serializedDate = "/Users/macbookprolisaaviss/Desktop/ИТМО/Програмирование/Lab7.1/src/Files/serializedDate.txt"; // файл для передачи сериализованных сообщений
    /**
     * Это main)
     *
     * @param args - что-то
     */
    public static void main(String[] args) {
        try {
            DateBase dateBase = new DateBase();
            dateBase.connect(collection);
            DateBase.creattables();
            System.out.println("Сервер запущен!");
            ExecutorService fixedPool = Executors.newFixedThreadPool(10);
            ThreadPoolExecutor pool = (ThreadPoolExecutor) fixedPool;

            Callable<String> cread = (Callable) () -> read();
            while (true) {
                connection(true, "non");
                authorisation();
                while (true) {
                    Future<String> future = pool.submit(cread);
                    String message = future.get();
                    if (message != null) {
                        write(message, "get");
                        if (message.equals("exit")) {
                            connection(false, "non");
                            break;
                        } else if (message.equals("close server")) {
                            execution("save", today, serializedDate);
                            connection(false,"close server");
                            break;
                        }
                        write(message, "send");
                    }
                }
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println(e);
        }
    }

    /**
     * Модуль выполнения команд
     *  @param  - переменная окружения
     * @param message - сообщение принятое от клиент
     * @param today - текущая дата
     * @param serializedDate - файл, где храниться сериализованная команда
     * @return
     */
    public static String execution(String message, LocalDateTime today, String serializedDate) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        Callable<String> ex = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    String command;
                    String[] field;
                    field = message.split(",");
                    command = field[0];
                    serialization.SerializeObject(CommandsExecution.action(collection, today, message, command, clientLogin), serializedDate);
                }catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                String command = "";
                return (String) CommandsExecution.action(collection, today, message, command, clientLogin);
            }
        };
        Future<String> future = forkJoinPool.submit(ex);
        return future.get();
    }

    /**
     * Модуль приёма подключений
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @param close - звкрытие сервера
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect, String close) throws IOException {
        if (connect) {
            server = new ServerSocket(port);
            System.out.println("Ожидание подключения...");
            clientSocket = server.accept();
            System.out.println("Соединение с клиентом установлено");
        }
        if (!connect) {
            System.out.println("Соединение с клиентом разорвано");
            clientSocket.close();
            server.close();
            if (close.equals("close server")){
                System.out.println("Сервер закрыт!");
                DateBase.disconnect();
                System.exit(0);
            }
        }

    }

    /**
     * Модуль чтения запроса
     *
     * @return - возвращает десериализованную команду
     * @throws IOException - ошибка чтения запроса
     */
    public static String read() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String message = in.readLine();
        message = serialization.DeserializeObject(serializedDate);
        return message;
    }

    /**
     * Модуль отправки ответов клиенту
     *
     * @param message - сообщение от клиента
     * @throws IOException - ошибка чтения запроса
     * @throws InterruptedException - ошибка ожидания
     */
    public static void write(String message, String command) throws IOException, InterruptedException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ExecutorService service = Executors.newCachedThreadPool();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    if (command.equals("get")) {
                        String messageToClient = "\nСервер принял команду: " + message + "\n";
                        serialization.SerializeObject(messageToClient, serializedDate);
                        out.write("\n");
                        out.flush();
                    } else if (command.equals("send")) {
                        TimeUnit.SECONDS.sleep(1);
                        execution(message, today, serializedDate);
                        out.write("\n");
                        out.flush();
                    }
                } catch (IOException | InterruptedException | ExecutionException e) {
                    System.out.println("Ошибка отправки ответа");
                }
            }
        };service.submit(task);
    }
    public static void write(boolean work) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            try {
                String messageToClient = "";
                if (work) messageToClient = "\nВы успешно авторизировались";
                else messageToClient = "\nВо время авторизации произошла ошибка, повторите попытку";
                serialization.SerializeObject(messageToClient, serializedDate);
                out.write("\n");
                out.flush();
            } catch (IOException e) {
                System.out.println("Ошибка отправки ответа");
            }
    }
    public static void authorisation() {
        while (true) {
            try {
                String[] fields = read().split(" ");
                boolean work = false;
                if (fields.length == 3){
                    clientLogin = fields[0];
                    if (fields[2].equals("2")) work = DateBase.addUser(fields[0], fields[1]);
                    else if (fields[2].equals("1")) work = DateBase.login(fields[0], fields[1]);
                }
                write(work);
                if (work) break;
            } catch (IOException e) {
                System.out.println("Ошибка авторизации");
            }
        }
    }
    //@Override
    protected String compute() {
        String message = null;
        try {
            message = read();
        } catch (IOException e) {
            System.out.println("Невозможно считать запрос");
        }
        return message;
    }
}
