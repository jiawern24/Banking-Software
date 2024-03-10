package bankingsoftware;

/**
 * Constants for campus codes
 * 
 * @author Frances Cortuna
 */
public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    private int campusCode;

    /**
     * Campus constants with the campus code as its property
     * 
     * @param campusCode Campus code
     */
    Campus(int campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Getter method to get campus code
     * 
     * @return Campus code
     */
    public int getCampusCode() {
        return campusCode;
    }

    /**
     * Getter method to get campus code
     * 
     * @return Campus code
     */
    public String getCampus() {
        switch(campusCode) {
            case 0:
                return "NEW_BRUNSWICK";
            case 1:
                return "NEWARK";
            case 2:
                return "CAMDEN";
            default:
                return "UNKNOWN";
        }
    }
}
