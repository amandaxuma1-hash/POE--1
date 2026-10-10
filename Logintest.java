import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class Logintest {

       private Login1 loginService;

       @BeforeEach
       void setUp() {
              loginService = new Login1();
       }


       @Test
       @DisplayName("Username - Valid format with underscore and length <= 5")
       void testCheckUserName_Valid() {
              boolean result = loginService.checkUserName("kyl_1");
              assertTrue(result, "Username 'kyl_1' should be valid.");
       }

       @Test
       @DisplayName("Username - Invalid: Exceeds 5 characters")
       void testCheckUserName_TooLong() {
              boolean result = loginService.checkUserName("kyle!!!!!!");
              assertFalse(result, "Username 'Kyle!!!!!!' should be invalid if it is more than 5 characters long.");
       }

       @Test
       @DisplayName("Username - Invalid: Missing an underscore")
       void testCheckUserName_NoUnderscore() {
              boolean result = loginService.checkUserName("kyle");
              assertFalse(result, "Username Username should be invalid if it does not contain an underscore.");
       }

       @Test
       @DisplayName("Password - Meets complexity requirements")
       void testCheckPassword_Valid() {
              boolean result = loginService.checkPassword("Ch&&sec2ke99!");
              assertTrue(result, "Password should meet all complexity rules.");
       }

       @Test
       @DisplayName("Password - Does not meet complexity requirements")
       void testCheckPassword_Invalid() {
              boolean result = loginService.checkPassword("password");
              assertFalse(result, "Password should fail due to missing uppercase, number, or special characters.");
       }

       @Test
       @DisplayName("Cell Phone - Correctly formatted with international code")
       void testCheckPhoneNumber_Valid() {
              boolean result = loginService.checkPhoneNumber("+27838968976");
              assertTrue(result, "International cell phone number should be accepted.");
       }

       @Test
       @DisplayName("Cell Phone - Incorrectly formatted local number")
       void testCheckPhoneNumber_Invalid() {
              boolean result = loginService.checkPhoneNumber("08966553");
              assertFalse(result, "Phone number missing international prefix should be rejected.");
       }

       @Test
       @DisplayName("Login - Successful authentication and welcome massage response")
       void testLoginUser_SuccessFlow() {
              loginService.checkUserName("kyl_1");
              loginService.checkPassword("Ch&&sec@ke99!");

              boolean loginSuccess = loginService.loginUser("kyl_1", "Ch&&sec@ke99!");
              assertTrue(loginSuccess, "Login should true for matching credentials.");

              String expectedMessage = "Welcome nullnull,it is great to see you again.";
              assertEquals(expectedMessage, loginService.returnLoginStatus(loginSuccess));


       }

       @Test
       @DisplayName("Login - Failed authentication due to mismatched credentials")
       void testLoginUser_FailureFlow() {
              loginService.checkUserName("kyl_1");
              loginService.checkPassword("Ch&&sec@ke99!");

              boolean loginSuccess = loginService.loginUser("kyl_1", "wrongPassword123");
              assertFalse(loginSuccess, "Login should false for incorrect credentials.");

              String expectedMessage = "Username or password incorrect, please try again.";
              assertEquals(expectedMessage, loginService.returnLoginStatus(loginSuccess));


       }


}
