import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationButton extends JButton {
    char caption;
    int actionType;
    ActionListener onClick;
    Component leftRelative;
    Component topRelative;

    public OperationButton(int actionType, ActionListener onClick) {
        this.actionType = actionType;
        caption = ConstClass.OB_CAPTIONS[actionType];
        setText(String.valueOf(caption));
        setActionCommand(String.valueOf(actionType));
        addActionListener(onClick);
    }

    public void setRelative(Component leftRelative, Component topRelative){
        this.leftRelative = leftRelative;
        this.topRelative = topRelative;
    }

}
