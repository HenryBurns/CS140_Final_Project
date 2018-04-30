package projectview;

import java.util.Observable;

import javax.swing.JFrame;

import project.MachineModel;

public class ViewMediator extends Observable {

	private MachineModel model;
	
	private JFrame frame;
	
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
	
}
