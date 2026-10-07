import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {


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
     System.out.println("You are registered successfully!"+login.storedUsername);

     //=============== Login Phase ===============

    System.out.println("===============Login===============");

    boolean loginPassed = false;

    while (!loginPassed){
        System.out.println("Enter your username to login:");
        String loginUser = sc.nextLine();

        System.out.println("Enter your password to login:");
        String loginPass = sc.nextLine();

        loginPassed = login.loginuser(loginUser, loginPass);

        System.out.println(login.returnLoginStatus(loginPassed));
    }

    sc.close();
}




