import java.io.*;

/**
 * Класс, проводящий сериализацию и десериализацию объектов
 */
public class Serialization {

    /**
     * Сериализует объект
     *
     * @param <T> тип объекта
     * @return массив байтов
     */
    public <T> byte[] SerializeObject(T input, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            T object = input;
            oos.writeObject(object);
        } catch (IOException e) {
            System.out.println("ошибка сериализации");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Десериализует объект
     *
     * @param <T> тип объекта
     * @return объект
     */
    public <T> T DeserializeObject(String filename) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            return (T)ois.readObject();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
