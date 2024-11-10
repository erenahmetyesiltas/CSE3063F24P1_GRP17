import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        try{
            system.restoreSystem();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        DataHandler dataHandler = system.getDataHandler();

        /*try {
            Student student1 = new Student();
            student1.setYear(2019);
            dataHandler.storeObject(student1);

            Student student2 = new Student();
            student2.setYear(2020);
            dataHandler.storeObject(student2);

            Student student3 = new Student();
            student3.setYear(2021);
            dataHandler.storeObject(student3);

        } catch (IOException e) {
            e.printStackTrace();
        }*/


        System.out.println(system.getAllStudents());
    }
}
