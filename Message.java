import java.util.Random;

public class Message {

    private String messageId;
    private String recipientCell;
    private String messageText;
    private String messageHash;

    public Message() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        this.messageId = sb.toString();
    }

    public boolean checkMessageID() {
        return this.messageId != null && this.messageId.length() == 10;
    }

    public String checkRecipientCell(String cellNumber) {
        String errorMsg = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the code and try again.";
        if (cellNumber == null) {
            return errorMsg;
        }

        boolean isValidLength = cellNumber.length() == 10 || cellNumber.length() == 12;
        boolean isValidPrefix = cellNumber.startsWith("+27") || cellNumber.startsWith("0");

        if (isValidLength && isValidPrefix) {
            return "Cell phone number successfully captured.";
        } else {
            return errorMsg;
        }
    }


    public String createMessageHash(int numMessagesSent, String text) {
        if (text == null) {
            text = "";
        }

        if (text.length() > 250) {
            int exceededBy = text.length() - 250;
            return "Message exceeds 250 charecters by " + exceededBy + "; please reduce the size.";
        }

        String firstTwoId = this.messageId.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        this.messageHash = firstTwoId + ":" + numMessagesSent + ":" + firstWord + ":" + lastWord;
        return this.messageHash;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Unknown selection state.";
        }
    }

    public String printMessages() {
        return "\n==================================" +
                "\nMessage ID:   " + this.messageId +
                "\nMessage Hash: " + this.messageHash +
                "\nRecipient:    " + this.recipientCell +
                "\nMessage:      " + this.messageText +
                "\n==================================";
    }


    public String storeMessageJSON() {
        String safeId = (this.messageId != null) ? this.messageId : "Auto generated";
        String safeRecipient = (this.recipientCell != null) ? this.recipientCell : "";
        String safeText = (this.messageText != null) ? this.messageText.replace("\"", "\\\"") : "";

        return """
               {
                 "messages sent": {
                   "MessageID": "%s",
                   "Recipient Number": "%s",
                   "Message": "%s"
                 }
               }
               """.formatted(safeId, safeRecipient, safeText);
    }


    public String getMessageId() { return messageId; }
    public String getRecipientCell() { return recipientCell; }
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public String getMessageHash() { return messageHash; }
}
