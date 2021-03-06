package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loader {
    public static String load(MachineModel model, File file, int codeOffset, int memoryOffset){
        int codeSize = 0;
        if(model == null || file == null)
            return null;
        try (Scanner input = new Scanner(file)) {
            boolean inCode = true;
            while(input.hasNextLine()) {
                    String line1 = input.nextLine();
                    String line2 = input.nextLine();

                Scanner parser = new Scanner(line1 + " " + line2);
                int opCode = parser.nextInt();
                int arg;
                try {
                    if (inCode) {
                        if (opCode == -1)
                            inCode = false;
                        else {
                            arg = parser.nextInt();
                            model.setCode(codeOffset + codeSize, opCode, arg);
                            codeSize++;
                           // System.out.println("here");
                        }
                    } else {
                        try{
                            int temp = parser.nextInt();
                            model.setData(opCode + memoryOffset, temp);
                            parser.close();
                        } catch(NoSuchElementException e){
                            return "From Scanner: NoSuchElementException";
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    return  "Array Index " + e.getMessage();
                }


            catch(NoSuchElementException e){
                    return "From Scanner: NoSuchElementException";
                }
            }
            for (Integer val:model.getCode()) {
                //System.out.println(val);
            }

            return "" + codeSize;
        } catch(FileNotFoundException e){
            return "File " + file.getName() + " Not Found";
        }
    }
    public static void main(String[] args) {
        MachineModel model = new MachineModel();
        String s = Loader.load(model, new File("factorial8.pexe"),100,200);
        for(int i = 100; i < 100+Integer.parseInt(s); i++) {
            System.out.println(model.getOp(i));
            System.out.println(model.getArg(i));
        }
        for (int i = 200; i < 203; i++)
            System.out.println(i + " " + model.getData(i));
    }
}
