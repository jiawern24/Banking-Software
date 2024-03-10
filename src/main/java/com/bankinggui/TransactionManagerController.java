package com.bankinggui;
import bankingsoftware.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;



/**
 * This is the controller class of the Transaction Manager.
 * This class contains the event handlers
 *
 * @author Jia Wern Chong, Frances Cortuna
 */
public class TransactionManagerController {
    private AccountDatabase accountDatabase = new AccountDatabase();
    private String activePane = "open";

    @FXML
    private AnchorPane openTab, depositTab, accTab;
    @FXML
    private TextArea textArea;
    @FXML
    private ToggleGroup toggleAcc, toggleCollege, toggleAcc2;
    @FXML
    private DatePicker dobbox1, dobbox2;
    @FXML
    private TextField fnamebox1, lnamebox1;
    @FXML
    private RadioButton buttonChecking, buttonCollege, buttonSavings, buttonMoney;
    @FXML
    private RadioButton buttonNB, buttonNewark, buttonCamden;
    @FXML
    private CheckBox buttonLoyal;
    @FXML
    private Button buttonOpen, buttonClose, buttonClear;
    @FXML
    private TextField fnamebox2, lnamebox2, amountbox;
    @FXML
    private RadioButton buttonChecking2, buttonCollege2, buttonSavings2, buttonMoney2;
    @FXML
    private Button buttonDeposit, buttonWithdraw;
    @FXML
    private Button buttonPrintAll, buttonLoadAcc, buttonPrintInterest, buttonUpdateAcc;

    /**
     * Initializes application by  connecting buttons and UI with event handlers
     */
    @FXML
    private void initialize() {
        openTab.setOnMouseClicked(this::handlePaneSwitch);
        buttonOpen.setOnAction(this::handleOpenButton);
        buttonClose.setOnAction(this::handleCloseButton);
        buttonClear.setOnAction(this::handleClearButton);

        depositTab.setOnMouseClicked(this::handlePaneSwitch);
        buttonDeposit.setOnAction(this::handleDepositWithdrawButton);
        buttonWithdraw.setOnAction(this::handleDepositWithdrawButton);

        accTab.setOnMouseClicked(this::handlePaneSwitch);
        buttonPrintAll.setOnAction(this::handleDisplayButton);
        buttonLoadAcc.setOnAction(this::handleLoadAccButton); //-10 points bc cannot load
        buttonPrintInterest.setOnAction(this::handleDisplayFeesInterestButton);
        buttonUpdateAcc.setOnAction(this::handleUpdateAccountsButton);

    }

    /**
     * This method allows user to choose only one account type
     */
    @FXML
    private void toggleDisable() {
        if (buttonCollege.isSelected()) {
            buttonNB.setDisable(false);
            buttonNewark.setDisable(false);
            buttonCamden.setDisable(false);
            buttonLoyal.setDisable(true);
            buttonLoyal.setSelected(false);

        } else if (buttonChecking.isSelected()) {
            buttonNB.setDisable(true);
            buttonNB.setSelected(false);
            buttonNewark.setDisable(true);
            buttonNewark.setSelected(false);
            buttonCamden.setDisable(true);
            buttonCamden.setSelected(false);
            buttonLoyal.setDisable(true);
            buttonLoyal.setSelected(false);

        } else if (buttonSavings.isSelected() || buttonMoney.isSelected()) {
            buttonNB.setDisable(true);
            buttonNB.setSelected(false);
            buttonNewark.setDisable(true);
            buttonNewark.setSelected(false);
            buttonCamden.setDisable(true);
            buttonCamden.setSelected(false);
            buttonLoyal.setDisable(false);
        }
    }

    /**
     * Changes the active pane variable when pane is switched
     *
     * @param event When a new pane is clicked
     */
    private void handlePaneSwitch(MouseEvent event) {
        Object source = event.getSource();

        if(source == openTab) {
            activePane = "open";
        } else if(source == depositTab) {
            activePane = "deposit";
        } else if(source == accTab) {
            activePane = "acc";
        }
    }

    /**
     * Checks if all the appropriate fields are filled in or selected.
     *
     * @param firstName First name from input
     * @param lastName Last name from input
     * @param dateOfBirth Date of birth from input
     * @return True if all fields are filled in, false otherwise
     */
    private boolean checkFields(String firstName, String lastName, LocalDate dateOfBirth) {
        if(firstName.isEmpty() || lastName.isEmpty() || dateOfBirth == null) {
            textArea.appendText("Error: Fill in all required fields (First name, last name) and input a valid date of birth\n");
            return false;
        }

        if(activePane.equals("open") && !buttonChecking.isSelected() && !buttonCollege.isSelected() && !buttonSavings.isSelected() && !buttonMoney.isSelected()) {
            textArea.appendText("Error: Select an account type.\n");
            return false;
        }

        if(activePane.equals("deposit") && !buttonChecking2.isSelected() && !buttonCollege2.isSelected() && !buttonSavings2.isSelected() && !buttonMoney2.isSelected()) {
            textArea.appendText("Error: Select an account type.\n");
            return false;
        }

        return true;
    }

    /**
     * Event Handler for the open button
     * Get the data required to add account from user input
     * @param event Event that triggers this handler
     */
    @FXML
    private void handleOpenButton(ActionEvent event) {
        String firstName = fnamebox1.getText().trim();
        String lastName = lnamebox1.getText().trim();
        LocalDate dateOfBirth = dobbox1.getValue();

        if(!checkFields(firstName, lastName, dateOfBirth)) return;

        String accountType = ((RadioButton) toggleAcc.getSelectedToggle()).getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = dateOfBirth.format(formatter);
        Date dob = new Date(formattedDate);

        switch(accountType) {
            case "Checking":
                parseChecking(firstName, lastName, dob, "Open");
                break;
            case "College Checking":
                if(!buttonNB.isSelected() && !buttonNewark.isSelected() && !buttonCamden.isSelected()) {
                    textArea.appendText("Error: Please select a campus for College Checking account\n");
                    return;
                }
                String campus = ((RadioButton) toggleCollege.getSelectedToggle()).getText();
                parseCollegeChecking(firstName, lastName, dob, campus, "Open");
                break;
            case "Savings":
                parseSavings(firstName, lastName, dob, "Open");
                break;
            case "Money Market":
                parseMoneyMarket(firstName, lastName, dob, "Open");
                break;
        }
    }

    /**
     * Event Handler for the close button
     * Get the data required to close account from user input
     * @param event Event that triggers this handler
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        String firstName = fnamebox1.getText().trim();
        String lastName = lnamebox1.getText().trim();
        LocalDate dateOfBirth = dobbox1.getValue();

        if(!checkFields(firstName, lastName, dateOfBirth)) return;

        String accountType = ((RadioButton) toggleAcc.getSelectedToggle()).getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = dateOfBirth.format(formatter);
        Date dob = new Date(formattedDate);

        switch(accountType) {
            case "Checking":
                parseChecking(firstName, lastName, dob, "closing");
                break;
            case "College Checking":
                parseCollegeChecking(firstName, lastName, dob, "NB", "closing");
                break;
            case "Savings":
                parseSavings(firstName, lastName, dob, "closing");
                break;
            case "Money Market":
                parseMoneyMarket(firstName, lastName, dob, "closing");
                break;
        }
    }

    /**
     * Method to clear user input for the clear button in Open/Close tab
     */
    @FXML
    private void handleClearButton(ActionEvent actionEvent) {
        fnamebox1.clear();
        lnamebox1.clear();
        dobbox1.setValue(LocalDate.now());
        buttonChecking.setSelected(false);
        buttonCollege.setSelected(false);
        buttonNB.setSelected(false);
        buttonNewark.setSelected(false);
        buttonCamden.setSelected(false);
        buttonSavings.setSelected(false);
        buttonMoney.setSelected(false);
        buttonLoyal.setSelected(false);
    }

    /**
     * Opens a new account
     *
     * @param account New account object to open
     */
    private void openAccount(Account account) {
        accountDatabase.open(account);
        Profile profile = account.getProfile();
        textArea.appendText(String.format("%s %s %s(%s) opened.\n", profile.getFname(), profile.getLname(),
                profile.getDOB().toString(), account.getAccountTypeInitial()));
    }

    /**
     * Closes an account.
     * Throws an exception if account is not in database.
     *
     * @param profile Profile of account to close
     * @param accountTypeInitial The initial of the account type
     */
    private void closeAccount(Profile profile, String accountTypeInitial) {
        try {
            for (int i = 0; i < accountDatabase.getNumAcct(); i++) {
                Account otherAccount = accountDatabase.getAccountsArray()[i];
                Profile otherProfile = otherAccount.getProfile();
                if (profile.equals(otherProfile) && accountTypeInitial.equals(otherAccount.getAccountTypeInitial())) {
                    accountDatabase.close(otherAccount);
                    textArea.appendText(
                            String.format("%s %s %s(%s) has been closed.\n", profile.getFname(), profile.getLname(),
                                    profile.getDOB().toString(), accountTypeInitial));
                    return;
                }
            }

            throw new Exception(String.format("%s %s %s(%s) is not in the database.\n",
                    profile.getFname(), profile.getLname(), profile.getDOB().toString(), accountTypeInitial));
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
        }
    }

    /**
     * Parses input data into a Checking object
     * Also checks to make sure all data is valid.
     */
    private void parseChecking(String fname, String lname, Date dob, String action) {

        if (!checkDOB(dob, "Checking")) return;

        Profile profile = new Profile(fname, lname, dob);

        if (action.equals("closing")) {
            closeAccount(profile, "C");
            return;
        }

        Checking newChecking = new Checking(profile, 0.0);
        if (!checkDuplicateProfile(newChecking)) return;
        openAccount(newChecking);
    }

    /**
     * Opens college checking account with input data.
     * Also checks to make sure all data is valid.
     */
    private void parseCollegeChecking(String fname, String lname, Date dob, String campus, String action) {

        if (!checkDOB(dob, "College Checking")) return;

        Profile profile = new Profile(fname, lname, dob);

        if (action.equals("closing")) {
            closeAccount(profile, "CC");
            return;
        }

        Campus campusCode = parseCampusCode(campus);

        CollegeChecking newCollegeChecking = new CollegeChecking(profile, 0.0, campusCode);
        if (!checkDuplicateProfile(newCollegeChecking)) return;
        openAccount(newCollegeChecking);
    }

    /**
     * Takes campus selection and returns appropriate Campus object
     * @param campus Campus selection
     */
    private Campus parseCampusCode(String campus) {
        return switch (campus) {
            case "New Brunswick" -> Campus.NEW_BRUNSWICK;
            case "Newark" -> Campus.NEWARK;
            case "Camden" -> Campus.CAMDEN;
            default -> null;
        };
    }

    /**
     * Opens savings account with input data.
     * Also checks to make sure all data is valid.
     */
    private void parseSavings(String fname, String lname, Date dob, String action) {
        if (!checkDOB(dob, "Savings")) return;

        Profile profile = new Profile(fname, lname, dob);

        if (action.equals("closing")) {
            closeAccount(profile, "S");
            return;
        }

        Savings newSavings = new Savings(profile, 0.0, buttonLoyal.isSelected());
        if (!checkDuplicateProfile(newSavings)) return;
        openAccount(newSavings);
    }

    /**
     * Opens money market savings account with input data.
     * Also checks to make sure all data is valid.
     */
    private void parseMoneyMarket(String fname, String lname, Date dob, String action) {
        if (!checkDOB(dob, "Savings")) return;

        Profile profile = new Profile(fname, lname, dob);

        if (action.equals("closing")) {
            closeAccount(profile, "MM");
            return;
        }

        boolean loyaltyStatus = true;

        MoneyMarket newMoneyMarket = new MoneyMarket(profile, 0.0, loyaltyStatus, 0);
        if (!checkDuplicateProfile(newMoneyMarket)) return;
        openAccount(newMoneyMarket);
    }

    /**
     * Checks that DOB is a valid past date and that profile in at least 16 years old.
     * If account type is CC, also checks that profile is under 24 years old.
     * Returns true if DOB is valid and profile is old enough and returns false otherwise
     *
     * @param dob DOB of profile
     * @return True if DOB and age is valid, false otherwise.
     */
    private boolean checkDOB(Date dob, String accType) {
        try {
            Date today = new Date();
            int dateComparison = dob.compareTo(today);

            if (!dob.isValid()) {
                throw new Exception("DOB invalid: is not a valid calendar date.\n");
            }

            if (dateComparison >= 0) {
                throw new Exception("DOB invalid: cannot be today or a future date.\n");
            }

            if (today.getYear() - dob.getYear() < 16) {
                throw new Exception("DOB invalid: user under 16.\n");
            } else if (today.getYear() - dob.getYear() == 16) {
                if (today.getMonth() < dob.getMonth()
                        || (today.getMonth() == dob.getMonth() && today.getDay() < dob.getDay())) {
                    throw new Exception("DOB invalid: user under 16.\n");
                }
            }

            if (accType.equals("College Checking")) {
                if (today.getYear() - dob.getYear() > 24) {
                    throw new Exception("DOB invalid: user over 24.\n");
                } else if(today.getYear() - dob.getYear() == 24) {
                    if (today.getMonth() > dob.getMonth()
                            || (today.getMonth() == dob.getMonth() && today.getDay() > dob.getDay())) {
                        throw new Exception("DOB invalid: user over 24.\n");
                }
                }
            }
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Checks if new profile matches existing profile in account database.
     * Also checks if a person opening a Checking or College Checking does not
     * already have another Checking or College Checking account.
     *
     * @param account New profile being made
     * @return True if account is not already in account database, false otherwise.
     */
    private boolean checkDuplicateProfile(Account account) {
        try{
            Profile profile = account.getProfile();

            if (account.getAccountType().equals("Checking") || account.getAccountType().equals("College Checking")) {
                for (int i = 0; i < accountDatabase.getNumAcct(); i++) {
                    Account otherAccount = accountDatabase.getAccountsArray()[i];
                    Profile otherProfile = otherAccount.getProfile();

                    if (account.getProfile().equals(otherProfile) && (otherAccount.getAccountType().equals("Checking")
                            || otherAccount.getAccountType().equals("College Checking"))) {
                        throw new Exception(String.format("%s %s %s(%s) is already in the database.\n",
                                profile.getFname(), profile.getLname(), profile.getDOB().toString(),
                                account.getAccountTypeInitial()));
                    }
                }
            }

            if (accountDatabase.contains(account)) {
                throw new Exception(
                        String.format("%s %s %s(%s) is already in the database.\n", profile.getFname(),
                                profile.getLname(), profile.getDOB().toString(), account.getAccountTypeInitial()));
            }
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Parses user input for deposit.
     *
     * @param event Clicking deposit button
     */
    @FXML
    private void handleDepositWithdrawButton(ActionEvent event) {
        String firstName = fnamebox2.getText().trim();
        String lastName = lnamebox2.getText().trim();
        LocalDate dateOfBirth = dobbox2.getValue();

        if(!checkFields(firstName, lastName, dateOfBirth)) return;

        String accountType = ((RadioButton) toggleAcc2.getSelectedToggle()).getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = dateOfBirth.format(formatter);
        Date dob = new Date(formattedDate);

        Profile profile = new Profile(firstName, lastName, dob);

        String value = amountbox.getText().trim();
        if (!checkAmount(value, "Deposit")) return;

        Double amount = Double.parseDouble(value);

        Account account = null;
        String accountTypeInitial = null;

        if (accountType.equals("Checking")) {
            accountTypeInitial = "C";
            account = new Checking(profile, amount);
        } else if (accountType.equals("College Checking")) {
            accountTypeInitial = "CC";
            account = new CollegeChecking(profile, amount);
        } else if (accountType.equals("Savings")) {
            accountTypeInitial = "S";
            account = new Savings(profile, amount);
        } else if (accountType.equals("Money Market")) {
            accountTypeInitial = "MM";
            account = new MoneyMarket(profile, amount);
        }

        if (event.getSource() == buttonDeposit) {
            depositMoney(account, accountTypeInitial);
        } else if (event.getSource() == buttonWithdraw) {
            withdrawMoney(account, accountTypeInitial);
        }
    }

    /**
     * Checks deposit or withdrawal amount to ensure it is a valid amount.
     *
     * @param balance Amount to be deposited or withdrawn
     * @param action Deposit or withdraw
     * @return True if amount is valid, false otherwise.
     */
    private boolean checkAmount(String balance, String action) {
        try {
            double amount = Double.parseDouble(balance);

            if (amount <= 0) {
                throw new Exception(String.format("%s - amount cannot be 0 or negative.\n", action));
            }
        } catch (NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
            return false;
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Deposits money into an account.
     * Throws an exception if account is not in database.
     *
     * @param account Account to deposit money into
     * @param accountTypeInitial Type of account in initial form.
     */
    private void depositMoney(Account account, String accountTypeInitial) {
        Profile profile = account.getProfile();
        try {
            if (!accountDatabase.contains(account)) {
                throw new Exception(String.format("%s %s %s(%s) is not in the database.\n",
                        profile.getFname(), profile.getLname(), profile.getDOB().toString(), accountTypeInitial));
            }

            accountDatabase.deposit(account);
            textArea.appendText(
                    String.format("%s %s %s(%s) Deposit - balance updated.\n", profile.getFname(), profile.getLname(),
                            profile.getDOB().toString(), accountTypeInitial));
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
        }
    }

    /**
     * Withdraw money into an account.
     * Throws an exception if account is not in database or if there is insufficient
     * funds.
     *
     * @param account Account to withdraw money from
     * @param accountTypeInitial Type of account in initial form.
     */
    private void withdrawMoney(Account account, String accountTypeInitial) {
        Profile profile = account.getProfile();
        try {
            if (!accountDatabase.contains(account)) {
                throw new Exception(String.format("%s %s %s(%s) is not in the database.\n",
                        profile.getFname(), profile.getLname(), profile.getDOB().toString(), accountTypeInitial));
            }

            if (!accountDatabase.withdraw(account)) {
                throw new Exception(String.format("%s %s %s(%s) Withdraw - insufficient fund.\n",
                        profile.getFname(), profile.getLname(), profile.getDOB().toString(), accountTypeInitial));
            } else {
                textArea.appendText(String.format("%s %s %s(%s) Withdraw - balance updated.\n",
                        profile.getFname(), profile.getLname(), profile.getDOB().toString(), accountTypeInitial));
            }
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
        }
    }

    /**
     * Displays all accounts sorted by account types.
     */
    @FXML
    private void handleDisplayButton(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textArea.appendText("Account Database is empty!\n");
            return;
        }
        textArea.appendText("\n*Accounts sorted by account type and profile.\n");
        String accountInfo = accountDatabase.printSorted();
        textArea.appendText(accountInfo);
        textArea.appendText("*end of list.\n\n");
    }

    /**
     * Event Handler for the Load Accounts from a File button
     * Read file to get the information of the accounts before adding
     * the account to Account Database.
     * @param event
     */
    @FXML
    private void handleLoadAccButton(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File readFile = fileChooser.showOpenDialog(null);
            Scanner fileScanner = new Scanner(readFile);

            while(fileScanner.hasNextLine()) {
                StringTokenizer fileString = new StringTokenizer(fileScanner.nextLine(), ",");
                String fileCommand = fileString.nextToken();

                switch (fileCommand) {
                    case "C" -> addCheckingLoad(fileString);
                    case "CC" -> addCollegeCheckingLoad(fileString);
                    case "S" -> addSavingsLoad(fileString);
                    case "MM" -> addMoneyMarketLoad(fileString);
                    default -> {textArea.appendText("Invalid account type.\n");}
                }
            }
            textArea.appendText("Accounts loaded.\n");
        } catch (FileNotFoundException e) {
            textArea.appendText("File not found\n");
        } if (accountDatabase.getNumAcct() == 0) {
            textArea.appendText("Account Database is empty.\n");
        }
    }

    /**
     * Add Checking to Account Database from read file
     * Only called at loadAcc()
     * @param input The data token to add Checking
     */
    private void addCheckingLoad(StringTokenizer input) {
        if (input.countTokens() != 5) {
            textArea.appendText("Missing data for loading an account.\n");
            return;
        }
        String fname = input.nextToken();
        String lname = input.nextToken();
        String dob = input.nextToken();
        double amount = Double.parseDouble(input.nextToken());
        Date date = new Date(dob);
        boolean check = checkDOB(date, "Checking");

        if (check) {
            Profile profile = new Profile(fname, lname, date);
            Checking checkingAcc = new Checking(profile, amount);

            if (!accountDatabase.contains(checkingAcc)) {
                accountDatabase.open(checkingAcc);
            }
        }
    }

    /**
     * Add College Checking to Account Database from read file
     * Only called at loadAcc()
     * @param input The data token to add College Checking
     */
    private void addCollegeCheckingLoad(StringTokenizer input) {
        if (input.countTokens() != 5) {
            textArea.appendText("Missing data for loading an account.\n");
            return;
        }
        String fname = input.nextToken();
        String lname = input.nextToken();
        String dob = input.nextToken();
        double amount = Double.parseDouble(input.nextToken());
        Date date = new Date(dob);
        boolean check = checkDOB(date, "College Checking");

        if (check) {
            Profile profile = new Profile(fname, lname, date);
            CollegeChecking collegeCheckingAcc = new CollegeChecking(profile, amount);

            if (!accountDatabase.contains(collegeCheckingAcc)) {
                accountDatabase.open(collegeCheckingAcc);
            }
        }
    }

    /**
     * Parses integer to return boolean for loyalty status
     *
     * @param loyaltyStatusCode Loyalty status code from input
     * @return Appropriate boolean for loyalty status
     */
    private boolean parseLoyaltyStatus(int loyaltyStatusCode) {
        switch (loyaltyStatusCode) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new IllegalArgumentException("Invalid loyal customer status code.");
        }
    }

    /**
     * Add Savings to Account Database from read file
     * Only called at loadAcc()
     * @param input The data token to add Savings
     */
    private void addSavingsLoad(StringTokenizer input) {
        if (input.countTokens() != 5) {
            textArea.appendText("Missing data for loading an account.\n");
            return;
        }
        String fname = input.nextToken();
        String lname = input.nextToken();
        String dob = input.nextToken();
        double amount = Double.parseDouble(input.nextToken());
        int loyaltyStatusCode = Integer.parseInt(input.nextToken());

        boolean loyaltyStatus = parseLoyaltyStatus(loyaltyStatusCode);
        Date date = new Date(dob);
        boolean check = checkDOB(date, "College Checking");

        if (check) {
            Profile profile = new Profile(fname, lname, date);
            Savings savingsAcc = new Savings(profile, amount, loyaltyStatus);

            if (!accountDatabase.contains(savingsAcc)) {
                accountDatabase.open(savingsAcc);
            }
        }
    }

    /**
     * Add Money Market to Account Database from read file
     * Only called at loadAcc()
     * @param input The data token to add Money Market
     */
    private void addMoneyMarketLoad(StringTokenizer input) {
        if (input.countTokens() != 5) {
            textArea.appendText("Missing data for loading an account.\n");
            return;
        }
        String fname = input.nextToken();
        String lname = input.nextToken();
        String dob = input.nextToken();
        double amount = Double.parseDouble(input.nextToken());
        int loyaltyStatusCode = Integer.parseInt(input.nextToken());

        boolean loyaltyStatus = parseLoyaltyStatus(loyaltyStatusCode);
        Date date = new Date(dob);
        boolean check = checkDOB(date, "College Checking");
    }

    /**
     * Displays all accounts sorted by account type with calculated fees and monthly interests.
     */
    @FXML
    private void handleDisplayFeesInterestButton(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textArea.appendText("Account Database is empty!\n");
            return;
        }
        textArea.appendText("\n*list of accounts with fee and monthly interest.\n");
        String accountInfo = accountDatabase.printFeesAndInterests();
        textArea.appendText(accountInfo);
        textArea.appendText("*end of list.\n");
    }

    /**
     * Applies fees and monthly interests to accounts.
     * Then prints accounts sorted by account type.
     */
    @FXML
    private void handleUpdateAccountsButton(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textArea.appendText("Account Database is empty!");
            return;
        }
        textArea.appendText("\n*list of accounts with fees and interests applied.\n");
        String accountInfo = accountDatabase.printUpdatesBalances();
        textArea.appendText(accountInfo);
        System.out.println("*end of list.\n");
    }
}