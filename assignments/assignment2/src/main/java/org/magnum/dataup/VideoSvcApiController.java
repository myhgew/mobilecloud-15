/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import org.magnum.dataup.helper.VideoFileManager;
import org.magnum.dataup.helper.VideoManager;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class VideoSvcApiController implements VideoSvcApi {

    /**
     * You will need to create one or more Spring controllers to fulfill the
     * requirements of the assignment. If you use this file, please rename it
     * to something other than "AnEmptyController"
     * <p/>
     * <p/>
     * ________  ________  ________  ________          ___       ___  ___  ________  ___  __
     * |\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \
     * \ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_
     * \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \
     * \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \
     * \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
     * \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
     */

    @Autowired
    private VideoFileManager videoFileManager;

    private static final AtomicLong currentId = new AtomicLong(0L);
    private Map<Long, Video> videos = new HashMap<>();


    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Video> getVideoList() {
        return videos.values();
    }

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    Video addVideo(@RequestBody Video v) {
        checkAndSetId(v);
        v.setDataUrl(getDataUrl(v.getId()));
        videos.put(v.getId(), v);
        return v;
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    VideoStatus setVideoData(@PathVariable(ID_PARAMETER) long id, @RequestPart(DATA_PARAMETER) TypedFile videoData) {
        Video foundVideo = findVideoById(id);

        if (foundVideo == null) return null;
        if (videoFileManager.hasVideoData(foundVideo)) return null;

        try {
            videoFileManager.saveVideoData(foundVideo, videoData.in());
            VideoStatus videoStatus = new VideoStatus(VideoStatus.VideoState.READY);
            return videoStatus;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Response getData(@PathVariable(ID_PARAMETER) long id) {
        Video foundVideo = findVideoById(id);

        if (foundVideo == null) return new Response("", 404, "", null, null);

        OutputStream outputStream = null;
        try {
            videoFileManager.copyVideoData(foundVideo, outputStream);
            return new Response("", 200, "good", null, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response("", 404, "", null, null);
    }

    private void checkAndSetId(Video entity) {
        if (entity.getId() == 0) {
            entity.setId(currentId.incrementAndGet());
        }
    }

    public Video findVideoById(long id) {
        for (Long currentId : videos.keySet()) {
            if (id == currentId) return videos.get(currentId);
        }
        return null;
    }

    private String getDataUrl(long videoId){
        String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
        return url;
    }

    private String getUrlBaseForLocalServer() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String base =
                "http://"+request.getServerName()
                        + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
        return base;
    }
}
