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

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    // An in-memory list that the servlet uses to store the
    // videos that are sent to it by clients
    private List<Video> videos = new CopyOnWriteArrayList<Video>();

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
//        Collection<Video> videoList = new ArrayList<>();
//        Video video = Video.create().withDuration(10).withSubject("subtitle").withTitle("title").build();
//        videoList.add(video);
//        return videoList;
        return videos;
    }

    @RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v) {
        try {
            VideoFileManager videoFileManager = VideoFileManager.get();


            videos.add(v);
            return v;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.POST)
    public VideoStatus setVideoData(@RequestParam(ID_PARAMETER) long id, @RequestPart(DATA_PARAMETER) TypedFile videoData) {
        // TODO
        return null;
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.GET)
    public Response getData(@PathVariable(ID_PARAMETER) long id) {
        // TODO
        return null;
    }


}
