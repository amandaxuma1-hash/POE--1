import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Message> GlobalMessageHistory = new ArrayList<>();
    static int totalMessagesSent = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String name;
        String surname;
        String username;
        String password;
        String cellphoneNumber;

        char c = 'a';
        boolean isPasswordValid = false;
        boolean isUsernameValid = false;
        boolean isPhoneValid = false;

        Login1 login = new Login1();

        System.out.println("===============Registration===============");


        System.out.println("Enter your first name:");
        name = sc.nextLine();


        System.out.println("Enter your Surname");
        surname = sc.nextLine();

        login.storePersonalDetails(name, surname);

        while (!isUsernameValid) {
            System.out.println("Enter your username (Max 5 characters must have an underscore'_'):");

            username = sc.nextLine();
            isUsernameValid = login.checkUserName(username);

        }
        while (!isPasswordValid) {
            System.out.println("Enter Password (Min 8 char, 1 uppercase, 1 digit, 1 special):");
            password = sc.nextLine();
            isPasswordValid = login.checkPassword(password);


        }
        while (!isPhoneValid) {
            System.out.println("Enter a cell phone number (e.g., +27123456789):");
            cellphoneNumber = sc.nextLine();
            isPhoneValid = login.checkPhoneNumber(cellphoneNumber);


        }
        System.out.println("You are registered successfully!" + login.storedUsername);

        //=============== Login Phase ===============

        System.out.println("===============Login===============");

        boolean loginPassed = false;

        while (!loginPassed) {
            System.out.println("Enter your username to login:");
            String loginUser = sc.nextLine();

            System.out.println("Enter your password to login:");
            String loginPass = sc.nextLine();

            loginPassed = login.loginUser(loginUser, loginPass);

            System.out.println(login.returnLoginStatus(loginPassed));
        }

        // ========================================================
        // PART 2 STARTS HERE: QuickChat Menu
        // ========================================================
        System.out.println("Welcome to QuickChat.");
        int menuSelection = 0;

        while (menuSelection != 3) {
            System.out.println("--- QuickChat Application Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Please select an option: ");

            if (sc.hasNextInt()) {
                menuSelection = sc.nextInt();
                sc.nextLine();


            switch (menuSelection) {
                case 1:
                    runSendMessageWorkflow(sc);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Exiting application workflow loop. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection option context bounds.");
            }
            } else {
                System.out.println("Invalid choice input. Please select via option digit numbers.");
                sc.nextLine();
                continue;
            }
        }

        sc.close();
    }

    private static void runSendMessageWorkflow(Scanner sc) {
        System.out.print("How many messages do you wish to enter? ");
        int totalBatchCount = 0;

        if (sc.hasNextInt()) {
            totalBatchCount = sc.nextInt();
            sc.nextLine(); //
        } else {
            System.out.println("Invalid input count detected.");
            sc.nextLine();
            return;
        }

        for (int i = 0; i < totalBatchCount; i++) {
            System.out.println("------Processing Message " + (i + 1) + " of " + totalBatchCount + "-------");
            Message messageInstance = new Message();

            if (!messageInstance.checkMessageID()) {
                System.out.println("Critical Error occurred: Invalid ID structure encountered.");
                continue;
            }

            while (true) {
                System.out.print("Enter Recipient Cell Number (e.g., +27821234567): ");
                String cellInput = sc.nextLine();
                if (messageInstance.checkRecipientCell(cellInput).equals("Cell phone number successfully captured.")) {
                    messageInstance.setRecipientCell(cellInput);
                    break;
                } else {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the code and try again." );
                }
            }

            while (true) {
                System.out.print("Enter message body content text: ");
                String textInput = sc.nextLine();
                if (textInput.length() <= 250) {
                    System.out.println("Message sent");
                    messageInstance.setMessageText(textInput);
                    break;
                } else {
                    System.out.println("Please enter a message of less than 250 characters.");
                }
            }

            messageInstance.createMessageHash(totalMessagesSent, messageInstance.getMessageText());

            System.out.println("Select structural handling path outcome routines:");
            System.out.println("1 - Send Message");
            System.out.println("2 - Disregard Message");
            System.out.println("3 - Store Message");
            System.out.print("Action choice selection input: ");

            int handlingChoice = 0;
            if (sc.hasNextInt()) {
                handlingChoice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
            }

            System.out.println(messageInstance.SentMessage(handlingChoice));

            if (handlingChoice == 2) {
                System.out.println("Message discarded by manual input rule triggers.");
                continue;
            }

            GlobalMessageHistory.add(messageInstance);
            totalMessagesSent++;

            System.out.println(messageInstance.printMessages());
        }

        returnTotalMessages();
    }

    public static int returnTotalMessages() {
        System.out.println(">>> Cumulative program messages processed history: " + totalMessagesSent + " <<<");
        return totalMessagesSent;
    }

}



