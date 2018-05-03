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
                        error.append("\nIllegal blank line in the source file on line: " + blankline);
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
                if (!pastData)
                    code.add(temp);
                else
                    data.add(temp);
            }
        } catch(FileNotFoundException){
            error.append("\nUnable to open the source file"); // that is in the catch (FileNotFoundException)
            return -1;
        }
        for (int i = data.size() - 1; i >= 0; i++) {
            if (data.get(i).equals(""))
                data.remove(i);
        }

        return 0;
    }
}

