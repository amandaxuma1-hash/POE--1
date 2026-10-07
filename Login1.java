public class Login1 {

    String storedUsername;
    String storedPassword;
    String storedPhoneNumber;

/*
        if (Storedusername && Storedpassword && Storedphonenumber) {
        System.out.println("Username or password incorrect, please try again.");
    } else {
        System.out.println("Welcome <name>,<lastName> it is great to see you again.");
    }*/

        public boolean checkUserName(String username){
            boolean usernameGood=false;

            boolean hasUnderscore = username.contains("_");
            boolean correctLength = username.length() <= 5;

            if (hasUnderscore && correctLength) {
                System.out.println("Username successfully captured.");
                usernameGood = true;
                storedUsername = username;
            } else {
                System.out.println("Username is not not correctly formatted; please ensure that your username contains at least an underscore and is no more than five character in length.");
            }

            return usernameGood;
        }
        public boolean checkPassword(String password){
            boolean passwordGood = false;

            // Explaining the regex pattern:
            // (?=.*[A-Z])      -> must contain at least uppercase letter
            // (?=.*\d)         -> must contain at least one digit
            // (?=.*[\W_])      -> must contain at least one special character (non-word character or underscore)
            // .{8,}            -> must be at least 8 characters long

            String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

            if (password.matches(passwordPattern)) {
                System.out.println("Password successfully captured");
                passwordGood = true;
                storedPassword = password;
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
            return passwordGood;

        }
        public boolean checkPhoneNumber(String cellphoneNumber) {
            boolean phonenumberGood = false;

            boolean isValidPhone = cellphoneNumber.matches("^\\+\\d{1,3}\\d{1,10}$");

            if (isValidPhone) {
                System.out.println("Cell phone number successfully added.");
                phonenumberGood = true;
                storedPhoneNumber = cellphoneNumber;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }


            return phonenumberGood;
        }
        public boolean loginuser(String username, String password){
            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                return true;
            }else{
                return false;
            }

        }
        public String returnLoginStatus(boolean loginSuccess) {
            if (loginSuccess) {
                return "Welcome " + storedUsername + ",it is great to see you again.";
            }else {
                return "Username or password incorrect, please try again.";
            }
        }


}
