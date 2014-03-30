package presentation;

public class InputValidator {

    public static boolean intInput() {

        try {


            return true;
        } catch (NumberFormatException ne) {
            return false;
        }
    }



}
