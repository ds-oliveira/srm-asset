package com.srm.asset.domain.repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.srm.asset.domain.model.Customer;

@Service
public class SQLiteRepositoryImpl implements SQLiteRepository {
	private final static Logger LOGGER = Logger.getLogger(SQLiteRepositoryImpl.class.getName());

	@Value("${db.name}")
	private String dbPath;
	private Connection connection;

	public boolean connect() {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbPath));
		} catch (Exception e) {
			throwException(e);
		}
		return true;
	}

	public boolean disconnect() {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			throwException(e);
		}
		return true;
	}

	public <T> boolean executeNonQuery(String sql) {
		return executeNonQuery(sql, new HashMap<Integer, T>());
	}

	public <T> boolean executeNonQuery(String sql, Map<Integer, T> params) {
		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);

			for (Entry<Integer, T> param : params.entrySet())
				ptmt.setObject(param.getKey(), param.getValue());

			ptmt.executeUpdate();
			ptmt.close();
		} catch (SQLException e) {
			throwException(e);
		}
		return true;
	}

	public <T> List<T> executeQuery(String sql, Class<T> model) {
		return executeQuery(sql, model, new HashMap<Integer, T>());
	}

	public <T> List<T> executeQuery(String sql, Class<T> model, Map<Integer, T> params) {
		List<T> models = new ArrayList<T>();

		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);

			for (Entry<Integer, T> param : params.entrySet())
				ptmt.setObject(param.getKey(), param.getValue());

			ResultSet rs = ptmt.executeQuery();

			while (rs.next()) {
				T modelNewInstace = getNewInstance(model);

				for (Field field : modelNewInstace.getClass().getDeclaredFields()) {
					if (field.getType() == int.class)
						field.set(modelNewInstace, rs.getInt(field.getName()));
					if (field.getType() == String.class)
						field.set(modelNewInstace, rs.getString(field.getName()));
					if (field.getType() == double.class)
						field.set(modelNewInstace, rs.getDouble(field.getName()));
				}
				models.add(modelNewInstace);
			}
			rs.close();
			ptmt.close();
		} catch (Exception e) {
			throwException(e);
		}
		return models;
	}

	public boolean findStructure(String sql) {
		boolean result = false;

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				return true;

			rs.close();
			stmt.close();
		} catch (Exception e) {
			throwException(e);
		}

		return result;
	}

	private void throwException(Exception e) {
		LOGGER.log(Level.SEVERE, String.format("%s: %s", e.getClass().getName(), e.getMessage()), e);
		throw new RuntimeException(
				String.format("Something went wrong with SQLite: %s: %s", e.getClass().getName(), e.getMessage()));
	}

	private <T> T getNewInstance(Class<T> model) {
		if (model == Customer.class)
			return (T) new Customer();
		return null;
	}
}