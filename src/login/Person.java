package login;

public class Person{
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String advisor;
    private String userClass; // "class" yerine farklı isim kullanılır, çünkü "class" Java'da rezerve edilmiş bir kelimedir.


    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAdvisor() {
        return advisor;
    }

    public String getUserClass() {
        return userClass;
    }

    // Diğer getter/setter metodları...
}
