import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalcWindow extends JFrame {
    ActionListener onClickOperation;
    ActionListener onClickDigit;

    boolean isNeedClear;
    double calcResult = 0.0;
    int lastAction = 0;
    Container contentPane = this.getContentPane();
    SpringLayout layout = new SpringLayout();

    OperationButton[] operationButton = new OperationButton[9];
    DigitButton[] digitButton = new DigitButton[10];
    JLabel resultField;

    public void addResultText(String addString) {
        if (resultField.getText().equals("0")){
            resultField.setText(addString);
        }else {
            resultField.setText(resultField.getText() + addString);
        }
    }

    public void setResultText(double newValue) {
        resultField.setText(DecimalFormat.getNumberInstance().format(newValue));
    }

    public void clearResultText() {
        resultField.setText("0");
    }

    public double getResultText() {
        String curText;
        curText = resultField.getText();
        curText = curText.replaceAll("\\s+", "");
        curText = curText.replaceAll(",", ".");
        return Double.valueOf(curText);
    }

    public void placeButton(Component button, Component left, Component top, boolean isLeft){
        layout.putConstraint(SpringLayout.NORTH, button, ConstClass.SL_PAD, SpringLayout.SOUTH, top);
        if (isLeft) {
            layout.putConstraint(SpringLayout.WEST, button, ConstClass.SL_PAD, SpringLayout.WEST, left);
        }
        else {
            layout.putConstraint(SpringLayout.WEST, button, ConstClass.SL_PAD, SpringLayout.EAST, left);
        }
        button.setPreferredSize(new Dimension((getWidth() - 5 * ConstClass.SL_PAD) / 4, ConstClass.SL_BUTTON_HEIGHT));
        contentPane.add(button);
    }

    public void doAction(int command, boolean showResult){
        double calcCurrent = getResultText();
        switch (command){
            case (ConstClass.AT_CLEAR) : clearResultText(); calcResult = 0.0; break;
            case (ConstClass.AT_PLUS) : calcResult += calcCurrent; break;
            case (ConstClass.AT_MINUS) : calcResult -= calcCurrent; break;
            case (ConstClass.AT_DIVISION) : if (calcCurrent != 0) {calcResult /= calcCurrent;} break;
            case (ConstClass.AT_MULTIPLICATION) : calcResult *= calcCurrent; break;
            case (ConstClass.AT_PERCENT) : setResultText(getResultText() / 100); break;
            case (ConstClass.AT_SIGN) : setResultText(-1 * getResultText()); break;
            case (ConstClass.AT_CALC) : doAction(lastAction, true); calcResult = 0.0; break;
        }
        if (showResult){
            showResult = false;
            setResultText(calcResult);
        }
    }

    public CalcWindow() {
        lastAction = ConstClass.AT_CALC;
        onClickOperation = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int currentCommand = Integer.valueOf(actionEvent.getActionCommand());
                if (lastAction != currentCommand) {
                    isNeedClear = true;
                    if (calcResult == 0) {
                        calcResult = getResultText();
                    } else {
                        doAction(currentCommand, false);
                    }
                    lastAction = currentCommand;
                }
            }
        };

        onClickDigit = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (isNeedClear){
                    clearResultText();
                    isNeedClear = false;
                }
                addResultText(actionEvent.getActionCommand());
            }
        };

        // create operation buttons
        for (int i = 0; i < ConstClass.OB_COUNT; i++){
            operationButton[i] = new OperationButton(i, onClickOperation);
        }

        // create digit buttons
        for (int i = 0; i < 10; i++){
            digitButton[i] = new DigitButton(i, onClickDigit);
        }

        // create result text window
        resultField = new JLabel();
        resultField.setOpaque(true);
        resultField.setBackground(Color.DARK_GRAY);
        resultField.setForeground(Color.WHITE);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setVerticalAlignment(JTextField.CENTER);
        resultField.setFont(new Font("Serif", Font.PLAIN, 40));

        setResultText(calcResult);

        setTitle("Easy calculator");
        setBounds(0, 0, 500, 330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(layout);

        // positioning
        layout.putConstraint(SpringLayout.NORTH, resultField, ConstClass.SL_PAD, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, resultField, ConstClass.SL_PAD, SpringLayout.WEST, contentPane);
        resultField.setPreferredSize(new Dimension(getWidth() - 2 * ConstClass.SL_PAD, 50));
        contentPane.add(resultField);

        placeButton(operationButton[ConstClass.AT_CLEAR], contentPane, resultField, true);
        placeButton(operationButton[ConstClass.AT_SIGN], operationButton[ConstClass.AT_CLEAR], resultField, false);
        placeButton(operationButton[ConstClass.AT_PERCENT], operationButton[ConstClass.AT_SIGN], resultField, false);
        placeButton(operationButton[ConstClass.AT_DIVISION], operationButton[ConstClass.AT_PERCENT], resultField, false);
        placeButton(digitButton[7], contentPane, operationButton[ConstClass.AT_CLEAR], true);
        placeButton(digitButton[8], digitButton[7], operationButton[ConstClass.AT_SIGN], false);
        placeButton(digitButton[9], digitButton[8], operationButton[ConstClass.AT_PERCENT], false);
        placeButton(operationButton[ConstClass.AT_MULTIPLICATION], digitButton[9], operationButton[ConstClass.AT_DIVISION], false);
        placeButton(digitButton[4], contentPane, digitButton[7], true);
        placeButton(digitButton[5], digitButton[4], digitButton[8], false);
        placeButton(digitButton[6], digitButton[5], digitButton[9], false);
        placeButton(operationButton[ConstClass.AT_MINUS], digitButton[6], operationButton[ConstClass.AT_MULTIPLICATION], false);
        placeButton(digitButton[1], contentPane, digitButton[4], true);
        placeButton(digitButton[2], digitButton[1], digitButton[5], false);
        placeButton(digitButton[3], digitButton[2], digitButton[6], false);
        placeButton(operationButton[ConstClass.AT_PLUS], digitButton[3], operationButton[ConstClass.AT_MINUS], false);
        placeButton(digitButton[0], contentPane, digitButton[1], true);
        digitButton[0].setPreferredSize(new Dimension((getWidth() - 4 * ConstClass.SL_PAD) / 2, ConstClass.SL_BUTTON_HEIGHT));
        placeButton(operationButton[ConstClass.AT_DOT], digitButton[0], digitButton[3], false);
        placeButton(operationButton[ConstClass.AT_CALC], operationButton[ConstClass.AT_DOT], operationButton[ConstClass.AT_PLUS], false);

        setVisible(true);
    }
}
