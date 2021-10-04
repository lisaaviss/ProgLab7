package Elements;

/**
 * Класс с полями, для описания поля Person
 *
 * @author Артём
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null

    public void setName(String name) {
        if (name == null){
            System.out.println("screenwriter.name не может быть null");
            System.exit(0);
        }else if (name.equals("")){
            System.out.println("screenwriter.name не может быть null");
            System.exit(0);
        }else this.name = name;
    }

    public void setHeight(String height) {
        if (Integer.parseInt(height) <= 0){
            System.out.println("screenwriter.height должно быть больше 0");
            System.exit(0);
        }else this.height = Integer.parseInt(height);
    }

    public void setEyeColor(String eyeColor) {
        switch (eyeColor) {
            case ("BLACK"):
                this.eyeColor = Color.BLACK;
                break;
            case ("BLUE"):
                this.eyeColor = Color.BLUE;
                break;
            case ("WHITE"):
                this.eyeColor = Color.WHITE;
                break;
            case ("YELLOW"):
                this.eyeColor = Color.YELLOW;
                break;
            case ("BROWN"):
                this.eyeColor = Color.BROWN;
                break;
        }
    }

    public void setHairColor(String hairColor) {
        switch (hairColor) {
            case ("BLACK"):
                this.hairColor = Color.BLACK;
                break;
            case ("BLUE"):
                this.hairColor = Color.BLUE;
                break;
            case ("WHITE"):
                this.hairColor = Color.WHITE;
                break;
            case ("YELLOW"):
                this.hairColor = Color.YELLOW;
                break;
            case ("BROWN"):
                this.hairColor = Color.BROWN;
                break;
        }
    }

    public void setNationallity(String nationality) {
        switch (nationality) {
            case ("NORTH_KOREA"):
                this.nationality = Country.NORTH_KOREA;
                break;
            case ("FRANCE"):
                this.nationality = Country.FRANCE;
                break;
            case ("GERMANY"):
                this.nationality = Country.GERMANY;
                break;
            case ("UNITED_KINGDOM"):
                this.nationality = Country.UNITED_KINGDOM;
                break;
        }
    }
    @Override
    public String toString(){
        String result;
        result = this.name + "," + this.height + "," + this.eyeColor + "," + this.hairColor + "," + this.nationality;
        return (result);
    }
    public String getName() {
        return (this.name);
    }
    public Integer getHeight() {
        return (this.height);
    }
    public Color getEyeColor() {
        return (this.eyeColor);
    }
    public Color getHairColor() {
        return (this.hairColor);
    }
    public Country getNationality() {
        return (this.nationality);
    }
}