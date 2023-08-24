import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static short id = 0;
    public static short idOfActualAccount = 0;

    public static void main(String[] args) {


        accountClass [] accounts = new accountClass[10];

        System.out.print("\033[H\033[2J");

        System.out.println("Welcome to the bank system");

        menu(accounts);

    }

    public static void menu(accountClass[] accounts){
        String option;
        String password;

        short account;

        Scanner input = new Scanner(System.in);

        newAccount(accounts);

        while (true) {
            do {
                System.out.println("Options menu:\nEnter account\nNew Account\nQuit");
                option = input.nextLine();
                switch (option.toLowerCase()) {
                    case "enter account" -> {
                        System.out.println("Enter the id of the account:");
                        try {

                            account = (short) Integer.parseInt(input.nextLine());
                        }
                        catch (NumberFormatException e){
                            System.out.println("No id inserted");
                            System.out.println("press enter to continue");
                            input.nextLine();
                            System.out.print("\033[H\033[2J");
                            break;
                        }
                        if (id<=account){
                            System.out.println("Id of a invalid account");
                            System.out.println("press enter to continue");
                            input.nextLine();
                            System.out.print("\033[H\033[2J");
                            break;
                        }
                        System.out.println("Details of the account\nName: " + accounts[account].name
                                + "\nID: " + accounts[account].id);
                        System.out.println("Enter the password of te other account:");
                        password = input.nextLine();
                        if (password.equals(accounts[account].password)) {
                            idOfActualAccount = account;
                            System.out.println("right password");
                            System.out.println("press enter to continue");
                            input.nextLine();
                            System.out.print("\033[H\033[2J");
                            accountsMenu(accounts);
                        } else {
                            System.out.println("wrong password");
                            System.out.println("press enter to continue");
                            input.nextLine();
                            System.out.print("\033[H\033[2J");
                        }
                    }
                    case "new account" -> {
                        newAccount(accounts);
                        idOfActualAccount++;
                    }
                    case "quit" -> {
                        System.out.println("thanks for using ours services");
                        return;
                    }
                    default -> System.out.println("invalid option");
                }
            } while (!option.equalsIgnoreCase("quit"));
        }
    }
    public static void accountsMenu(accountClass[] accounts) {
        String option;

        int withdraw;
        int deposit;
        int transfer;

        byte index;
        byte idToTransfer;

        Scanner input = new Scanner(System.in);

        DateTimeFormatter timeDateFormat = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        do {
            System.out.println("Options menu:\nBalance\nExtract\nDeposit\nWithdraw\ntransfer\nOthers\nLog Out");
            option = input.nextLine();

            switch (option.toLowerCase()) {
                case "balance" -> System.out.println("Balance in the account: "
                        + NumberFormat.getCurrencyInstance().format(accounts[idOfActualAccount].balance));

                case "extract" -> {
                    System.out.println("\nDeposits:");
                    for (index = 0; index < accounts[idOfActualAccount].depositsIndex; index++) {
                        System.out.println("Deposit of: "
                                + NumberFormat.getCurrencyInstance().format(accounts[idOfActualAccount].deposits[index])
                                + " make at:" +
                                accounts[idOfActualAccount].depositDataList[index]);
                    }
                    System.out.println("\nWithdraws:");
                    for (index = 0; index < accounts[idOfActualAccount].withdrawsIndex; index++) {
                        System.out.println("Withdraw of: "
                                + NumberFormat.getCurrencyInstance().format(accounts[idOfActualAccount].withdraws[index])
                                + " make at: " +
                                accounts[idOfActualAccount].withdrawDataList[index]);
                    }
                    System.out.println("\ntransfers:");
                    System.out.println("Transfers done:");
                    for (index = 0; index < accounts[idOfActualAccount].transferDoneIndex; index++) {
                        System.out.println("transfer of: "
                                + NumberFormat.getCurrencyInstance().format(accounts[idOfActualAccount].transferDone[index])
                                +" to account of id: "+accounts[idOfActualAccount].accountToTransfer[index]
                                + " at: " +
                                accounts[idOfActualAccount].transferDoneDataList[index]);
                    }
                    System.out.println("Transfers received:");
                    for (index = 0; index < accounts[idOfActualAccount].transferReceivedIndex; index++) {
                        System.out.println("transfer of: "
                                + NumberFormat.getCurrencyInstance().format(accounts[idOfActualAccount].transferReceived[index])
                                +" received from account of id: "+accounts[idOfActualAccount].accountToTransfer[index]
                                +" at: "
                                +accounts[idOfActualAccount].transferReceivedDataList[index]);
                    }
                }
                case "deposit" -> {
                    System.out.print("Price to deposit: ");
                    try {
                        deposit = Integer.parseInt(input.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("invalid input\nthe input is not a number");
                        break;
                    }
                    if (deposit <= 0) {
                        System.out.println("invalid price to deposit\nPrice equal or lower than zero");
                        break;
                    }
                    accounts[idOfActualAccount].balance = accounts[idOfActualAccount].balance + deposit;
                    accounts[idOfActualAccount].deposits[accounts[idOfActualAccount].depositsIndex] = deposit;
                    accounts[idOfActualAccount].depositsIndex++;
                    accounts[idOfActualAccount].depositDataList[accounts[idOfActualAccount].depositDataListIndex]
                            = timeDateFormat.format(LocalDateTime.now());
                    accounts[idOfActualAccount].depositDataListIndex++;
                }
                case "withdraw" -> {
                    System.out.println("Price to withdraw: ");
                    try {
                        withdraw = Integer.parseInt(input.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("invalid input\nthe input is not a number");
                        break;
                    }
                    if (withdraw <= 0) {
                        System.out.println("invalid price to withdraw\nPrice equal or lower than zero");
                        break;
                    }
                    if (withdraw > accounts[idOfActualAccount].balance) {
                        System.out.println("Invalid price" +
                                "\nThe price selected to withdraw is greater to the total balance in the account.");
                    } else {
                        accounts[idOfActualAccount].balance = accounts[idOfActualAccount].balance - withdraw;
                        accounts[idOfActualAccount].withdraws[accounts[idOfActualAccount].withdrawsIndex] = withdraw;
                        accounts[idOfActualAccount].withdrawsIndex++;
                        accounts[idOfActualAccount].withdrawDataList[accounts[idOfActualAccount].withdrawDataListIndex]
                                = timeDateFormat.format(LocalDateTime.now());
                        accounts[idOfActualAccount].withdrawDataListIndex++;
                    }
                }
                case "transfer"->{
                    System.out.println("account to transfer:");
                    try {
                        idToTransfer = (byte) Integer.parseInt(input.nextLine());
                    }catch (NumberFormatException e){
                        System.out.println("invalid input\nthe input is not a number");
                        break;
                    }
                    if(idToTransfer<id&&idToTransfer!=idOfActualAccount) {
                        System.out.println("price to transfer:");
                        try {
                            transfer = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("invalid input\nthe input is not a number");
                            break;
                        }
                        if (transfer > accounts[idOfActualAccount].balance) {
                            System.out.println("Invalid price" +
                                    "\nThe price selected to transfer is greater to the total balance in the account.");
                        } else {
                            accounts[idOfActualAccount].balance = accounts[idOfActualAccount].balance - transfer;
                            accounts[idOfActualAccount].transferDone[accounts[idOfActualAccount].transferDoneIndex]
                                    = transfer;
                            accounts[idOfActualAccount].transferDoneIndex++;
                            accounts[idOfActualAccount].transferDoneDataList[accounts[idOfActualAccount]
                                    .transferDoneDataListIndex]
                                    = timeDateFormat.format(LocalDateTime.now());
                            accounts[idOfActualAccount].transferDoneDataListIndex++;
                            accounts[idOfActualAccount].accountToTransfer[accounts[idOfActualAccount]
                                    .accountToTransferIndex]
                                    = idToTransfer;
                            accounts[idOfActualAccount].accountToTransferIndex++;

                            accounts[idToTransfer].balance = accounts[idToTransfer].balance + transfer;
                            accounts[idToTransfer].transferReceived[accounts[idToTransfer].transferReceivedIndex]
                                    = transfer;
                            accounts[idToTransfer].transferReceivedIndex++;
                            accounts[idToTransfer].transferReceivedDataList[accounts[idToTransfer]
                                    .transferReceivedDataListIndex]
                                    = timeDateFormat.format(LocalDateTime.now());
                            accounts[idToTransfer].transferReceivedDataListIndex++;
                            accounts[idToTransfer].accountThatTransfer[accounts[idToTransfer]
                                    .accountThatTransferIndex]
                                    = idOfActualAccount;
                            accounts[idToTransfer].accountThatTransferIndex++;
                        }
                    } else
                        System.out.println("invalid id");
                }
                case "others"-> {
                        System.out.println("Account details\nChose one to change:\nPassword\nName\nQuit");
                        option = input.nextLine();
                        switch (option.toLowerCase()) {
                            case "password" -> {
                                System.out.println("Change password");
                                for (byte i = 3; i>=0;i--) {
                                    System.out.print("Type your password: ");
                                    if (input.nextLine().equalsIgnoreCase(accounts[idOfActualAccount].password)) {
                                        System.out.print("Type your new password: ");
                                        accounts[idOfActualAccount].password = input.nextLine();
                                        System.out.print("\033[H\033[2J");
                                        System.out.println("Your new password is: " + accounts[idOfActualAccount].password);
                                        i=0;
                                    } else {
                                        System.out.print("\033[H\033[2J");
                                        System.out.println("Wrong password\nYou have more "+i+" chances");
                                    }
                                }
                            }
                            case "name" -> {
                                System.out.print("Type your new account name: ");
                                accounts[idOfActualAccount].name = input.nextLine();
                                System.out.print("\033[H\033[2J");
                                System.out.println("Your new account name is: " + accounts[idOfActualAccount].name);
                            }
                            case "quit" -> System.out.println("returning to account menu");
                            default -> System.out.println("invalid option");
                        }
                }
                case "log out" ->
                    System.out.println("Log outing of this account");


                default -> System.out.println("invalid option");

            }
            System.out.println("press enter to continue");
            input.nextLine();
            System.out.print("\033[H\033[2J");
        } while (!option.equalsIgnoreCase("log out"));
    }
    public static void newAccount(accountClass[] accounts) {
        String name;
        String password;
        String [] depositDataList = new String[10];
        String [] withdrawDataList = new String[10];
        String [] transferDoneDataList = new String[10];
        String [] transferReceivedDataList = new String[10];
        int [] withdraws = new int [10];
        int [] deposits = new int [10];
        int [] transferDone = new int [10];
        int [] transferReceived = new int [10];
        short [] accountToTransfer = new short[10];
        short [] accountThatTransfer = new short[10];
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name:");
        name = input.nextLine();
        System.out.print("Enter the password of the account:");
        password = input.nextLine();
        accounts[id] = new accountClass(name,id,0,password,withdrawDataList,depositDataList,transferDoneDataList
                ,transferReceivedDataList,withdraws,deposits,transferDone,transferReceived,accountToTransfer
                ,accountThatTransfer,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0);
        System.out.print("\033[H\033[2J");
        System.out.println("Details of the account\nName: "+accounts[id].name
                +"\nID: "+accounts[id].id+"\nPassword: "+accounts[id].password);
        System.out.println("press enter to continue");
        input.nextLine();
        System.out.print("\033[H\033[2J");
        idOfActualAccount = id;
        id++;
        accountsMenu(accounts);
    }
    static class accountClass {
        String name;
        short id;
        int balance;
        String password;
        String [] depositDataList;
        String [] withdrawDataList;
        String [] transferDoneDataList;
        String [] transferReceivedDataList;
        int [] withdraws;
        int [] deposits;
        int [] transferDone;
        int [] transferReceived;
        short [] accountToTransfer;
        short [] accountThatTransfer;
        byte accountToTransferIndex;
        byte accountThatTransferIndex;
        byte withdrawsIndex;
        byte depositsIndex;
        byte transferDoneIndex;
        byte transferReceivedIndex;

        byte withdrawDataListIndex;
        byte depositDataListIndex;
        byte transferDoneDataListIndex;
        byte transferReceivedDataListIndex;
        accountClass (String name, short id, int balance, String password,String [] depositDataList
                ,String [] withdrawDataList,String [] transferDoneDataList,String [] transferReceivedDataList
                ,int [] withdraws,int [] deposits,int [] transferDone,int [] transferReceived,short [] accountToTransfer
                ,short [] accountThatTransfer,byte accountToTransferIndex,byte accountThatTransferIndex,byte withdrawsIndex
                ,byte depositsIndex,byte transferDoneIndex,byte transferReceivedIndex,byte withdrawDataListIndex
                ,byte depositDataListIndex,byte transferDoneDataListIndex,byte transferReceivedDataListIndex){

            this.name = name;
            this.id = id;
            this.balance = balance;
            this.password = password;
            this.depositDataList = depositDataList;
            this.withdrawDataList = withdrawDataList;
            this.transferDoneDataList = transferDoneDataList;
            this.transferReceivedDataList = transferReceivedDataList;
            this.withdraws = withdraws;
            this.deposits = deposits;
            this.transferDone = transferDone;
            this.transferReceived = transferReceived;
            this.accountToTransfer = accountToTransfer;
            this.accountThatTransfer = accountThatTransfer;
            this.accountToTransferIndex = accountToTransferIndex;
            this.accountThatTransferIndex = accountThatTransferIndex;
            this.withdrawsIndex = withdrawsIndex;
            this.depositsIndex = depositsIndex;
            this.transferDoneIndex = transferDoneIndex;
            this.transferReceivedIndex = transferReceivedIndex;
            this.withdrawDataListIndex = withdrawDataListIndex;
            this.depositDataListIndex = depositDataListIndex;
            this.transferDoneDataListIndex = transferDoneDataListIndex;
            this.transferReceivedDataListIndex = transferReceivedDataListIndex;
        }
    }
}
