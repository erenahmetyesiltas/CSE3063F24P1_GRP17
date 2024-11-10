import com.fasterxml.jackson.annotation.JsonProperty;

public class ID {
    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("entranceYearId")
    private int entranceYearId;

    @JsonProperty("entrancePlaceId")
    private int entrancePlaceId;

    @JsonProperty("staffId")
    private int staffId;

    public ID() {

    }

    public ID(String id, Object object) {
        if (object instanceof Student) {
            departmentId = Integer.parseInt(id.substring(0, 3));
            entranceYearId = Integer.parseInt(id.substring(3, 6));
            entrancePlaceId = Integer.parseInt(id.substring(6, 9));
        }

        // Here needs to be filled according to the staffID
    }

    public String getFullId(Object object) {
        if (object instanceof Student) {
            return "" + departmentId + entranceYearId + entrancePlaceId;
        }
        else if (object instanceof Advisor) {
            return "" + departmentId + staffId;
        }
        else {
            return "";
        }

    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getEntranceYearId() {
        return entranceYearId;
    }

    public int getEntrancePlaceId() {
        return entrancePlaceId;
    }

    public int getStaffId() {
        return staffId;
    }
}
