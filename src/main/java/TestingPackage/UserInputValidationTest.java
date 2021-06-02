package TestingPackage;

import View.UserInputValidator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.junit.Assert;
import org.junit.Test;

public class UserInputValidationTest {

    UserInputValidator userInputValidator = new UserInputValidator();

    @Test
    public void testNumInput(){
        // Arrange
        String numInput = "1234";
        // Act
        boolean validation = userInputValidator.validateText(numInput);
        // Assert
        Assert.assertEquals(false,validation);
    }

    @Test
    public void testAllowedSpecialChars(){
        // Arrange
        String allowedChars = "@@@@####__";
        // Act
        boolean validation = userInputValidator.validateText(allowedChars);
        // Assert
        Assert.assertEquals(true,validation);
    }

    @Test
    public void testUnAllowedGermanChars(){
        // Arrange
        String unAllowedChars = "ääöäöäööäü";
        // Act
        boolean validation = userInputValidator.validateText(unAllowedChars);
        // Assert
        Assert.assertEquals(false,validation);
    }

    @Test
    public void testUnAllowedSpecialChars() {
        // Arrange
        String unAllowedChars = "[]^^?$§\"!|<;>%";
        // Act
        boolean validation = userInputValidator.validateText(unAllowedChars);
        // Assert
        Assert.assertEquals(false,validation);
    }
}
