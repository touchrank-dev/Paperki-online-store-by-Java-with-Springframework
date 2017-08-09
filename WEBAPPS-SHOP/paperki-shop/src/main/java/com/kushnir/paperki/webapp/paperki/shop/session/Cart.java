package com.kushnir.paperki.webapp.paperki.shop.session;

import com.kushnir.paperki.model.Product;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("session")
public class Cart {

    private HashMap<Integer, Product> items;
}
