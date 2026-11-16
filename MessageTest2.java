import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest2 {

    private Message msg1;
    private Message msg2;
    private Message msg3;
    private Message msg4;
    private Message msg5;

    @BeforeEach
    public void setUp() {
        Main.sentMessages = new String[Main.MAX_MESSAGES];
        Main.sentRecipients = new String[Main.MAX_MESSAGES];
        Main.sentIDs = new String[Main.MAX_MESSAGES];
        Main.sentHashes = new String[Main.MAX_MESSAGES];

        Main.disregardedMessages = new String[Main.MAX_MESSAGES];

        Main.storedMessages = new String[Main.MAX_MESSAGES];
        Main.storedRecipients = new String[Main.MAX_MESSAGES];
        Main.storedIDs = new String[Main.MAX_MESSAGES];
        Main.storedHashes = new String[Main.MAX_MESSAGES];

        Main.messageHashes = new String[Main.MAX_MESSAGES];
        Main.messageIDs = new String[Main.MAX_MESSAGES];

        Main.sentCount = 0;
        Main.disregardedCount = 0;
        Main.storedCount = 0;
        Main.idHashCount = 0;
        Main.currentSender = "Amanda Xuma";

        msg1 = new Message();
        msg1.setRecipientCell("+27834557896");
        msg1.setMessageText("Did you get the cake?");
        msg1.createMessageHash(1, msg1.getMessageText());
        routeTestMessage(msg1, "Sent");

        msg2 = new Message();
        msg2.setRecipientCell("+27838884567");
        msg2.setMessageText("Where are you? You are late! I have asked you to be on time.");
        msg2.createMessageHash(2, msg2.getMessageText());
        routeTestMessage(msg2, "Stored");

        msg3 = new Message();
        msg3.setRecipientCell("+27834484567");
        msg3.setMessageText("Yohoooo, I am at your gate.");
        msg3.createMessageHash(3, msg3.getMessageText());
        routeTestMessage(msg3, "Disregard");

        msg4 = new Message();
        msg4.setRecipientCell("0838884567");
        msg4.setMessageText("It is dinner time !");
        msg4.createMessageHash(4, msg4.getMessageText());
        routeTestMessage(msg4, "Sent");

        msg5 = new Message();
        msg5.setRecipientCell("+27838884567");
        msg5.setMessageText("Ok, I am leaving without you.");
        msg5.createMessageHash(5, msg5.getMessageText());
        routeTestMessage(msg5, "Stored");
    }

    private void routeTestMessage(Message msg, String flag) {
        Main.messageIDs[Main.idHashCount] = msg.getMessageId();
        Main.messageHashes[Main.idHashCount] = msg.getMessageHash();
        Main.idHashCount++;

        if (flag.equalsIgnoreCase("Sent")) {
            Main.sentMessages[Main.sentCount] = msg.getMessageText();
            Main.sentRecipients[Main.sentCount] = msg.getRecipientCell();
            Main.sentIDs[Main.sentCount] = msg.getMessageId();
            Main.sentHashes[Main.sentCount] = msg.getMessageHash();
            Main.sentCount++;
        } else if (flag.equalsIgnoreCase("Disregard")) {
            Main.disregardedMessages[Main.disregardedCount] = msg.getMessageText();
            Main.disregardedCount++;
        } else if (flag.equalsIgnoreCase("Stored")) {
            Main.storedMessages[Main.storedCount] = msg.getMessageText();
            Main.storedRecipients[Main.storedCount] = msg.getRecipientCell();
            Main.storedIDs[Main.storedCount] = msg.getMessageId();
            Main.storedHashes[Main.storedCount] = msg.getMessageHash();
            Main.storedCount++;
        }
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        String[] expectedSent = {"Did you get the cake?", "It is dinner time !"};
        String[] actualSent = {Main.sentMessages[0], Main.sentMessages[1]};
        assertArrayEquals(expectedSent, actualSent);
    }

    @Test
    public void testDisplayLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, Main.getLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        String result = Main.searchByMessageID(msg4.getMessageId());
        String expected = "Recipient: 0838884567\nMessage: It is dinner time !";
        assertEquals(expected, result);
    }

    @Test
    public void testSearchMessagesForRecipient() {
        String expected = "\"Where are you? You are late! I have asked you to be on time.\", \"Ok, I am leaving without you.\"";
        assertEquals(expected, Main.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        String expected = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, Main.deleteByMessageHash(msg2.getMessageHash()));
    }
    
    @Test
    public void testDisplayReport() {
        String expected = "Message Hash: " + msg1.getMessageHash() + "\n" +
                "Recipient: +27834557896\n" +
                "Message: Did you get the cake?\n\n" +
                "Message Hash: " + msg4.getMessageHash() + "\n" +
                "Recipient: 0838884567\n" +
                "Message: It is dinner time !";
        assertEquals(expected, Main.displaySentReport());
    }
}