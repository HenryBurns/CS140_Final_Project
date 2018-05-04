package projectview;

import java.awt.*;
import java.util.Observable;

import javax.swing.*;

import project.MachineModel;
import project.Memory;

public class ViewMediator extends Observable {

	private MachineModel model;
	private CodeViewPanel codeViewPanel;
	private MemoryViewPanel memoryViewPanel1;
	private MemoryViewPanel memoryViewPanel2;
	private MemoryViewPanel memoryViewPanel3;
	//private ControlPanel controlPanel;
	//private ProcessorViewPanel processorPanel;
	//private MenuBarBuilder menuBuilder;
	private JFrame frame;
	private FilesManager filesManager;
	private Animator animator;
	
	public JFrame getFrame() {
		return frame;
	}

	public MachineModel getModel() {
		return model;
	}

	public void setModel(MachineModel model) {
		this.model = model;
	}

	public void step() { } 

	public void clearJob (){
	}

	void makeReady(String s){

	}

	private void createAndShowGUI(){
		animator = new Animator(this);
		filesManager = new FilesManager(this);
		filesManager.initialize();
		codeViewPanel = new CodeViewPanel(this,model);
		memoryViewPanel1 = new MemoryViewPanel(this, model, 0, 240);
		memoryViewPanel2 = new MemoryViewPanel(this, model, 240, Memory.DATA_SIZE/2);
		memoryViewPanel3 = new MemoryViewPanel(this, model, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
		//controlPanel = new ControlPanel(this);
		//processorPanel = new ProcessorPanel(this, model);
		//menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Simulator");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		frame.setSize(1200,600);
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1,3));
		frame.add(codeViewPanel.createCodeDisplay(),BorderLayout.LINE_START);
		center.add(memoryViewPanel1.createMemoryDisplay());
		center.add(memoryViewPanel2.createMemoryDisplay());
		center.add(memoryViewPanel3.createMemoryDisplay());
		frame.add(center);
		//return HERE for the other GUI components.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// return HERE for other setup details
		frame.setVisible(true);
	}
	// CORRECTED LATER IN THE EVENING
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ViewMediator mediator = new ViewMediator();
				MachineModel model = new MachineModel(
						//true,
						//() -> mediator.setCurrentState(States.PROGRAM_HALTED)
				);
				mediator.setModel(model);
				mediator.createAndShowGUI();
			}
		});
	}

}
