package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Enterprise;

public interface EnterpriseDao {
    Enterprise getEnterpriseByUserId(int UserId);
}
