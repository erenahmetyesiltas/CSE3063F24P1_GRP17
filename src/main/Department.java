package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Department {

    @JsonProperty("id")
    private String id;

    @JsonProperty("departmentName")
    private String departmentName;

    @JsonProperty
    private List<String> courseSectionIds = new ArrayList<>();

    public Department() {

    }

    public List<String> getCourseSectionIds() {
        return courseSectionIds;
    }

    public void setCourseSectionIds(List<String> courseSectionIds) {
        this.courseSectionIds = courseSectionIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
