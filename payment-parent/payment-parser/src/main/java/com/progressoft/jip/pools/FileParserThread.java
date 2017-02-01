package com.progressoft.jip.pools;

import com.progressoft.jip.watchers.Event;

import java.nio.file.Path;

public class FileParserThread extends AutoReturnableThread {
    private Path filePath;
    private Event<Path> pathEvent;
    private DataFileParsersPool dataFileParsersPool;

    public FileParserThread(DataFileParsersPool dataFileParsersPool) {
        this.dataFileParsersPool = dataFileParsersPool;
    }

    public void startParsing(Path filePath, Event<Path> pathEvent) {
        this.filePath = filePath;
        this.pathEvent = pathEvent;
        notifyThread();
    }

    @Override
    protected void doOperation() {
        pathEvent.handleEvent(filePath);
    }

    @Override
    protected void releaseThread() {
        dataFileParsersPool.put(this);
    }
}