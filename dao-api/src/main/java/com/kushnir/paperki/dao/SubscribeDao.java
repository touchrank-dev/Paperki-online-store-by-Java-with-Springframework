package com.kushnir.paperki.dao;

public interface SubscribeDao {
    int subscribe(String email, int idEmailList) throws Exception;
}
