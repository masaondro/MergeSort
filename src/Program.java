import java.io.*;
import java.util.*;

public class Program {
    
    public static void main(String[] args) {
        // Parse command line arguments
        boolean ascending = true;
        boolean isInteger = true;
        String outputFile = "";
        List<String> inputFiles = new ArrayList<>();
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    ascending = true;
                    break;
                case "-d":
                    ascending = false;
                    break;
                case "-s":
                    isInteger = false;
                    break;
                case "-i":
                    isInteger = true;
                    break;
                default:
                    if (i == args.length - 1) {
                        outputFile = args[i];
                    } else {
                        inputFiles.add(args[i]);
                    }
                    break;
            }
        }
        
        // Read input files
        List<List<String>> inputData = new ArrayList<>();
        
        for (String inputFile : inputFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(" ")) {
                        // Error: line contains spaces
                    	System.out.println("Line contains spaces");
                    } else {
                        lines.add(line);
                    }
                }
                inputData.add(lines);
            } catch (IOException e) {
                // Error: failed to read input file
            	System.out.println("Failed to read input file");
            }
        }
        
        // Merge-sort the input files
        List<String> sortedData;
        if (isInteger) {
            sortedData = mergeSortIntegers(inputData, ascending);
        } else {
            sortedData = mergeSortStrings(inputData, ascending);
        }
        
        // Write output file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : sortedData) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            // Error: failed to write output file
        	System.out.println("Failed to write output file");
        }
    }
    
    private static List<String> mergeSortIntegers(List<List<String>> inputData, boolean ascending) {
        List<Integer> sortedIntegers = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        for (List<String> inputList : inputData) {
            for (String inputString : inputList) {
                try {
                    int integer = Integer.parseInt(inputString);
                    sortedIntegers.add(integer);
                    res.add(0);
                } catch (NumberFormatException e) {
                    // Error: input string is not a valid integer
                	System.out.println("Input string is not a valid integer");
                }
            }
        }
        mergeSortInt(sortedIntegers, ascending, res);
        List<String> sortedData = new ArrayList<>();
        for (int integer : res) {
            sortedData.add(Integer.toString(integer));
        }
        return sortedData;
    }

    private static void mergeSortInt(List<Integer> integers, boolean ascending, List<Integer> res) {
        if (integers.size() > 1) {
            int mid = integers.size() / 2;
            List<Integer> left = integers.subList(0, mid);
            List<Integer> right = integers.subList(mid, integers.size());
            mergeSortInt(left, ascending, res);
            mergeSortInt(right, ascending, res);
            mergeInt(res, left, right, ascending);
        }
    }

    private static void mergeInt(List<Integer> integers, List<Integer> left, List<Integer> right, boolean ascending) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if ((ascending && left.get(i) < right.get(j)) || (!ascending && left.get(i) > right.get(j))) {
                integers.set(k++, left.get(i++));
            } else {
                integers.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            integers.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            integers.set(k++, right.get(j++));
        }
    }

    
    private static List<String> mergeSortStrings(List<List<String>> inputData, boolean ascending) {
        List<String> sortedStrings = new ArrayList<>();
        for (List<String> inputList : inputData) {
            for (String inputString : inputList) {
                sortedStrings.add(inputString);
            }
        }
        mergeSort(sortedStrings, ascending);
        return sortedStrings;
    }

    private static void mergeSort(List<String> strings, boolean ascending) {
        if (strings.size() > 1) {
            int mid = strings.size() / 2;
            List<String> left = strings.subList(0, mid);
            List<String> right = strings.subList(mid, strings.size());
            mergeSort(left, ascending);
            mergeSort(right, ascending);
            merge(strings, left, right, ascending);
        }
    }

    private static void merge(List<String> strings, List<String> left, List<String> right, boolean ascending) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            int comparison = left.get(i).compareTo(right.get(j));
            if ((ascending && comparison <= 0) || (!ascending && comparison >= 0)) {
                strings.set(k++, left.get(i++));
            } else {
                strings.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            strings.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            strings.set(k++, right.get(j++));
        }
    }

}
