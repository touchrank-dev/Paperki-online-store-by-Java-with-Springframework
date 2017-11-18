package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.callback.Callback;
import com.kushnir.paperki.model.callback.CallbackErrorResponse;
import com.kushnir.paperki.model.feedback.FeedbackErrorResponse;
import com.kushnir.paperki.model.feedback.FeedbackRequest;
import com.kushnir.paperki.model.subscribe.SubscribeErrorResponse;
import com.kushnir.paperki.model.subscribe.SubscribeRequest;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.service.*;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RESTcontroller {

    private static final Logger LOGGER = LogManager.getLogger(RESTcontroller.class);
    private static final String version = "1.0";

    @Autowired
    SubscribeService subscribeService;

    @Autowired
    CallBackService callBackService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    Mailer mailer;

    @Value("${webapp.host}")
    String host;

    //curl -v [host]:8080/api
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage version() {
        LOGGER.debug("{} Rest api version() >>>", host);
        RestMessage restMessage = new RestMessage(HttpStatus.OK, version);
        return restMessage;
    }

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage subscribe (@RequestBody SubscribeRequest subscribeRequest) throws ServiceException {
        LOGGER.debug("EMAIL SUBSCRIBE >>>\nREQUEST EMAIL: {}", subscribeRequest.getEmail());
        RestMessage restMessage;
        try {
            Object obj = subscribeService.subscribe(subscribeRequest.getEmail(), 1);
            if(obj instanceof SubscribeErrorResponse) {
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "SUBSCRIBE ERROR",
                        (SubscribeErrorResponse)obj);
                return restMessage;
            } else {
                restMessage = new RestMessage(HttpStatus.OK, "EMAIL SUCCESSFULLY SUBSCRIBED", (Integer)obj);
                return restMessage;
            }
        } catch (Exception e) {
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST SUBSCRIBE");
            return restMessage;
        }
    }

    @PostMapping("/callback")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage callback (@RequestBody Callback callback) {
        LOGGER.debug("CALLBACK REQUEST >>>\nCALLBACK REQUEST: {}", callback);
        RestMessage restMessage;
        try {
            Object obj = callBackService.addCallBack(callback);
            if(obj instanceof CallbackErrorResponse) {
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "CALLBACK ERROR",
                        (CallbackErrorResponse)obj);
                return restMessage;
            } else {
                restMessage = new RestMessage(HttpStatus.OK, "CALLBACK SUCCESSFULLY PLACED", (Integer)obj);
                return restMessage;
            }
        } catch (Exception e) {
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST CALLBACK");
            return restMessage;
        }
    }

    @PostMapping("/feedback")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage feedback (@RequestBody FeedbackRequest feedbackRequest,
                                               HttpSession session,
                                               HttpServletRequest req) {
        LOGGER.debug("FEEDBACK REQUEST >>>\nFEEDBACK REQUEST: {}", feedbackRequest);
        RestMessage restMessage;
        User user = (User) session.getAttribute("user");
        int userId = 0;
        if (user != null) {
            userId = (user.getId() == null)? 0:user.getId();
        }
        try {
            Object obj = feedbackService.addFeedback(feedbackRequest, req.getRemoteAddr(), userId);
            if(obj instanceof FeedbackErrorResponse) {
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "FEEDBACK ERROR",
                        (FeedbackErrorResponse)obj);
                return restMessage;
            } else {
                restMessage = new RestMessage(HttpStatus.OK, "FEEDBACK SUCCESSFULLY PLACED", (Integer)obj);
                return restMessage;
            }
        } catch (Exception e) {
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST FEEDBACK");
            return restMessage;
        }
    }

}
