package org.example;


import javafx.scene.control.TextField;

public class FormValidator {
    public static boolean validateIfEmpty(TextField textField){
        return textField.getText().trim().length()>1;
    }

    public static boolean validateLength(TextField textField, int len){
        return textField.getText().length() == len;
    }

    public static boolean validateDouble(TextField textField){
        try {
            Double.parseDouble(textField.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateInteger(TextField textField){
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean validateLessLength(TextField textField, int len){
        return textField.getText().length() <= len;
    }
}
