import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Messagetest {

    private Message messageInstance;

    @BeforeEach
    public void setUp() {
        messageInstance = new Message();
    }

    @Test
    public void testCheckMessageID_Valid() {
        assertTrue(messageInstance.checkMessageID(),
                "Message ID should be non-null and exactly 10 characters long.");
    }

    @Test
    public void testCheckRecipientCell_ValidInternational() {
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, messageInstance.checkRecipientCell("+27771869300"));
    }

    @Test
    public void testCheckRecipientCell_ValidLocalPrefix() {
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, messageInstance.checkRecipientCell("0857597588"));
    }

    @Test
    public void testCheckRecipientCell_InvalidNullOrEmpty() {
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the code and try again.";
        assertEquals(expected, messageInstance.checkRecipientCell(null));
        assertEquals(expected, messageInstance.checkRecipientCell(""));
    }

    @Test
    public void testCheckRecipientCell_InvalidLength() {
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the code and try again.";
        assertEquals(expected, messageInstance.checkRecipientCell("+2777186938025"));
    }

    @Test
    public void testCheckRecipientCell_InvalidPrefix() {
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the code and try again.";
        assertEquals(expected, messageInstance.checkRecipientCell("1234567890"));
    }

    @Test
    public void testCreateMessageHash_ExceedsLength() {
        // Creating a string that is exactly 260 characters long (10 characters over the 250 limit)
        String longText = "a".repeat(260);

        // Match the exact required failure format with the specific assignment typo ("charecters")
        String expected = "Message exceeds 250 charecters by 10; please reduce the size.";
        assertEquals(expected, messageInstance.createMessageHash(1, longText));
    }


    @Test
    public void testCreateMessageHash_EmptyOrNullText() {
        assertEquals("00:0:EMPTY", messageInstance.createMessageHash(0, "   "));
        assertEquals("00:0:EMPTY", messageInstance.createMessageHash(2, null));
    }

    @Test
    public void testCreateMessageHash_ValidFormat() {
        String testText = "Hi Mike, can you join us for dinner tonight?";
        int sentCount = 1;

        String generatedHash = messageInstance.createMessageHash(sentCount, testText);

        String expectedPrefix = messageInstance.getMessageId().substring(0, 2);
        String expectedHashPattern = expectedPrefix + ":" + sentCount + ":HI:TONIGHT?";

        assertEquals(expectedHashPattern, generatedHash,
                "Hash output formatting pattern mismatch.");
    }
    
    @Test
    public void testSendMessage_Actions() {
        // Case 1: Send Message
        assertEquals("Message successfully sent", messageInstance.SentMessage(1));

        // Case 2: Disregard Message
        assertEquals("Press 0 to delete the message", messageInstance.SentMessage(2));

        // Case 3: Store Message choice
        assertEquals("Message successfully stored", messageInstance.SentMessage(3));

        // Default: Unknown out-of-bounds
        assertEquals("Unknown selection state.", messageInstance.SentMessage(99));
    }
}
