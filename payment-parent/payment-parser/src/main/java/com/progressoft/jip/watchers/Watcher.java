package com.progressoft.jip.watchers;

import java.io.IOException;

public interface Watcher<T> {
    void startWatching(Event<T> pathEvent) throws IOException;

    void stopWatching();
}
