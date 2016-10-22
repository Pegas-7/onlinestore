package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public final class BookMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        Book entity = new Book();
        entity.setId(id);
        entity.setTitle(title);
        return entity;
    }
}