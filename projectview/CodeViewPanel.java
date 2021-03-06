package projectview;

import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import project.Loader;
import project.MachineModel;
import project.Memory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel implements Observer {

    private MachineModel model;
    private JScrollPane scroller;
    private JTextField[] codeHex = new JTextField[Memory.CODE_MAX/2];
    private JTextField[] codeDecimal = new JTextField[Memory.CODE_MAX/2];
    private int previousColor= -1;

    public CodeViewPanel(ViewMediator gui, MachineModel mdl){
        model = mdl;
        gui.addObserver(this);
    }
    JComponent createCodeDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        Border border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Code Memory View [" + 0 + "-" + Memory.CODE_MAX/2 + "]",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        panel.setBorder(border);
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        JPanel numPanel = new JPanel();
        numPanel.setLayout(new GridLayout(0,1));
        JPanel decimalPanel = new JPanel();
        decimalPanel.setLayout(new GridLayout(0,1));
        JPanel hexPanel = new JPanel();
        hexPanel.setLayout(new GridLayout(0,1));
        innerPanel.add(numPanel, BorderLayout.LINE_START);
        innerPanel.add(decimalPanel, BorderLayout.CENTER);
        innerPanel.add(hexPanel, BorderLayout.LINE_END);
        for(int i = 0; i < Memory.CODE_MAX/2; i++){
            codeHex[i] = new JTextField(10);
            codeDecimal[i] = new JTextField(10);
            numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
            decimalPanel.add(codeDecimal[i-0]); // might be problems with lower
            hexPanel.add(codeHex[i-0]);

        }
        scroller = new JScrollPane(innerPanel);
        panel.add(scroller);
        return panel;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
       // System.out.println("entering update of code view");
        //System.out.println(arg1);
        if(arg1 != null && arg1.equals("Load Code")) {
            //System.out.println("entering load code of code view");
            int offset = model.getCurrentJob().getStartcodeIndex();
            System.out.println("CODE SIZE " +  model.getCurrentJob().getCodeSize());
            for(int i = offset;
                i < offset + model.getCurrentJob().getCodeSize(); i++) {
                System.out.println("CODE SIZE " +  model.getCurrentJob().getCodeSize());
                codeHex[i].setText(model.getHex(i));
                codeDecimal[i].setText(model.getDecimal(i));
            }
            previousColor = model.getInstructionPointer();
            codeHex[previousColor].setBackground(Color.YELLOW);
            codeDecimal[previousColor].setBackground(Color.YELLOW);
        } else if(arg1 != null && arg1.equals("Clear")) {
            int offset = model.getCurrentJob().getStartcodeIndex();
            for(int i = offset;
                i < offset + model.getCurrentJob().getCodeSize(); i++) {
                if(model != null) {
                    codeHex[i].setText("");
                    codeDecimal[i].setText("");
                }
                else {
                    codeHex[i].setText(model.getHex(i));
                    codeDecimal[i].setText(model.getDecimal(i));
                }
            }
            if(previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
                codeHex[previousColor].setBackground(Color.WHITE);
                codeDecimal[previousColor].setBackground(Color.WHITE);
            }
            previousColor = -1;
        }
        if(this.previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
            codeHex[previousColor].setBackground(Color.WHITE);
            codeDecimal[previousColor].setBackground(Color.WHITE);
        }
        previousColor = model.getInstructionPointer();
        if(this.previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
            codeHex[previousColor].setBackground(Color.YELLOW);
            codeDecimal[previousColor].setBackground(Color.YELLOW);
        }

        if(scroller != null && model != null && model!= null) {
            JScrollBar bar= scroller.getVerticalScrollBar();
            int pc = model.getInstructionPointer();
            //CHANGE
            if(pc > 0 && pc < Memory.CODE_MAX && codeHex[pc] != null) {
                Rectangle bounds = codeHex[pc].getBounds();
                bar.setValue(Math.max(0, bounds.y - 15*bounds.height));
            }
        }
    }

    public static void main(String[] args) {
        ViewMediator view = new ViewMediator();
        MachineModel model = new MachineModel();
        CodeViewPanel panel = new CodeViewPanel(view, model);
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLocationRelativeTo(null);
        frame.add(panel.createCodeDisplay());
        frame.setVisible(true);
        String str = Loader.load(model, new File("large.pexe"), 0, 0);
        model.getCurrentJob().setCodeSize(Integer.parseInt(str));
        panel.update(view, "Load Code");
    }

}
