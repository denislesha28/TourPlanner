package View;

import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputValidator {
    final String regex = "(([A-Z])|([a-z])|([@#]))+([1-9])*|[\\&\\.\\,  ]+";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    int minCharCount = 3;

    public boolean validateInputText(TextInputControl textInput){
        if(validateText(textInput.getText())){
            textInput.setStyle("");
            textInput.setPromptText(" ");
            return true;
        }
        textInput.setStyle("-fx-border-color: #e74c3c ; -fx-border-width: 2px ; -fx-border-radius: 2px;  ");
        String errorText = textInput.getText()+" ----- not enough characters, name is too short or not allowed characters";
        textInput.setText("");
        textInput.setPromptText(errorText);
        return false;
    }

    public boolean validateText(String text){
        Matcher matcher = pattern.matcher(text);
        if(matcher.find() && text.length() >= minCharCount){
            return true;
        }
        return false;
    }
}
