package com.progressoft.jip.watchers;

import com.progressoft.jip.pools.DataFileParsersPool;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectoryWatcher implements Watcher<Path> {
    private WatchService watchService;
    private DataFileParsersPool dataFileParsersPool = new DataFileParsersPool();
    private Path path;
    private boolean continueWatching;

    public DirectoryWatcher(Path path) {
        this.path = path;
    }

    @Override
    public void startWatching(Event<Path> pathEvent) throws IOException {
        registerWatchService(path);
        checkAndParseExistingFiles(pathEvent);
        continueWatching = true;
        watchAndParseNewFiles(pathEvent);
    }

    @Override
    public void stopWatching() {
        continueWatching = false;
    }

    private void registerWatchService(Path path) throws IOException {
        FileSystem fileSystem = path.getFileSystem();
        watchService = fileSystem.newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    private void checkAndParseExistingFiles(Event<Path> pathEvent) throws IOException {
        Files.list(path).filter(Files::isRegularFile)
                .forEach(p -> deliverFileForParsing(p, pathEvent));
    }

    private void deliverFileForParsing(Path filePath, Event<Path> pathEvent) {
        if (filePath.toFile().isFile()) {
            dataFileParsersPool.get().startParsing(filePath, pathEvent);
        }
    }

    private void watchAndParseNewFiles(Event<Path> pathEvent) {
        WatchKey watchKey = new DefaultWatchKey();
        do {
            try {
                watchKey = watchService.take();
                parseAnyCreatedFiles(watchKey, pathEvent);
                watchKey.reset();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (watchKey.isValid() && continueWatching);
    }

    private void parseAnyCreatedFiles(WatchKey watchKey, Event<Path> pathEvent) {
        Set<Path> createdFilePaths = getCreatedFilePathsFromWatchKey(watchKey);
        if (!createdFilePaths.isEmpty()) {
            createdFilePaths.forEach(p -> deliverFileForParsing(p, pathEvent));
            dataFileParsersPool.waitForTakenObjectsToReturn();
        }
    }

    private Set<Path> getCreatedFilePathsFromWatchKey(WatchKey watchKey) {
        return watchKey.pollEvents().stream().filter(e -> e.kind().equals(StandardWatchEventKinds.ENTRY_CREATE))
                .map(e -> {
                    @SuppressWarnings("unchecked")
                    Path eventPath = ((WatchEvent<Path>) e).context();
                    return path.resolve(eventPath.toString());
                }).collect(Collectors.toSet());
    }

    private class DefaultWatchKey implements WatchKey {
        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public List<WatchEvent<?>> pollEvents() {
            return new ArrayList<>();
        }

        @Override
        public boolean reset() {
            return false;
        }

        @Override
        public void cancel() {
            /* not needed */
        }

        @Override
        public Watchable watchable() {
            return null;
        }
    }
}
