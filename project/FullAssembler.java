package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
public class FullAssembler implements Assembler {
    public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
        if (error == null) throw new IllegalArgumentException("Coding error: the error buffer is null");
        boolean pastData = false;
        boolean hasBlank = false;
        int blankline = 0;
        int codeLine = 0;
        int value;
        int address;
        String[] parts;
        int retValue = 0;
        boolean codeAgain = false;
        try (Scanner scan = new Scanner(Paths.get(inputFileName))){
            while (scan.hasNextLine()) {
                String temp = scan.nextLine();
                codeLine++;
                if(hasBlank) {
                    if (temp.trim().length() > 0)
                        codeAgain = true;
                    if(codeAgain) {
                        error.append("\nIllegal blank line in the source file on line " + blankline);
                    }
                }
                if (temp.trim().length() == 0) {
                    if(!codeAgain)
                        if (!hasBlank) {
                            hasBlank = true;
                            blankline = codeLine;
                        }
                }
                else if (temp.substring(0, 1).equals(' ') || temp.substring(0, 1).equals('\t')) {
                    error.append("\nLine starts with illegal white space");
                    retValue = codeLine;
                }
                if (temp.equalsIgnoreCase("DATA")) {
                    if (!temp.equals("DATA")) {
                        error.append("\nLine does not have DATA in upper case");
                        retValue = codeLine;
                    }
                    pastData = true;
                    continue;
                } else if (pastData) {
                    parts = temp.split("\\s");

                        value = Integer.parseInt(parts[1], 16);
                        address = Integer.parseInt(parts[0],16);
                    }
                if(!pastData){
                    parts = temp.split("\\s");
                    if (!InstrMap.toCode.keySet().contains(parts[0])) {
                        error.append("\nError on line " + (codeLine) + ": illegal mnemonic");
                        retValue = codeLine;
                    } else {
                        if (parts.length > 1)
                            if (noArgument.contains(parts[0])) {
                                error.append("\nError on line " + codeLine + ": this mnemonic cannot take arguments");
                                retValue = codeLine;
                            } else {
                                if (parts.length > 2) {
                                    error.append("\nError on line " + codeLine + ": this mnemonic has too many arguments");
                                    retValue = codeLine;
                                }

                                if (parts.length < 2) {
                                    error.append("\nError on line " + codeLine + ": this mnemonic is missing an argument");
                                    retValue = codeLine;
                                }
                            }
                        if (!parts[0].toUpperCase().equals(parts[0])) {
                            error.append("\nError on line " + codeLine + ": mnemonic must be upper case");
                            retValue = codeLine;
                        }
                    }
                }
            }
        } catch
                (FileNotFoundException e) {
            error.append("\nError: Unable to write the assembled program to the output file");
            return  -1;
        } catch (IOException e){
            error.append("\nUnexplained IO Exception");
            return -1;
        }catch (NumberFormatException e) {
            error.append("\nError on line " + codeLine +
                    ": argument is not a hex number");
            return codeLine;
        }
        if(retValue == 0) {
            SimpleAssembler assembler = new SimpleAssembler();
            assembler.assemble(inputFileName,outputFileName,error);

        }
        return retValue;
    }
}

