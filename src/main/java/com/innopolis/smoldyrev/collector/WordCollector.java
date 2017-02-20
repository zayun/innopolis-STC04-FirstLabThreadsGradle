package com.innopolis.smoldyrev.collector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.HashMap;

/**
 * Собирает слова
 * в поле words
 */
public class WordCollector{

    private static Logger logger = Logger.getLogger(WordCollector.class);

    static {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * Поле для хранения полученных слов
     * Ключ - слово
     * Значение - количество повторов слова
     */
    private HashMap<String, Integer> words = new HashMap<>();

    /**
     * <p>Добавляет слово в поле words</p>
     * если слово отсутсвует в списке - добавляет ключ в words
     * если слово уже есть, то прибавляет 1 к значению найденного ключа
     * по завершению выводит текущее количество повторов, введенного слова
     * @param word добавляемое слово
     */
    public synchronized void put (String word) {

        Integer count = getWordQty(word);

        if (count == 0) logger.trace(word + " added in collection");

        words.put(word, ++count);
        System.out.println("Слово: " + word + "(" + count + ")");

    }

    /**
     * Итоговый отчет
     * выводит список words
     */
    public void getWords() {
        System.out.println("\n=======ИТОГОВЫЙ ОТЧЕТ=======");
        for (String str:words.keySet()) {
            System.out.println(str+" = " + words.get(str));
        }
    }

    /**
     * возвращает количество повторений слова
     * @param word искомое слово
     */
    public Integer getWordQty(String word) {

        Integer count = words.get(word);

        if (count == null) {
            count = 0;
        }
        return count;
    }


}
