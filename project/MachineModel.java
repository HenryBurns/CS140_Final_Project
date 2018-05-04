package project;

import projectview.States;

import java.util.Map;
import java.util.TreeMap;
import projectview.States;

public class MachineModel {

	public final Map<Integer, Instruction> INSTRUCTIONS = new TreeMap<>();
	private CPU cpu = new CPU();
	private Memory memory = new Memory();
	private HaltCallback callback;
	private boolean withGUI;
	
	private Job[] jobs = new Job[2]; //New from pt 2
	private Job currentJob;//new

	public MachineModel() {
		this(false, null);
	}

	public MachineModel(boolean usingGUI, HaltCallback stopthing) {
		withGUI = usingGUI;
		callback = stopthing;
		
		for(int i =0; i<jobs.length;i++){
			jobs[i] = new Job();
		}
			
		currentJob = jobs[0];
		
		jobs[0].setStartcodeIndex(0);
		jobs[0].setStartmemoryIndex(0);
		
		jobs[1].setStartcodeIndex(Memory.CODE_MAX/4);
		jobs[1].setStartmemoryIndex(Memory.DATA_SIZE/2);
		
		for(int i =0; i<jobs.length;i++){
			jobs[i].setCurrentState(States.NOTHING_LOADED);
		}
		
		 //INSTRUCTION_MAP entry for "NOP"
        INSTRUCTIONS.put(0x0, arg -> {
            cpu.incrementIP(1);
        });

		 //INSTRUCTION_MAP entry for "LODI"
       INSTRUCTIONS.put(0x1, arg -> {
    	   cpu.accumulator = arg;
           cpu.incrementIP(1);
       });

		 //INSTRUCTION_MAP entry for "LOD"
      INSTRUCTIONS.put(0x2, arg -> {
    	  cpu.accumulator = memory.getData(cpu.memoryBase + arg);
          cpu.incrementIP(1);
      });

		 //INSTRUCTION_MAP entry for "LODN"
     INSTRUCTIONS.put(0x3, arg -> {
     		cpu.accumulator = memory.getData(cpu.memoryBase + memory.getData(cpu.memoryBase + arg));
         cpu.incrementIP(1);
     });
     
		 //INSTRUCTION_MAP entry for "STO"
     INSTRUCTIONS.put(0x4, arg -> {
     		memory.setData(cpu.memoryBase + arg, cpu.accumulator);
         cpu.incrementIP(1);
     });
	 
     	//INSTRUCTION_MAP entry for "STON"
     INSTRUCTIONS.put(0x5, arg -> {
     	memory.setData(cpu.memoryBase + memory.getData(cpu.memoryBase +arg), cpu.accumulator);
         cpu.incrementIP(1);
     });
    
    	 //INSTRUCTION_MAP entry for "JMPR"
     INSTRUCTIONS.put(0x6, arg -> {
     		cpu.incrementIP(arg);
     });
    
    
    	 //INSTRUCTION_MAP entry for "JUMP" 
     INSTRUCTIONS.put(0x7, arg -> {
  		cpu.incrementIP(memory.getData(cpu.memoryBase + arg));
     });
    
    	 //INSTRUCTION_MAP entry for "JMPI"
     INSTRUCTIONS.put(0x8, arg -> {
    	 cpu.instructionPointer = arg;
     });
     
     	//INSTRUCTION_MAP entry for "JMPZR"
     INSTRUCTIONS.put(0x9, arg -> {
    	 if(cpu.accumulator == 0)
      		cpu.incrementIP(arg);
    	 else
       		cpu.incrementIP(1);
     });
     
     	//INSTRUCTION_MAP entry for "JMPZ"
     INSTRUCTIONS.put(0xA, arg -> {
    	 if(cpu.accumulator == 0)
    	  	cpu.incrementIP(memory.getData(cpu.memoryBase + arg));
    	 else
       		cpu.incrementIP(1);
     });
     
     	//INSTRUCTION_MAP entry for "JMPZI"
     INSTRUCTIONS.put(0xB, arg -> {
    	 if(cpu.accumulator == 0)
        	 cpu.instructionPointer = arg;
    	 else
       		cpu.incrementIP(1);
     });
		 //INSTRUCTION_MAP entry for "ADDI"
        INSTRUCTIONS.put(0xC, arg -> {
            cpu.accumulator += arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADD"
        INSTRUCTIONS.put(0xD, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator += arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADDN"
        INSTRUCTIONS.put(0xE, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator += arg2;
            cpu.incrementIP(1);
        });
        
        //INSTRUCTION_MAP entry for "SUBI"
        INSTRUCTIONS.put(0xF, arg -> {
            cpu.accumulator -= arg;
            cpu.incrementIP(1);
        });
        
        //INSTRUCTION_MAP entry for "SUB"
        INSTRUCTIONS.put(0x10, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator -= arg1;
            cpu.incrementIP(1);
        });
        	//INSTRUCTION_MAP entry for "SUBN"
        INSTRUCTIONS.put(0x11, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator -= arg2;
            cpu.incrementIP(1);
        });
    	
        //INSTRUCTION_MAP entry for "MULI"
        INSTRUCTIONS.put(0x12, arg -> {
            cpu.accumulator *= arg;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "MUL"
        INSTRUCTIONS.put(0x13, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
            cpu.accumulator *= arg1;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "MULN"
        INSTRUCTIONS.put(0x14, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	int arg2 = memory.getData(cpu.memoryBase + arg1);
            cpu.accumulator *= arg2;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "DIVI"
        INSTRUCTIONS.put(0x15, arg -> {
        	if(arg == 0)
        		throw new DivideByZeroException("Divide by zero Exception");
            cpu.accumulator /= arg;
            cpu.incrementIP(1);
        });
      //INSTRUCTION_MAP entry for "DIV"
        INSTRUCTIONS.put(0x16, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	if(arg1 == 0)
        		throw new DivideByZeroException("Divide by zero Exception");
            cpu.accumulator /= arg1;
            cpu.incrementIP(1);
        });
        
        //INSTRUCTION_MAP entry for "DIVN"
        INSTRUCTIONS.put(0x17, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	int arg2 = memory.getData(cpu.memoryBase + arg1);
        	if(arg2 == 0)
        		throw new DivideByZeroException("Divide by zero Exception");
        	cpu.accumulator /= arg2;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "ANDI"
        INSTRUCTIONS.put(0x18, arg -> {
        	if(cpu.accumulator != 0 && arg != 0)
        		cpu.accumulator = 1;
        	else
        		cpu.accumulator = 0;
            cpu.incrementIP(1);
        });
        
        //INSTRUCTION_MAP entry for "AND"
        INSTRUCTIONS.put(0x19, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	if(cpu.accumulator != 0 && arg1 != 0)
        		cpu.accumulator = 1;
        	else
        		cpu.accumulator = 0;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "NOT"
        INSTRUCTIONS.put(0x1A, arg -> {
        	if(cpu.accumulator != 0)
        		cpu.accumulator = 0;
        	else
        		cpu.accumulator = 1;
            cpu.incrementIP(1);
        });
        
      //INSTRUCTION_MAP entry for "CMPL"
        INSTRUCTIONS.put(0x1B, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	if(arg1 < 0)
        		cpu.accumulator = 1;
        	else
        		cpu.accumulator = 0;
            cpu.incrementIP(1);
        });
       
        //INSTRUCTION_MAP entry for "CMPZ"
        INSTRUCTIONS.put(0x1C, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	if(arg1 == 0)
        		cpu.accumulator = 1;
        	else
        		cpu.accumulator = 0;
            cpu.incrementIP(1);
        });
		//INSTRUCTION_MAP entry for "JUMPN"
		INSTRUCTIONS.put(0x1D, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.instructionPointer = currentJob.getStartcodeIndex() + arg1;
		});
        //INSTRUCTION_MAP entry for "HALT"
        INSTRUCTIONS.put(0x1F, arg -> {
        	callback.halt();
        });
	}
	private class CPU {
		private int accumulator;
		private int instructionPointer;
		private int memoryBase;
		
		public void incrementIP(int val) {
			instructionPointer += val;
		}	
	}

	public Job getCurrentJob(){
		return currentJob;
	}

	public void setJob(int i){
		if(i!=1 && i!=0)
			throw new IllegalArgumentException();

		currentJob.setCurrentAcc(cpu.accumulator);
		currentJob.setCurrentIP(cpu.instructionPointer);
		currentJob = jobs[i];
		cpu.accumulator = currentJob.getCurrentAcc();
		cpu.instructionPointer = currentJob.getCurrentIP();
		cpu.memoryBase = currentJob.getStartmemoryIndex();
	}

	public void clearJob(){
		memory.clearData(currentJob.getStartmemoryIndex(), currentJob.getStartmemoryIndex()+Memory.DATA_SIZE/2);
		memory.clear(currentJob.getStartcodeIndex(), currentJob.getStartcodeIndex()+currentJob.getCodeSize());//not sure, he wanted memory.clearCode but that doesnt exist
		cpu.accumulator =0;
		cpu.instructionPointer =currentJob.getStartcodeIndex();
		currentJob.reset();
	}

	public void step(){
		try{
			int ip = cpu.instructionPointer;
			if(ip<currentJob.getStartcodeIndex()||ip>=currentJob.getStartcodeIndex()+currentJob.getCodeSize())
				throw new CodeAccessException();
			int op = memory.getOp(ip);
			int arg = memory.getArg(ip);
			//get(op).execute(arg);
		}
		catch(Exception e){
			callback.halt();
			throw e;
		}
	}

	public int getData(int index) {
		return memory.getData(index);
	}
	public int[] getData() {
		return memory.getData();
	}
	public void setData(int index, int value) {
		memory.setData(index, value);
	}
	public int getAccumulator() {
		return cpu.accumulator;
	}
	public void setAccumulator(int value) {
		cpu.accumulator = value;
	}
	public int getInstructionPointer() {
		return cpu.instructionPointer;
	}
	public void setInstructionPointer(int value) {
		cpu.instructionPointer = value;
	}
	public int getChangedIndex() {
		return memory.getChangedIndex();
	}
	public void clear(int start, int end) {
		memory.clear(start, end);
	}

	public void clearData(int start, int end) {
		memory.clearData(start, end);
	}

	public int getMemoryBase() {
		return cpu.memoryBase;
	}
	public void setMemoryBase(int value) {
		cpu.memoryBase = value;
	}
	public Instruction get(int index) {
		return INSTRUCTIONS.get(index);
	}

	public int[] getCode() {
		return memory.getCode();
	}

	public int getOp(int i) {
		return memory.getOp(i);
	}

	public int getArg(int i) {
		return memory.getArg(i);
	}

	public void setCode(int index, int op, int arg) {
		memory.setCode(index, op, arg);
	}

	public String getHex(int i){
		return memory.getHex(i);
	}

	public String getDecimal(int i){
		return memory.getDecimal(i);
	}

	public void enter() {
		getCurrentState().enter();
	}

	public boolean getAssembleFileActive() {
		return getCurrentState().getAssembleFileActive();
	}

	public boolean getClearActive() {
		return getCurrentState().getClearActive();
	}

	public boolean getLoadFileActive() {
		return getCurrentState().getLoadFileActive();
	}

	public boolean getReloadActive() {
		return getCurrentState().getReloadActive();
	}

	public boolean getRunningActive() {
		return getCurrentState().getRunningActive();
	}

	public boolean getRunPauseActive() {
		return getCurrentState().getRunPauseActive();
	}

	public boolean getStepActive() {
		return getCurrentState().getStepActive();
	}

	public boolean getChangeJobActive() {
		return getCurrentState().getChangeJobActive();
	}

	public String name() {
		return getCurrentState().name();
	}

	public int ordinal() {
		return getCurrentState().ordinal();
	}

	public int compareTo(States o) {
		return getCurrentState().compareTo(o);
	}

	public Class<States> getDeclaringClass() {
		return getCurrentState().getDeclaringClass();
	}

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
		return Enum.valueOf(enumType, name);
	}

	public States getCurrentState(){
		return currentJob.getCurrentState();
	}

	public void setCurrentState(States currState){
		currentJob.setCurrentState(currState);
	}
}
