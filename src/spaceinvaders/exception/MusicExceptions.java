package spaceinvaders.exception;

import javax.swing.JOptionPane;

public class MusicExceptions {

    public static void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Music Error", JOptionPane.ERROR_MESSAGE);
    }
}
