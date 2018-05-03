package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullAssembler implements Assembler {
    public int assemble(String inputFileName, String outputFileName, StringBuilder error){
        List<String> code = new ArrayList<>();
        List<String> data = new ArrayList<>();
        try(Scanner scan = new Scanner(inputFileName)){
            while(scan.hasNextLine()){
                data.add(scan.nextLine());
            }
            for(int i= data.size()-1; i >= 0; i++){
                if(data.get(i))
            }
        }
return 0;
    }

}
