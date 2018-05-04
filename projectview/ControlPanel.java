package projectview;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ControlPanel implements Observer {
    private JMenuItem stepButton = new JMenuItem("Step");
    private JMenuItem clearButton = new JMenuItem("Clear");
    private JMenuItem runButton = new JMenuItem("Run/pause");
    private JMenuItem reloadButton = new JMenuItem("Reload");
    private ViewMediator view;

    public ControlPanel(ViewMediator gui) {
        view = gui;
        gui.addObserver(this);
    }

    public JComponent createControlDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0));
        stepButton.addActionListener(e -> view.step());
        clearButton.addActionListener(e -> view.clearJob());
        runButton.addActionListener(e -> view.toggleAutoStep());
        reloadButton.addActionListener(e -> view.reload());
        panel.add(stepButton);
        panel.add(clearButton);
        panel.add(runButton);
        panel.add(reloadButton);
        JSlider slider = new JSlider(5, 1000);
        slider.addChangeListener(e -> view.setPeriod(slider.getValue()));
        panel.add(slider);
        return panel;
    }
    @Override
    public void update(Observable arg0, Object arg1) {
        runButton.setEnabled(view.getCurrentState().getRunPauseActive());
        stepButton.setEnabled(view.getCurrentState().getStepActive());
        clearButton.setEnabled(view.getCurrentState().getClearActive());
        reloadButton.setEnabled(view.getCurrentState().getReloadActive());
    }
}