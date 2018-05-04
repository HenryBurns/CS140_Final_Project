package project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullAssembler implements Assembler {
    public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
        if (error == null) throw new IllegalArgumentException("Coding error: the error buffer is null");
        List<String> code = new ArrayList<>();
        List<String> data = new ArrayList<>();
        boolean pastData = false;
        boolean hasBlank = false;
        boolean readingCode = false;
        int blankline = 0;
        int codeLine = 0;
        try (Scanner scan = new Scanner(inputFileName)) {
            while (scan.hasNextLine()) {
                String temp = scan.nextLine();
                codeLine++;
                if (temp.trim().length() == 0) {
                    if (hasBlank)
                        error.append("\nIllegal blank line in the source file on line " + blankline);
                    else {
                        hasBlank = true;
                        blankline = codeLine;
                    }
                }
                if(temp.substring(0,1).equals(' ') || temp.substring(0,1).equals('\t'))
                    error.append("\nLine starts with illegal white space");
                if (temp.equalsIgnoreCase("DATA")) {
                    if(!temp.equals("DATA"))
                        error.append("\nLine does not have DATA in upper case");
                    pastData = true;
                    continue;
                }
                else if(pastData){
                    temp = temp.split("\\s");
                    // If you are in code and you split the line into parts using line.trim().split("\\s+"), then parts[0] must be contained in
                    // InstrMap.toCode.keySet().  You also need to use the same trick that was used for DATA to be sure that the mnemonic, if present, is in upper case.
                }
            }
        } catch(FileNotFoundException){
            error.append("\nUnable to open the source file"); // that is in the catch (FileNotFoundException)
            return -1;
        }

        return 0;
    }
}

