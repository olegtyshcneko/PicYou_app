package com.olegtyshchenko.PicYouAPI.Models.Responses;


import com.olegtyshchenko.PicYouAPI.Models.Image;

import java.util.List;

public class ImagesResponse {
    public int num_pages;
    public List<Image> images;
    public String status;
}
