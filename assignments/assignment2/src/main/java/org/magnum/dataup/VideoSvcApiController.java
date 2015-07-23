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
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.util.Collection;

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
    private VideoManager videoManager;

    @Autowired
    private VideoFileManager videoFileManager;

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Video> getVideoList() {
        return videoManager.getVideoList();
    }

    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    Video addVideo(@RequestBody Video v) {
        return videoManager.save(v);
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    VideoStatus setVideoData(@PathVariable(ID_PARAMETER) long id, @RequestPart(DATA_PARAMETER) TypedFile videoData) {
        VideoStatus videoStatus = new VideoStatus(VideoStatus.VideoState.READY);
        return videoStatus;
    }

    @RequestMapping(value = VIDEO_DATA_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Response getData(@PathVariable(ID_PARAMETER) long id) {
        // TODO
        return new Response("", 200, "good", null, null);
    }


}
