import project.MachineModel; // and other swing components
import projectview.ViewMediator;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ProcessorViewPanel implements Observer {
    private MachineModel model;
    private JTextField acc = new JTextField();

    public ProcessorViewPanel(ViewMediator gui, MachineModel model) {
        this.model = model;
        gui.addObserver(this);
    }

    public JComponent createProcessorDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        panel.add(new JLabel("Accumulator: ", JLabel.RIGHT));
        panel.add(acc);
        return panel;
    }

    public JComponent createInstructionPointerDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        panel.add(new JLabel("Instruction Pointer: ", JLabel.RIGHT));
        panel.add(acc);
        return panel;
    }

    public JComponent createMemoryBaseDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        panel.add(new JLabel("MemoryBase: ", JLabel.RIGHT));
        panel.add(acc);
        return panel;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if(model != null) {
            acc.setText("" + model.getAccumulator());
        }
    }

    public static void main(String[] args) {
        ViewMediator view = new ViewMediator();
        MachineModel model = new MachineModel();
        ProcessorViewPanel panel =
                new ProcessorViewPanel(view, model);
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 60);
        frame.setLocationRelativeTo(null);
        frame.add(panel.createProcessorDisplay());
        frame.add(panel.createInstructionPointerDisplay());
        frame.add(panel.createProcessorDisplay());
        frame.setVisible(true);
    }
}