package exercise;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class PatternCounterTest {

    String args[] = {"src/test/resources/Input1.txt", "1"};

    @Test
    public void testGetPatternCounts() throws Exception {
        File file = new File(args[0]);
        if (file.exists()) {
            // test for unique words count
            Map uniqueWords = PatternCounter.getPatternCounts(args);
            Assert.assertNotNull(uniqueWords);

            // test for unique numbers count
            args[1] = "2";
            Map uniqueNumbers = PatternCounter.getPatternCounts(args);
            Assert.assertNotNull(uniqueNumbers);

            // test for unique phrases count
            args[1] = "3";
            Map uniquePhrases = PatternCounter.getPatternCounts(args);
            Assert.assertNotNull(uniquePhrases);

            // test for wrong input argument
            // console will show this message: Error parsing second argument: Acceptable pattern ids are 1,2, and 3.
            args[1] = "";
            Map wrongInput2ndArg = PatternCounter.getPatternCounts(args);
            Assert.assertTrue(wrongInput2ndArg.isEmpty());

            // test for less than three words for phrase test in text file input argument
            args[0] = "src/test/resources/Input2.txt";
            args[1] = "3";
            Map lessThanThreeWordsInTextFile = PatternCounter.getPatternCounts(args);
            Assert.assertTrue(lessThanThreeWordsInTextFile.isEmpty());

            // test for empty input file
            args[0] = "src/test/resources/Input3.txt";
            Map emptyInputFile = PatternCounter.getPatternCounts(args);
            Assert.assertTrue(emptyInputFile.isEmpty());
        } else {
            Assert.fail();
        }
    }
}