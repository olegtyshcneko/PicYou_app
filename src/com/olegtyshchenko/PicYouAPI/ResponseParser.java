package com.olegtyshchenko.PicYouAPI;

import com.olegtyshchenko.PicYouAPI.Models.Responses.*;
import com.google.gson.Gson;


/**
 * Parses json response string
 */
public class ResponseParser {
    private Gson gson;

    public ResponseParser() {
        gson = new Gson();
    }

    public ImageResponse imageParse(String json) {
        return gson.fromJson(json, ImageResponse.class);
    }
    public ImagesResponse imagesParse(String json) {
        return gson.fromJson(json, ImagesResponse.class);
    }

}
