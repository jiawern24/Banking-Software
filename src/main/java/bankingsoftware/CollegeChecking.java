package bankingsoftware;

/**
 * College Checking class that extends savings.
 * It has its own interest fee, no monthly fee and has campus locations
 *
 * @author Frances Cortuna
 */
public class CollegeChecking extends Checking {
    private Campus campus;

    /**
     * Initializes College Checking object by calling on its super class and adds
     * its own parameter of campus code
     * 
     * @param holder
     * @param balance
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Overloaded constructor for depositing and withdrawal methods.
     * 
     * @param holder Profile of account holder
     * @param balance Balance to deposit/withdraw
     */
    public CollegeChecking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Returns balance of account
     * Overrides monthlyFee() method of checking class since college checking
     * accounts have no monthly fee.
     * 
     * @return Returns balance of account
     */
    @Override
    public double monthlyFee() {
        return balance;
    }

    @Override
    public double getMonthlyFee() {
        return 0.0;
    }

    /**
     * Getter method to return initial of account type.
     * 
     * @return Returns "CC" for college checking
     */
    @Override
    public String getAccountTypeInitial() {
        return "CC";
    }

    /**
     * Getter method to return full name of account type.
     * 
     * @return Returns "College Checking"
     */
    @Override
    public String getAccountType() {
        return "College Checking";
    }

    /**
     * Getter method to return campus code of college checking account.
     * 
     * @return Campus code
     */
    public int getCampusCode() {
        return campus.getCampusCode();
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    @Override
    public String toString() {
        return String.format("College Checking::%s %s %s::Balance $%,.2f::%s", holder.getFname(), holder.getLname(), holder.getDOB().toString(), balance, campus.getCampus());
    }
}
