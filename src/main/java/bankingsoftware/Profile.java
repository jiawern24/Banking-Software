package bankingsoftware;

/**
 * Profile class defines the profile of an account holder.
 * 
 * @author Frances Cortuna
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Parameterized constructor that initializes a Profile object with first name,
     * last name, and date of birth.
     * 
     * @param fname First name of profile holder
     * @param lname Last name of profile holder
     * @param dob   Date of birth of profile holder
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Compares the last names of two Profile objects.
     * If last names are the same, compares the first names.
     * If first names are the same, compares DOB.
     * Returns a positive if account 1 goes after account 2
     * Returns a negative if account 1 goes before account 2
     * 
     * @param other Profile object being compared to this
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = lname.compareTo(other.lname);
        
        if (lastNameComparison == 0) {
            int firstNameComparison = fname.compareTo(other.fname);

            if(firstNameComparison == 0) {
                return dob.compareTo(other.dob);
            }

            return firstNameComparison;
        }

        return lastNameComparison;
    }

    /**
     * Getter method to return first name
     * 
     * @return First name of Profile
     */
    public String getFname() {
        return fname;
    }

    /**
     * Getter method to return last name
     * 
     * @return Last name of Profile
     */
    public String getLname() {
        return lname;
    }

    /**
     * Getter method to return DOB
     * 
     * @return DOB of Profile
     */
    public Date getDOB() {
        return dob;
    }

    /**
     * Checks if two Profile objects are equal.
     * 
     * @param object Profile being compared
     * @return boolean whether or not two profiles are equal
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Profile other = (Profile) object;
        String thisfname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
        String thislname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
        String otherFname = other.getFname().substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
        String otherLname = other.getLname().substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();

        return thisfname.equals(otherFname) && thislname.equals(otherLname) && dob.equals(other.getDOB());
    }
}
