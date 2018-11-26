package com.srm.asset.domain.repository;

import java.util.List;
import java.util.Map;

public interface SQLiteRepository {
	public boolean connect();
	public boolean disconnect();
	public <T> boolean executeNonQuery(String sql);
	public <T> boolean executeNonQuery(String sql, Map<Integer, T> params);
	public <T> List<T> executeQuery(String sql, Class<T> model);
	public <T> List<T> executeQuery(String sql, Class<T> model, Map<Integer, T> params);
	public boolean findStructure(String sql);
}