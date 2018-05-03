package projectview;
import project.MachineModel;
import java.io.File;
import java.util.Properties;

public class FilesManager{

    private ViewMediator view;

    private MachineModel model; // imported from project

    private String defaultDir; //this is Eclipse's default directory

    private String sourceDir; // stored directory for pasm source files

    private String executableDir; // stored directory for pexe assembled files
    private Properties properties = null; // Java method for persistent program properties
    private File currentlyExecutingFile = null;

    public FilesManager(ViewMediator view){

    }



}