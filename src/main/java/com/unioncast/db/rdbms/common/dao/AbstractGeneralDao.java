package com.unioncast.db.rdbms.common.dao;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractGeneralDao<T extends Serializable, ID extends Serializable> implements GeneralDao<T, ID> {

    protected JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public Class<T> getEntityClass() {
        return null;
    }

    @Override
    public String getPkName() {
        return null;
    }

    @Override
    public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
        return null;
    }

    @Override
    public void save(List<T> entities) throws DaoException {

    }

    @Override
    public void saveOrUpdate(T entity) throws DaoException {

    }

    @Override
    public void saveOrUpdate(List<T> entities) throws DaoException {

    }

    @Override
    public int deleteById(ID id) throws DaoException {
        return 0;
    }

    @Override
    public int deleteById(List<ID> idList) throws DaoException {
        return 0;
    }

    @Override
    public int deleteById(ID[] ids) throws DaoException {
        return 0;
    }

    @Override
    public void delete(T entity) throws DaoException {

    }

    @Override
    public void delete(List<T> entities) throws DaoException {

    }

    @Override
    public void update(T entity) throws DaoException {

    }

    @Override
    public void update(List<T> entities) throws DaoException {

    }

    @Override
    public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
        return 0;
    }

    @Override
    public int update(String hql) throws DaoException {
        return 0;
    }

    @Override
    public int update(String hql, Map<String, Object> valuesMap) throws DaoException {
        return 0;
    }

    @Override
    public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {

    }

    @Override
    public T findById(ID id) throws DaoException {
        return null;
    }

    @Override
    public T[] findById(List<ID> ids) throws DaoException {
        return null;
    }

    @Override
    public List<T> findByProperty(String property, Object value) {
        return null;
    }

    @Override
    public List<T> findByProperty(String property, Object value, int maxResults) {
        return null;
    }

    @Override
    public T findUniqueByProperty(String property, Object value) {
        return null;
    }

    @Override
    public T[] findAll() throws DaoException {
        return null;
    }

    @Override
    public T[] findAll(int firstResult, int maxResults) throws DaoException {
        return null;
    }

    @Override
    public T[] find(String hql) throws DaoException {
        return null;
    }

    @Override
    public T[] find(String hql, Map<String, Object> valuesMap) throws DaoException {
        return null;
    }

    @Override
    public T[] find(String hql, int firstResult, int maxResults) throws DaoException {
        return null;
    }

    @Override
    public T[] find(String hql, int firstResult, int maxResults, Map<String, Object> valuesMap) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> pagination(String hql, Integer currentPageNo, Integer pageSize) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> pagination(String hql, Integer currentPageNo, Integer pageSize, Map<String, Object> valuesMap)
            throws DaoException {
        return null;
    }

    @Override
    public int countAll() throws DaoException {
        return 0;
    }

    @Override
    public int count(String hql) throws DaoException {
        return 0;
    }

    @Override
    public int count(String hql, Map<String, Object> valuesMap) throws DaoException {
        return 0;
    }

    @Override
    public Pagination<T> page(T entity, Integer currentPage, Integer pageSize) throws DaoException {
        return null;
    }

    @Override
    public List<Long> batchAdd(List<T> entitys) throws DaoException {
        return null;
    }

    @Override
    public Long[] batchAdd(T[] entitys) throws DaoException {
        return null;
    }

    @Override
    public int batchDelete(List<Long> ids) {
        return 0;
    }

    @Override
    public T findByString(String str) throws DaoException {
        return null;
    }

    public Pagination<T> paginationAll(T entity, Integer currentPageNo, Integer pageSize) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> page(PageCriteria pageCriteria, Long userId) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper) throws DaoException {
        return null;
    }

    @Override
    public Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper, String userFieldName, Long userId) throws DaoException {
        return null;
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return 0;
    }

    @Override
    public Long[] add(T[] entitys) throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException {
        return null;
    }

    @Override
    public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public Pagination<T> generalPage(PageCriteria pageCriteria)
            throws DaoException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public int delete(Class<?> clazz, ID[] ids) throws DaoException {
        return 0;
    }

    @Override
    public T[] find(ID id) throws DaoException {
        return null;
    }

    @Override
    public T[] findT(T id) throws DaoException, IllegalAccessException {
        return null;
    }

    @Override
    public int updateNotNullField(T[] entities) throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException {
        return 0;
    }

}
