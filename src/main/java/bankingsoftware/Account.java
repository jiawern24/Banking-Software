package bankingsoftware;

/**
 * Abstract Account class that is a general type of other account types
 * 
 * @author Frances Cortuna
 */
public abstract class Account {
    protected Profile holder;
    protected double balance;

    /**
     * Creates an Account object with a holder and balance
     * 
     * @param holder  Profile of account
     * @param balance Balance of account
     */
    Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;

    }

    /**
     * Abstract method that applies monthly interest fee to account balance
     * 
     * @return Returns new balance with accrued monthly interest.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that takes out monthly fee to account balance
     * 
     * @return Returns balance with monthly fee taken out.
     */
    public abstract double monthlyFee();

    /**
     * Abstract method that is a getter method to return initial of account type
     * 
     * @return Initial for account type
     */
    public abstract String getAccountTypeInitial();

    /**
     * Abstract method that is a getter method to return full name of account type
     * 
     * @return Full account type
     */
    public abstract String getAccountType();

    /**
     * Abstract method that is a getter method to return monthly interest of account type
     * 
     * @return Monthly interest amount
     */
    public abstract double getInterest();

    /**
     * Abstract method that is a getter method to return monthly fee for account type
     * 
     * @return Monthly fee amount
     */
    public abstract double getMonthlyFee();

    /**
     * Returns holder of account
     * 
     * @return Profile of account owner
     */
    public Profile getProfile() {
        return holder;
    }

    /**
     * Checks if two Account objects are equal.
     * 
     * @param object Account being compared
     * @return boolean whether or not two accounts are equal
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        Account other = (Account) object;
        return holder.equals(other.getProfile());
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    @Override
    public abstract String toString();

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    public abstract String toStringFeesInterest();
}
