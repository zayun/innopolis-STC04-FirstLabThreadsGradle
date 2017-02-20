import com.innopolis.smoldyrev.collector.WordCollector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by smoldyrev on 13.02.17.
 */
class WordCollectorTest {

    private static WordCollector wordCollector;

    @BeforeAll
    public static void init() {

        wordCollector = new WordCollector();
    }

    @Test
    void put() {

        assertNotNull(wordCollector);

        wordCollector.put("тест1");
        wordCollector.put("тест2");
        wordCollector.put("тест3");
        wordCollector.put("тест1");
        wordCollector.put("тест1");
        wordCollector.put("тест4");

        /*сразу тестим getWordQty*/
        assertTrue(wordCollector.getWordQty("тест1") == 3);
        assertTrue(wordCollector.getWordQty("тест2") == 1);
        assertTrue(wordCollector.getWordQty("тест3") == 1);
        assertTrue(wordCollector.getWordQty("тест4") == 1);

    }


}