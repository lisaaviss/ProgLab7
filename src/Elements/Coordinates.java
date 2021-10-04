package Elements;

import java.io.InputStream;

/**
 * Класс с координатами
 *
 * @author Артём
 */
public class Coordinates {
    private Integer x; //Поле не может быть null
    private int y; //Максимальное значение поля: 92

    public void setX(String x) throws NumberFormatException{
        this.x = Integer.parseInt(x);
    }

    public void setY(String y) throws NumberFormatException{
        this.y = Integer.parseInt(y);
    }
    @Override
    public String toString(){
        String result;
        result = x + ","+ y;
        return (result);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}