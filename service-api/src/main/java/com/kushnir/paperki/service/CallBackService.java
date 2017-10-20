package com.kushnir.paperki.service;

import com.kushnir.paperki.model.callback.Callback;
import com.kushnir.paperki.service.exceptions.ServiceException;

public interface CallBackService {

    Object addCallBack(Callback callback) throws ServiceException;
}
