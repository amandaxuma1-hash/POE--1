import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    static ArrayList<Message> GlobalMessageHistory = new ArrayList<>();
    static int totalMessagesSent = 0;

    static final int MAX_MESSAGES = 100;
    static String[] sentMessages = new String[MAX_MESSAGES];
    static String[] disregardedMessages = new String[MAX_MESSAGES];
    static String[] storedMessages = new String[MAX_MESSAGES];
    static String[] messageHashes = new String[MAX_MESSAGES];
    static String[] messageIDs = new String[MAX_MESSAGES];

    static String[] sentRecipients = new String[MAX_MESSAGES];
    static String[] sentIDs = new String[MAX_MESSAGES];
    static String[] sentHashes = new String[MAX_MESSAGES];

    static int sentCount = 0;
    static int disregardedCount = 0;
    static int storedCount = 0;
    static int idHashCount = 0;

    // Track logged-in sender (e.g., login.storedName + " " + login.storedSurname)
    static String currentSender = "Unknown Sender";

    // Parallel arrays specifically aligned with storedMessages[] for Menu Option 4
    static String[] storedRecipients = new String[MAX_MESSAGES];
    static String[] storedIDs = new String[MAX_MESSAGES];
    static String[] storedHashes = new String[MAX_MESSAGES];

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

        currentSender = login.storedName + " " + login.storedSurname;

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
            System.out.println("3) Store Massages");
            System.out.println("4) Quit");
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
                    runStoredMessagesMenu(sc);
                    break;
                case 4:
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
            sc.nextLine();
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

            messageIDs[idHashCount] = messageInstance.getMessageId();
            messageHashes[idHashCount] = messageInstance.getMessageHash();
            idHashCount++;


            int handlingChoice = 0;
            if (sc.hasNextInt()) {
                handlingChoice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
            }

            System.out.println(messageInstance.SentMessage(handlingChoice));


            if (handlingChoice == 1) {
                sentMessages[sentCount] = messageInstance.getMessageText();
                sentRecipients[sentCount] = messageInstance.getRecipientCell();
                sentIDs[sentCount] = messageInstance.getMessageId();
                sentHashes[sentCount] = messageInstance.getMessageHash();
                sentCount++;

                GlobalMessageHistory.add(messageInstance);
                totalMessagesSent++;
                System.out.println(messageInstance.printMessages());
            }
            else if (handlingChoice == 2) {
                disregardedMessages[disregardedCount] = messageInstance.getMessageText();
                disregardedCount++;

                System.out.println("Message discarded by manual input rule triggers.");
                continue;
            }

            else if (handlingChoice == 3) {
                System.out.println(messageInstance.SentMessage(handlingChoice));

                try (FileWriter writer = new FileWriter("messages.json", true)) {
                    writer.write(messageInstance.storeMessageJSON() + "\n");
                    System.out.println("-> Success: Message successfully appended to 'messages.json'.");
                    storedMessages[storedCount] = messageInstance.getMessageText();
                    storedRecipients[storedCount] = messageInstance.getRecipientCell();
                    storedIDs[storedCount] = messageInstance.getMessageId();
                    storedHashes[storedCount] = messageInstance.getMessageHash();
                    storedCount++;
                } catch (IOException e) {
                    System.out.println("-> Error saving message to JSON file: " + e.getMessage());
                }


                GlobalMessageHistory.add(messageInstance);
                totalMessagesSent++;
            }
        }

        returnTotalMessages();
    }

    public static void loadStoredMessagesFromJSON() {
        storedCount = 0;
        File jsonFile = new File("messages.json");

        if (!jsonFile.exists()) {
            return;
        }

        try (Scanner fileReader = new Scanner(jsonFile)) {
            while (fileReader.hasNextLine() && storedCount < MAX_MESSAGES) {
                String line = fileReader.nextLine().trim();

                if (line.startsWith("\"Message\":")) {
                    int firstQuote = line.indexOf("\"", 10);
                    int lastQuote = line.lastIndexOf("\"");

                    if (firstQuote != -1 && lastQuote > firstQuote) {
                        String extractedMsg = line.substring(firstQuote + 1, lastQuote);
                        // Unescape quotes if storeMessageJSON() escaped them
                        extractedMsg = extractedMsg.replace("\\\"", "\"");
                        storedMessages[storedCount] = extractedMsg;
                        storedCount++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading messages.json: " + e.getMessage());
        }
    }

    public static int returnTotalMessages() {
        System.out.println(">>> Cumulative program messages processed history: " + totalMessagesSent + " <<<");
        return totalMessagesSent;
    }

    private static void runStoredMessagesMenu(Scanner sc) {
        int subChoice = 0;
        while (subChoice != 7) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID and display recipient and message");
            System.out.println("4) Search for all messages stored for a particular recipient");
            System.out.println("5) Delete a message using the message hash");
            System.out.println("6) Display a report listing full details of all stored messages");
            System.out.println("7) Return to Main Menu");
            System.out.print("Select an option: ");

            if (sc.hasNextInt()) {
                subChoice = sc.nextInt();
                sc.nextLine();

                switch (subChoice) {
                    case 1:
                        System.out.println(displaySenderAndRecipient());
                        break;
                    case 2:
                        System.out.println(getLongestMessage());
                        break;
                    case 3:
                        System.out.print("Enter Message ID to search: ");
                        String searchId = sc.nextLine().trim();
                        System.out.println(searchByMessageID(searchId));
                        break;
                    case 4:
                        System.out.print("Enter Recipient Cell Number to search: ");
                        String searchRecipient = sc.nextLine().trim();
                        System.out.println(searchByRecipient(searchRecipient));
                        break;
                    case 5:
                        System.out.print("Enter Message Hash to delete: ");
                        String deleteHash = sc.nextLine().trim();
                        System.out.println(deleteByMessageHash(deleteHash));
                        break;
                    case 6:
                        System.out.println(displaySentReport());
                        break;
                    case 7:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    public static String displaySenderAndRecipient() {
        if (storedCount == 0) {
            return "No stored messages available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            sb.append("Sender: ").append(currentSender)
                    .append(" | Recipient: ").append(storedRecipients[i]).append("\n");
        }
        return sb.toString().trim();
    }

    public static String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null && sentMessages[i].length() > longest.length()) {
                longest = sentMessages[i];
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        return longest;
    }

    public static String searchByMessageID(String targetID) {
        for (int i = 0; i < sentCount; i++) {
            if (sentIDs[i] != null && sentIDs[i].equals(targetID)) {
                return "Recipient: " + sentRecipients[i] + "\nMessage: " + sentMessages[i];
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedIDs[i] != null && storedIDs[i].equals(targetID)) {
                return "Recipient: " + storedRecipients[i] + "\nMessage: " + storedMessages[i];
            }
        }
        return "Message ID '" + targetID + "' not found.";
    }

    public static String searchByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            if (sentRecipients[i] != null && sentRecipients[i].equals(targetRecipient)) {
                if (sb.length() > 0) sb.append(", ");
                sb.append("\"").append(sentMessages[i]).append("\"");
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedRecipients[i] != null && storedRecipients[i].equals(targetRecipient)) {
                if (sb.length() > 0) sb.append(", ");
                sb.append("\"").append(storedMessages[i]).append("\"");
            }
        }
        if (sb.length() == 0) {
            return "No messages found for recipient: " + targetRecipient;
        }
        return sb.toString();
    }

    public static String deleteByMessageHash(String targetHash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedHashes[i] != null && storedHashes[i].equalsIgnoreCase(targetHash)) {
                String deletedMsg = storedMessages[i];

                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedIDs[j] = storedIDs[j + 1];
                    storedHashes[j] = storedHashes[j + 1];
                }

                storedMessages[storedCount - 1] = null;
                storedRecipients[storedCount - 1] = null;
                storedIDs[storedCount - 1] = null;
                storedHashes[storedCount - 1] = null;
                storedCount--;

                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Message hash '" + targetHash + "' not found.";
    }

    public static String displaySentReport() {
        if (sentCount == 0) {
            return "No sent messages to display in report.";
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            report.append("Message Hash: ").append(sentHashes[i]).append("\n")
                    .append("Recipient: ").append(sentRecipients[i]).append("\n")
                    .append("Message: ").append(sentMessages[i]).append("\n\n");
        }
        return report.toString().trim();
    }

}



