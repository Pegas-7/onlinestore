package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.CategoryMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@Repository
public class CategoryDaoDbImpl implements EntityDao<Category> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Category get(Long id) {
		return jdbcTemplate.queryForObject("select * from category where category_id = ?", new Object[] { id },
				new CategoryMapper());
	}

	@Override
	public Long add(Category entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"category\" (item) VALUES (?)",
						new String[] { "category_id" });
				ps.setString(1, entity.getItem());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();

	}

	@Override
	public void update(Category entity) {
		jdbcTemplate.update("update category set item = ? where category_id = ?",
				entity.getItem(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from category where category_id = ?", id);

	}

	@Override
	public List<Category> getAll() {
		return jdbcTemplate.query("select * from category", new CategoryMapper());
	}
}
