package com.kushnir.paperki.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ContentBeanImpl implements ContentBean {

    @Override
    public void initModel(Model model) {

    }
}
