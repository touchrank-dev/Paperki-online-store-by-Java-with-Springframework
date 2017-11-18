package com.kushnir.paperki.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ImageService {
    HashMap<Integer, ArrayList<String>> getAllOldImages();
    String generateImageName(Integer pnt);
}
