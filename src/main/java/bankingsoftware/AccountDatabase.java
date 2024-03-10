package bankingsoftware;

import java.lang.StringBuilder;

/**
 * AccountDatabase class that is a linear data structure to hold the list of
 * accounts.
 * Has methods to open, close, withdraw, deposit and sort accounts.
 *
 * @author Jia Wern Chong, Frances Cortuna
 */
public class AccountDatabase {
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROWTH_FACTOR = 4;
    private static final int NOT_FOUND = -1;

    private Account[] accounts;
    private int numAcct;

    /**
     * Constructor for AccountDatabase object to initialize object with initial
     * capacity of 4 and initial numAcct as 0
     */
    public AccountDatabase() {
        accounts = new Account[INITIAL_CAPACITY];
        numAcct = 0;
    }

    /**
     * Getter method to get number of accounts
     * 
     * @return Number of accounts
     */
    public int getNumAcct() {
        return numAcct;
    }

    /**
     * Getter method to get accounts array
     * 
     * @return Accounts array
     */
    public Account[] getAccountsArray() {
        return accounts;
    }

    /**
     * Find an Account in array of Accounts.
     * If Account is in the array, return its index. If not, return -1
     *
     * @param account Account to find in the array
     * @return Index of account in array or -1 if not found in array.
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * When array is full, grow it's capacity by 4
     */
    private void grow() {
        int newCapacity = accounts.length + GROWTH_FACTOR;
        Account[] newAccountArr = new Account[newCapacity];

        for (int i = 0; i < numAcct; i++) {
            newAccountArr[i] = accounts[i];
        }
        accounts = newAccountArr;
    }

    /**
     * Checks if account is in an array.
     * Returns true if event is in array, false otherwise
     *
     * @param account Account to be searched in array
     * @return True if event is in array, false otherwise
     */
    public boolean contains(Account account) {
        if (find(account) != NOT_FOUND) {
            return true;
        }
        return false;
    }

    /**
     * Adds a new account to the array.
     * Returns true if new account is added, false otherwise.
     *
     * @param account Account to be added to array
     * @return True if new account is added, false otherwise
     */
    public boolean open(Account account) {
        if (numAcct == accounts.length) {
            grow();
        }
        accounts[numAcct] = account;
        numAcct += 1;
        return true;
    }

    /**
     * Removes a given account from array.
     * Returns true if account is removed, false otherwise
     *
     * @param account Account to be removed from array.
     * @return True if given account is removed, false otherwise.
     */
    public boolean close(Account account) {
        int indexOfAcc = find(account);
        if (indexOfAcc != NOT_FOUND) {
            for (int i = indexOfAcc; i < numAcct - 1; i++) {
                accounts[i] = accounts[i + 1];
            }
            numAcct -= 1;
            accounts[numAcct] = null;
            return true;
        }
        return false;
    }

    /**
     * Checks the account balance and updates isLoyal
     * 
     * @return False if account balance is less than 2000, true otherwise
     */
    private boolean loyaltyStatus(MoneyMarket account) {
        if (account.balance < 2000.0) {
            account.isLoyal = false;
            return false;
        } else {
            account.isLoyal = true;
            return true;
        }
    }

    /**
     * Charges withdrawal fee if number of withdrawals is more than 3 times
     * 
     * @return Withdrawal fee
     */
    private void withdrawalFee(MoneyMarket account) {
        account.setWithdrawal(account.getWithdrawal()+1);
    }

    /**
     * Checks if account has sufficient balance for withdrawal and withdraw from
     * account
     * Returns false if balance is insufficient for withdrawal
     *
     * @param account Account to withdraw from
     * @return false if insufficient
     */
    public boolean withdraw(Account account) {
        Account accountInArray = accounts[find(account)];
        if (account.balance <= accountInArray.balance) {
            accountInArray.balance -= account.balance;

            if (accountInArray.getAccountTypeInitial().equals("MM")) {
                loyaltyStatus((MoneyMarket) accountInArray);
                withdrawalFee((MoneyMarket) accountInArray);
            }

            return true;
        }
        return false;
    }

    /**
     * Adds money to an account
     *
     * @param account Account to deposit into
     */
    public void deposit(Account account) {
        Account accountInArray = accounts[find(account)];
        accountInArray.balance += account.balance;

        if (accountInArray.getAccountTypeInitial().equals("MM")) {
            loyaltyStatus((MoneyMarket) accountInArray);
        }
    }

    /**
     * Prints accounts in array
     */
    private String print() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < numAcct; i++) {
            output.append(accounts[i].toString());
            output.append("\n");
        }
        return output.toString();
    }

    /**
     * Swap the order of account one and account two.
     *
     * @param accountOne  Index of account one to be swapped with account two
     * @param accountTwo  Index of account two to be swapped with account one
     */
    private void swap(int accountOne, int accountTwo) {
        Account temp = accounts[accountOne];
        accounts[accountOne] = accounts[accountTwo];
        accounts[accountTwo] = temp;
    }

    /**
     * Compares account type of 2 accounts
     * Returns positive number if account one's type is after account two
     * Returns negative number if account one's type is before account two
     * Returns zero of both account type are the same
     *
     * @param accountOne Account one to be compared
     * @param accountTwo Account two to be compared
     * @return a positive number, a negative number or a zero depending
     *         on comparison results
     */
    private int compareAcctType(Account accountOne, Account accountTwo) {
        String accountTypeOne = accountOne.getClass().getSimpleName();
        String accountTypeTwo = accountTwo.getClass().getSimpleName();

        return accountTypeOne.compareTo(accountTypeTwo);
    }

    /**
     * Compares profiles of two accounts
     * Returns positive number if account one's profile is after account two
     * Returns negative number if account one's profile is before account two
     * Returns zero of both account profile are the same
     *
     * @param accountOne Account one to be compared
     * @param accountTwo Account two to be compared
     * @return a positive number, a negative number or a zero depending
     *         on comparison results
     */
    private int compareProfile(Account accountOne, Account accountTwo) {
        Profile accountProfileOne = accountOne.holder;
        Profile accountProfileTwo = accountTwo.holder;

        return accountProfileOne.compareTo(accountProfileTwo);
    }

    /**
     * If accounts are switched, it sorts the newly switched account by comparing to
     * previous accounts
     * Sorts by account type, followed by account profile.
     * 
     * @param accountToSort Account that was switched
     */
    private void sortAccountTypeProfile(int accountToSort) {
        while (accountToSort != 0) {
            int accountTypeCompare = compareAcctType(accounts[accountToSort], accounts[accountToSort - 1]);

            if (accountTypeCompare < 0) {
                swap(accountToSort, accountToSort - 1);
                accountToSort -= 1;

            } else if (accountTypeCompare == 0) {
                int accountProfileCompare = compareProfile(accounts[accountToSort], accounts[accountToSort - 1]);

                if (accountProfileCompare < 0) {
                    swap(accountToSort, accountToSort - 1);
                    accountToSort -= 1;

                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    /**
     * Sorts and prints account array in order of account type and profile
     */
    public String printSorted() {
        sortAccounts();
        return print();
    }

    /**
     * Sorts account array in order of account type and profile
     */
    private void sortAccounts() {
        for (int i = 0; i < numAcct - 1; i++) {
            int accountTypeCompare = compareAcctType(accounts[i], accounts[i + 1]);
            if (accountTypeCompare > 0) {
                swap(i, i + 1);

                sortAccountTypeProfile(i);
            } else if (accountTypeCompare == 0) {
                int accountProfileCompare = compareProfile(accounts[i], accounts[i + 1]);

                if (accountProfileCompare > 0) {
                    swap(i, i + 1);

                    sortAccountTypeProfile(i);
                }
            }
        }
    }

    /**
     * Calculate and prints the fees/interests
     */
    public String printFeesAndInterests() {
        sortAccounts();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < numAcct; i++) {
            output.append(accounts[i].toStringFeesInterest());
            output.append("\n");
        }

        return output.toString();
    }

    /**
     * Applies the fees and interests and update account balance
     * If account type is MoneyMarket, reset withdrawals to 0
     */
    public String printUpdatesBalances() {
        sortAccounts();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < numAcct; i++) {
            accounts[i].monthlyInterest();
            accounts[i].monthlyFee();
            if (accounts[i].getAccountTypeInitial().equals("MM")) {
                MoneyMarket account = (MoneyMarket) accounts[i];
                account.setWithdrawal(0);
            }
            output.append(accounts[i].toString());
            output.append("\n");
        }

        return output.toString();
    }
}
