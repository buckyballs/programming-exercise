package exercise;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Using java 8.
 * Find unique words in input text file<br>
 * 1. Read all lines from file as Stream<br>
 * 2. Stream.flatMap breaks line into words elements<br>
 * 3. Stream.distinct finds unique occurrences of words
 */
public class PatternCounter {

    public static final int phraseLength = 3;
    private static List<String> fileData = new ArrayList<>();

    public static void main(String[] args) {
        Map<String, Integer> map;
        map = getPatternCounts(args);
        printMap(map);
    }

    public static Map<String, Integer> getPatternCounts(String args[]) {
        Map<String, Integer> map = new HashMap<>();
        // input arguments length must be 2, first for input file path and second for pattern case to execute
        if (args.length != 2) {
            System.err.println("Enter valid input arguments, which are " + "\n" + "1. Input file path" +
                    "\n" + "2. Acceptable pattern ids are 1,2, and 3");
        }
        boolean patternIdCorrect = false;
        try {
            patternIdCorrect = Integer.parseInt(args[1]) == 1 || Integer.parseInt(args[1]) == 2 || Integer.parseInt(args[1]) == 3;
        } catch (NumberFormatException e) {
        }

        if (patternIdCorrect) {
            //1. Convert Input.txt data into list using space character as the de-limiter for words
            try {
                fileData = Files.lines(Paths.get(args[0]), Charset.defaultCharset())
                        .flatMap(line -> Arrays.stream(line.split(" ")))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                System.err.println("Error reading input text file: Exception Occurred" + e.getMessage());
            }
            if (fileData != null && !fileData.isEmpty()) {
                //4. print result based on 2nd argument
                switch (args[1]) {
                    case "1":
                        //3. Filter words from the all words list in 1
                        List<String> words = fileData.stream()
                                .filter(e -> !e.matches("-?[0-9]+"))
                                .collect(Collectors.toList());
                        // print patterns results
                        map = countUniqueWordsOrNumbers(words);
                        break;
                    case "2":
                        //2. Filter numbers (positives, negatives and zeros) from the all words list in 1
                        List<String> numbers = fileData.stream()
                                .filter(e -> e.matches("-?[0-9]+"))
                                .collect(Collectors.toList());
                        // print patterns results
                        map = countUniqueWordsOrNumbers(numbers);
                        break;
                    case "3":
                        // print patterns results
                        map = countUniquePhrases(fileData);
                        break;
                }
            }
        } else {
            System.err.println("Error parsing second argument: Acceptable pattern ids are 1,2, and 3.");
        }
        return map;
    }

    /**
     * Count repetitions of list elements
     *
     * @return map contains list element as String and its occurrences as Integer
     */
    private static Map<String, Integer> countUniqueWordsOrNumbers(List<String> words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            Integer wordCount = map.get(word);
            map.put(word, (wordCount == null) ? 1 : wordCount + 1);
        }
        return map;
    }

    /**
     * print unique phrase of three consecutive words from list of words
     */
    private static Map<String, Integer> countUniquePhrases(List<String> inputFileDataInList) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < inputFileDataInList.size(); i++) {
            if (i <= inputFileDataInList.size() - phraseLength) {
                String word = (inputFileDataInList.get(i) + " " + inputFileDataInList.get(i + 1)
                        + " " + inputFileDataInList.get(i + 2));

                Integer wordCount = map.get(word);
                map.put(word, (wordCount == null) ? 1 : wordCount + 1);
            }
        }
        return map;
    }

    private static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}
