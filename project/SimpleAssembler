package project;
import project.InstrMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleAssembler implements Assembler{
    private boolean readingCode = true;
    private String makeOutputCode(String[] parts){
        if(parts.length == 1)
            return InstrMap.toCode.get(parts[0]) + "\n" +0;
        //if(parts.length == 2)
            return InstrMap.toCode.get(parts[0]) + "\n" + Integer.parseInt(parts[1],16);
    }
    private String makeOutputData(String[] parts){
        return  Integer.parseInt(parts[0],16) + "\n" + Integer.parseInt(parts[1],16);
    }
    @Override
    public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
    Map<Boolean, List<String>> lists = null;
	try (Stream<String> lines = Files.lines(Paths.get(inputFileName))) {
        lists = lines
                .filter(line -> line.trim().length() > 0) // << CORRECTION <<
                .map(line -> line.trim()) //<< CORRECTION <<
			.peek(line -> {if(line.toUpperCase().equals("DATA")) readingCode = false;})
                .map(line -> line.trim()) //<< CORRECTION <<<
			.collect(Collectors.partitioningBy(line -> readingCode));
//			System.out.println("true List " + lists.get(true)); // these lines can be uncommented
//			System.out.println("false List " + lists.get(false)); // for checking the code
        //lists.get(false).remove("DATA");
        List<String> outputCode = lists.get(true).stream()
                .map(line -> line.split("\\s+"))
                .map(this::makeOutputCode) // note how we use an instance method in the same class
                .collect(Collectors.toList());
        List<String> outputCode = lists.get(true).stream()
                .map(line -> line.split("\\s+"))
                .map(this::makeOutputData) // note how we use an instance method in the same class
                .collect(Collectors.toList());
	} catch (IOException e) {
        e.printStackTrace();
    }

}
