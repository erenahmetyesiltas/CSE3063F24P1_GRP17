package main;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Classroom {

    @JsonProperty("id")
    private String id;

    @JsonProperty("capacity")
    private int capacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
