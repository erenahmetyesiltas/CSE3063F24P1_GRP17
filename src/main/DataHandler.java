package main;/*import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;*/

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {


    //private Gson gson;
    private int fileNumber = 0;
    private List<SystemData> allDatas = new ArrayList<SystemData>();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private ObjectMapper objectMapper = new ObjectMapper();


    public DataHandler() throws IOException {
        //gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassAdapter()).create();
        try {
            allDatas = retrieveData("allDatas.json");
        } catch (Exception e) {
            allDatas = new ArrayList<SystemData>();
            File file = new File("allDatas.json");
            file.createNewFile();
            e.printStackTrace();
        }
    }

    public void storeObject(Student studentObject) throws IOException {
        fileNumber++;
        SystemData data = new SystemData(studentObject, studentObject.getClass(), "object" + fileNumber + ".json");
        allDatas.add(data);
        this.removeDuplicateData();
        //objectMapper.writeValue(new File("object" + fileNumber + ".json"), studentObject);
        File file = new File("allDatas.json");
        file.delete();
        file.createNewFile();
        objectMapper.writeValue(file, allDatas);
    }



    public void storeObject(CourseSection courseSectionObject) throws IOException {
        fileNumber++;
        SystemData data = new SystemData(courseSectionObject, courseSectionObject.getClass(), "object" + fileNumber + ".json");
        allDatas.add(data);
        //objectMapper.writeValue(new File("object" + fileNumber + ".json"), courseSectionObject);
        File file = new File("allDatas.json");
        file.delete();
        file.createNewFile();
        objectMapper.writeValue(file, allDatas);
    }

    public void storeObject(Advisor advisorObject) throws IOException {
        fileNumber++;
        SystemData data = new SystemData(advisorObject, advisorObject.getClass(), "object" + fileNumber + ".json");
        allDatas.add(data);
        //objectMapper.writeValue(new File("object" + fileNumber + ".json"), courseSectionObject);
        File file = new File("allDatas.json");
        file.delete();
        file.createNewFile();
        objectMapper.writeValue(file, allDatas);
    }

    public void removeDuplicateData(){
        List<SystemData> removed = new ArrayList<>();
        for (SystemData data : allDatas) {
            for (int i = 0; i < allDatas.size(); i++) {
                if (i == allDatas.indexOf(data)) {
                    continue;
                }
                else {
                    if (allDatas.get(i).getObjectClass() == Student.class && data.getObjectClass() == Student.class) {
                        String id1 = ((Student) objectMapper.convertValue(data.getObject(), Student.class)).getId();
                        String id2 = ((Student) objectMapper.convertValue(allDatas.get(i).getObject(), Student.class)).getId();

                        if (id1.equals(id2)) {
                            removed.add(allDatas.get(i));
                        }
                    }
                    if (allDatas.get(i).getObjectClass() == CourseSection.class && data.getObjectClass() == CourseSection.class) {
                        String id1 = ((CourseSection) objectMapper.convertValue(data.getObject(), CourseSection.class)).getId();
                        String id2 = ((CourseSection) objectMapper.convertValue(data.getObject(), CourseSection.class)).getId();

                        if (id1.equals(id2)) {
                            removed.add(allDatas.get(i));
                        }
                    }
                    if (allDatas.get(i).getObjectClass() == Advisor.class && data.getObjectClass() == Advisor.class) {
                        String id1 = ((Advisor) objectMapper.convertValue(data.getObject(), Advisor.class)).getId();
                        String id2 = ((Advisor) objectMapper.convertValue(data.getObject(), Advisor.class)).getId();
                        if (id1.equals(id2)) {
                            removed.add(allDatas.get(i));
                        }
                    }
                }
            }
        }
        allDatas.removeAll(removed);
    }

    public List<SystemData> retrieveData(String fileName) throws JsonProcessingException {
        try {
            File file = new File(fileName);
            file.createNewFile();
            List<SystemData> allDatalist = objectMapper.readValue(file, new TypeReference<List<SystemData>>() {
            });
            return allDatalist;
        } catch (JsonParseException e) {
            e.printStackTrace();
            return new ArrayList<SystemData>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<SystemData>();
        }

    }

    /*public void storeObject(main.Student object){
        fileNumber++;
        main.SystemData data = new main.SystemData(object, object.getClass(), "object" + fileNumber + ".json");
      //  allDatas.add(data);

        try(FileWriter fileWriter = new FileWriter(data.getJsonFile())) {
            gson.toJson(object, fileWriter);
            allDatas.add(data);

            try(FileWriter allFileWriter = new FileWriter("allDatas.json")) {
                gson.toJson(allDatas, allFileWriter);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public <T> T retrieveData(String fileName, Class<T> classOfT) {
        try(FileReader fileReader = new FileReader(fileName)) {
            return gson.fromJson(fileReader, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return (T) allDatas;
        }
    }

    public void deleteData(main.SystemData data){
        File file = new File(data.getJsonFile());
        file.delete();

        allDatas.remove(data);

        data = null;

        return;
    }*/

    public List<SystemData> getAllDatas() {
        return allDatas;
    }
    public int getFileNumber() {
        fileNumber++;
        return fileNumber;
    }

}
