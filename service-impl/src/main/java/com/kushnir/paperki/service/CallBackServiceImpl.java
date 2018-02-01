package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CallBackDao;
import com.kushnir.paperki.model.callback.Callback;
import com.kushnir.paperki.model.callback.CallbackErrorResponse;
import com.kushnir.paperki.service.exceptions.ServiceException;

import com.kushnir.paperki.service.mail.HtmlMailer;
import com.kushnir.paperki.service.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("callBackService")
public class CallBackServiceImpl implements CallBackService {

    private static final Logger LOGGER = LogManager.getLogger(CallBackServiceImpl.class);

    @Autowired
    private CallBackDao callbackDao;

    @Autowired
    HtmlMailer htmlMailer;

    @Override
    @Transactional
    public Object addCallBack(Callback callback) throws ServiceException {
        LOGGER.debug("addCallBack ({})", callback);
        CallbackErrorResponse callbackErrorResponse = new CallbackErrorResponse();
        try {
            Assert.notNull(callback.getName(), "Пожалуйста укажите Ваше Имя");
            Assert.hasText(callback.getName(), "Пожалуйста укажите Ваше Имя");
        } catch (Exception e) {
            callbackErrorResponse.setName(e.getMessage());
        }
        try {
            Assert.notNull(callback.getPhone(), "Пожалуйста укажите Ваш номер телефона");
            Assert.hasText(callback.getPhone(), "Пожалуйста укажите Ваш номер телефона");
        } catch (Exception e) {
            callbackErrorResponse.setPhone(e.getMessage());
        }
        // end validation
        if(callbackErrorResponse.isErrors()) {
            LOGGER.error("ОШИБКА ЗАПРОСА ОБРАТНОГО ЗВОНКА: {}", callbackErrorResponse);
            return callbackErrorResponse;
        } else {
            try {
                int id = callbackDao.addCallback(callback);
                LOGGER.debug("ЗАПРОС НА ОБРАТНЫЙ ЗВОНОК УСПЕШНО ЗАРЕГИСТРИРОВАН, id: {}", id);

                callback.setId(id);

                htmlMailer.sendCallbackAcceptMessage(callback);

                return new Integer(id);
            } catch (Exception e) {
                LOGGER.error("ОШИБКА ЗАПРОСА ОБРАТНОГО ЗВОНКА", e.getMessage());
                throw new ServiceException(e.getMessage());
            }
        }
    }
}
