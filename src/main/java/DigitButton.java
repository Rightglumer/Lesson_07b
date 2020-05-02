import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DigitButton extends JButton {

    int buttonType;
    Component leftRelative;
    Component topRelative;

    public DigitButton(int buttonType, ActionListener onClick) {
        this.buttonType = buttonType;
        setText(String.valueOf(buttonType));
        setActionCommand(String.valueOf(buttonType));
        addActionListener(onClick);
    }

    public void setRelative(Component leftRelative, Component topRelative){
        this.leftRelative = leftRelative;
        this.topRelative = topRelative;
    }
}
