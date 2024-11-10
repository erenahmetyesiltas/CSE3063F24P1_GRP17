import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try {
            CourseRegistrationSystem system = new CourseRegistrationSystem();
            system.restoreSystem();
            DataHandler dataHandler = system.getDataHandler();
            try {
            Student student1 = new Student();
            student1.setYear(2019);

            ID id1 = new ID("150122507", student1);
            student1.setId(id1);

            Registration reg = new Registration();
            reg.setRegistrationStatus(2);
            student1.setRegistration(reg);

            dataHandler.storeObject(student1);

            Student student2 = new Student();
            student2.setYear(2020);

            ID id2 = new ID("150122011", student2);
            student2.setId(id2);

            dataHandler.storeObject(student2);

            Student student3 = new Student();
            student3.setYear(2021);

            ID id3 = new ID("150121017", student3);
            student3.setId(id3);

            dataHandler.storeObject(student3);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Student student = system.getAllStudents().get(0);
            //System.out.println(student.getRegistration().getRegistrationStatus());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }




    }
}
