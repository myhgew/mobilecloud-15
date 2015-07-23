package org.magnum.dataup.helper;

import org.magnum.dataup.model.Video;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class VideoManager {
    private static final AtomicLong currentId = new AtomicLong(0L);
    private Map<Long, Video> videos = new HashMap<>();

    public Collection<Video> getVideoList() {
        return videos.values();
    }

    public Video save(Video entity) {
        checkAndSetId(entity);

        // TODO
        entity.setDataUrl("Test Data Url");
        entity.setLocation("Test Location");

        videos.put(entity.getId(), entity);
        return entity;
    }

    private void checkAndSetId(Video entity) {
        if (entity.getId() == 0) {
            entity.setId(currentId.incrementAndGet());
        }
    }

    public Video findVideoById(long id) {
        return null;
    }
}
