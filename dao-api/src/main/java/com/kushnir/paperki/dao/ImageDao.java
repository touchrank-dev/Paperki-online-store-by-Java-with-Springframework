package com.kushnir.paperki.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface ImageDao {
    HashMap<Integer, ArrayList<String>> getAllOldImages();
}
