package Elements;
/**
 * Основной класс, содержащий поля для обработки json и работы пользователя с данными.
 *
 * @author Артём
 */

import java.time.ZonedDateTime;

public class Movie {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private Long totalBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person screenwriter = new Person();
    private String user;

    public void setId(String id){
        try {
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("id неверный формат строки!");
            System.exit(0);
        }
        if (this.id <= 0) {
            throw new IllegalStateException("id должно быть больше 0");
        }
    }
    public void setName(String name) {
        if (name == null){
            System.out.println("name не может быть null");
            System.exit(0);
        } else if (name.equals("")){
            System.out.println("name не может быть пустой строкой");
            System.exit(0);
        } else this.name = name;
    }
    public void setCoordinates(String x, String y){
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }
    public void setCreationDate(String creationDate){
        this.creationDate = ZonedDateTime.parse(creationDate);
    }
    public void setOscarsCount(String oscarsCount){
        try {
            if (Integer.parseInt(oscarsCount) <= 0){
                System.out.println("oscarsCount не может меньше или равно 0");
                System.exit(0);
            } else this.oscarsCount = Integer.parseInt(oscarsCount);
        } catch (NumberFormatException e) {
            System.out.println("oscarsCount неверный формат строки!");
            System.exit(0);
        }
    }
    public void setTotalBoxOffice(String totalBoxOffice) {
        try{
            if (Long.parseLong(totalBoxOffice) <= 0){
                System.out.println("totalBoxOffice должно быть больше 0");
                System.exit(0);
            }else this.totalBoxOffice = Long.parseLong(totalBoxOffice);
        } catch (NumberFormatException e){
            System.out.println("totalBoxOffice неверный формат строки");
            System.exit(0);
        }
    }
    public void setGenre(String genre) {
        switch (genre) {
            case ("WESTERN"):
                this.genre = MovieGenre.WESTERN;
                break;
            case ("DRAMA"):
                this.genre = MovieGenre.DRAMA;
                break;
            case ("THRILLER"):
                this.genre = MovieGenre.THRILLER;
                break;
            case ("HORROR"):
                this.genre = MovieGenre.HORROR;
                break;
            case ("SCIENCE_FICTION"):
                this.genre = MovieGenre.SCIENCE_FICTION;
                break;
            default:
                System.out.println("Значение поля genre, должно соответствовать одному из списка (WESTERN, DRAMA, THRILLER, HORROR, SCIENCE_FICTION)");
        }
    }
    public void setMpaaRating(String mpaaRating) {
        switch (mpaaRating) {
            case ("G"):
                this.mpaaRating = MpaaRating.G;
                break;
            case ("PG"):
                this.mpaaRating = MpaaRating.PG;
                break;
            case ("R"):
                this.mpaaRating = MpaaRating.R;
                break;
            case ("NC_17"):
                this.mpaaRating = MpaaRating.NC_17;
                break;
            default:
                System.out.println("Значение поля mpaaRating, должно соответствовать одному из списка (G, PG, R, NC_17)");
        }
    }
    public void setScreenwriter(String name, String height, String eyeColor, String hairColor, String nationality){
        screenwriter.setName(name);
        screenwriter.setHeight(height);
        screenwriter.setEyeColor(eyeColor);
        screenwriter.setHairColor(hairColor);
        screenwriter.setNationallity(nationality);
        if (screenwriter.getName() == null | screenwriter.getHeight() == null | screenwriter.getEyeColor() == null | screenwriter.getHairColor() == null | screenwriter.getNationality() == null){
            screenwriter = null;
            System.err.println("screenwriter не может быть null");
            System.exit(0);
        }
    }
    public void setUser (String user){
        this.user = user;
    }
    public String show(){
        return (id + "," + name + "," + coordinates + "," + creationDate + "," +
                oscarsCount + "," + totalBoxOffice + "," + genre + "," +
                mpaaRating + "," + screenwriter + " — " + user);

    }
    @Override
    public String toString() {
        return (id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," + creationDate + "," +
                oscarsCount + "," + totalBoxOffice + "," + genre + "," +
                mpaaRating + "," + screenwriter.getName() + "," + screenwriter.getHeight() + "," + screenwriter.getEyeColor() + "," + screenwriter.getHairColor() + "," +  screenwriter.getNationality() + "," + user);
    }

    public Object getUser() {
        return user;
    }

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public ZonedDateTime getCreationDate(){
        return creationDate;
    }
    public int getOscarsCount(){
        return oscarsCount;
    }
    public Long getTotalBoxOffice(){
        return totalBoxOffice;
    }
    public MovieGenre getGenre(){
        return genre;
    }
    public MpaaRating getMpaaRating(){
        return mpaaRating;
    }
    public Person getScreenwriter(){
        return screenwriter;
    }

}
