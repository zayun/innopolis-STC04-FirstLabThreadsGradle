package com.innopolis.smoldyrev.collector;

import com.innopolis.smoldyrev.resource.ParsedTextFile;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.ArrayList;

/**
 * Created by smoldyrev on 09.02.17.
 * Отправляет Массив строк в счетчик
 */
public class ThreadWordCollect implements Runnable {

    private String filePath;

    private WordCollector collector;

    private volatile static boolean error = false;

    private static Logger logger = Logger.getLogger(ThreadWordCollect.class);

    static {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
    }

    public ThreadWordCollect(String filePath, WordCollector collector) {
        this.filePath = filePath;
        this.collector = collector;
    }

    /**Устанавливает флаг наличия ошибки для потоков
     * все потоки остановятся если
     * ThreadWordCollect.error = true*/
    public static void setError(boolean error) {
        ThreadWordCollect.error = error;
    }

    @Override
    public void run() {
        logger.trace("thread was started");
        ArrayList<String> words = null;
        try {
            words = new ParsedTextFile(filePath).getWords();

            for (String str :
                    words) {
                if (!error) {
                    collector.put(str);
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ThreadWordCollect.setError(true);
            System.out.println(e.getMessage());
        }
        logger.trace("thread was ended");
    }
}
