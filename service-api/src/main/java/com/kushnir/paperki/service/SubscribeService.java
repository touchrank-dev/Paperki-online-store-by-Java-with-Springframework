package com.kushnir.paperki.service;

import com.kushnir.paperki.service.exceptions.ServiceException;

public interface SubscribeService {

    Object subscribe(String mail, int idEmailList) throws ServiceException;
}
