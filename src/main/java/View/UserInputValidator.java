package View;

import javafx.scene.control.TextInputControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputValidator {
    final String regex = "(([A-Z])|([a-z]))+([1-9])*|[\\&\\.\\,  ]+";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    int minCharCount = 3;

    boolean validateInputText(TextInputControl textInput){
        Matcher matcher = pattern.matcher(textInput.getText());
        if(matcher.find() && textInput.getText().length() >= minCharCount){
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
}
