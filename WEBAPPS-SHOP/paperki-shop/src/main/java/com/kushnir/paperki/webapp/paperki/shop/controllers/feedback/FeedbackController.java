package com.kushnir.paperki.webapp.paperki.shop.controllers.feedback;

import com.kushnir.paperki.model.feedback.Feedback;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.FeedbackService;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private static final Logger LOGGER = LogManager.getLogger(FeedbackController.class);

    private static final String FEEDBACK_MENU_NAME = "feedback";

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean catalogBean;

    @Value("${content.path}")
    String contentPath;

    @Autowired
    FeedbackService feedbackService;

    @GetMapping
    public String catalogPage(Model model) {
        LOGGER.debug("catalogPage() >>>");
        model.addAttribute("templatePathName", contentPath + FEEDBACK_MENU_NAME);
        model.addAttribute("fragmentName", FEEDBACK_MENU_NAME);
        return "index";
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

    @ModelAttribute("feedbacks")
    public ArrayList<Feedback> allFeedback () {
        return feedbackService.getAllFeedback();
    }
}
