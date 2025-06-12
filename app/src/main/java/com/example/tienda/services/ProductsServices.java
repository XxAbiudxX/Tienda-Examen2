package com.example.tienda.services;

import com.example.tienda.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsServices {
    private static List<Product> products = new ArrayList<>();

    static {
        dataProducts();
    }

    private static void dataProducts() {
        if (products.isEmpty()) {
            products.add(new Product("Computadora", 4500, "Prueba"));
            products.add(new Product("Tablet", 2500, "Prueba"));
            products.add(new Product("Procesador", 350, "Prueba"));
        }
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static Product getProduct(int id) {
        return products.get(id);
    }

    public static void deleteProduct(int index) {
        if (index >= 0 && index < products.size()) {
            products.remove(index);
        }
    }
}
