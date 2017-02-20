package com.innopolis.smoldyrev;

import com.innopolis.smoldyrev.collector.ThreadWordCollect;
import com.innopolis.smoldyrev.collector.WordCollector;

public class Main {

    public static void main(String[] args) {

        WordCollector wordCollector = new WordCollector();
        for (String filePath :
                args) {
            Thread thread = new Thread(new ThreadWordCollect(filePath, wordCollector));
            thread.start();

        }

    }
}
