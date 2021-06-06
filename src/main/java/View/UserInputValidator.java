package View;

import Datatypes.InputTypes;
import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputValidator {
    final String necessaryInput = "([$^§?üäößÜÄÖ;:|])+";
    final String unNecessaryInput = "([/^§üäößÜÄÖ|])+";
    final String numberInput = "\\p{Lower}|\\p{Upper}|(\\!\\\"#$%&'()*+,-./:;<=>?@\\[\\]^_`\\{|}~)";
    final Pattern necessaryPattern = Pattern.compile(necessaryInput, Pattern.MULTILINE);
    final Pattern unNecessaryPattern = Pattern.compile(unNecessaryInput, Pattern.MULTILINE);
    final Pattern numberPattern = Pattern.compile(numberInput, Pattern.MULTILINE);


    public boolean validateInputText(TextInputControl textInput, InputTypes inputType){
        if(inputType==InputTypes.MUST){
            if(validateNecessaryText(textInput.getText())){
                clearTextStyle(textInput);
                return true;
            }
            setTextErrorStyle(textInput);
            return false;
        }
        else if(inputType==InputTypes.OPTIONAL){
            if(validateUnNecessaryText(textInput.getText())){
                clearTextStyle(textInput);
                return true;
            }
            setTextErrorStyle(textInput);
            return false;
        }
        else if(inputType==InputTypes.NUMBER){
            if(validateNumberText(textInput.getText())){
                clearTextStyle(textInput);
                return true;
            }
            setTextErrorStyle(textInput);
            return false;
        }
        return true;
    }


    public boolean validateNecessaryText(String text){
        if (text==null){
            return false;
        }
        Matcher matcher = necessaryPattern.matcher(text);
        if(matcher.find()){
            return false;
        }
        return true;
    }

    public boolean validateUnNecessaryText(String text){
        if (text==null){
            text=" ";
        }
        Matcher matcher = unNecessaryPattern.matcher(text);
        if(matcher.find()){
            return false;
        }
        return true;
    }

    public boolean validateNumberText(String text){
        if (text==null){
            text="0";
        }
        Matcher matcher = numberPattern.matcher(text);
        if(matcher.find()){
            return false;
        }
        return true;
    }

    private void setTextErrorStyle(TextInputControl textInput){
        textInput.setStyle("-fx-border-color: #e74c3c ; -fx-border-width: 2px ; -fx-border-radius: 2px;  ");
        String errorText = textInput.getText()+" ----- not enough characters, text is too short or not allowed characters";
        textInput.setText("");
        textInput.setPromptText(errorText);
    }

    private void clearTextStyle(TextInputControl textInput){
        textInput.setStyle("");
        textInput.setPromptText(" ");
    }
}
