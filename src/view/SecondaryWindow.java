package view;

import javax.swing.*;
import java.awt.event.*;

public class SecondaryWindow {
    protected JFrame frame;

    public void show() {
        frame.pack();
        frame.setVisible (true);
        frame.setResizable(false);
    }

    public boolean isVisible()
    {
        return frame.isVisible();
    }

    public void close()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
