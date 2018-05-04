package projectview;

import projectview.ViewMediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MenuBarBuilder implements Observer {
    private JMenuItem assemble = new JMenuItem("Assemble Source...");
    private JMenuItem load = new JMenuItem("Load Program...");
    private JMenuItem exit = new JMenuItem("Exit");
    private JMenuItem go = new JMenuItem("Go");
    private JMenuItem job0 = new JMenuItem("Job 0");
    private JMenuItem job1 = new JMenuItem("Job 1");
    private ViewMediator view;

    public MenuBarBuilder(ViewMediator gui) {
        view = gui; gui.addObserver(this);
    }
    JMenu createFileMenu(){
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem assemble = new JMenuItem();
        assemble.setMnemonic(KeyEvent.VK_A);
        assemble.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        assemble.addActionListener(e -> view.assembleFile());
        menu.add(assemble);
        load.setMnemonic(KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(e -> view.loadFile());
        menu.add(load);
        menu.addSeparator();
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.addActionListener(e -> view.exit());
        menu.add(exit);

        return menu;
    }
    JMenu createExecuteMenu(){
        JMenu menu = new JMenu("Execute");
        JMenuItem execute = new JMenuItem();
        menu.add(execute);
        execute.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        execute.addActionListener(e -> view.execute());

        return menu;
    }
    JMenu createJobsMenu(){
        JMenu menu = new JMenu("Change Job");
        JMenuItem setJob1 = new JMenuItem();
        menu.add(setJob1);
        setJob1.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_0, ActionEvent.CTRL_MASK));
        setJob1.addActionListener(e -> view.setJob(0));
        JMenuItem setJob2 = new JMenuItem();
        menu.add(setJob2);
        setJob2.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        setJob2.addActionListener(e -> view.setJob(1));

        return menu;
    }
    public void update(Observable arg0, Object arg1){
        assemble.setEnabled(view.getCurrentState().getAssembleFileActive());
    }
}