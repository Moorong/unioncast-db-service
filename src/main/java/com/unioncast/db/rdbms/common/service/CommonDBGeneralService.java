package com.unioncast.db.rdbms.common.service;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class CommonDBGeneralService<T extends Serializable, ID extends Serializable>
		implements GeneralService<T, ID> {

	protected CommonGeneralDao<T, ID> generalDao;

	public CommonGeneralDao<T, ID> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(CommonGeneralDao<T, ID> generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		return generalDao.save(entity);
	}

	@Override
	public void save(List<T> entities) throws DaoException {
		generalDao.save(entities);
	}

	@Override
	public T[] findT(T t) throws DaoException, IllegalAccessException {
		return generalDao.findT(t);
	}

	@Override
	public void saveOrUpdate(T entity) throws DaoException {
		generalDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(List<T> entities) throws DaoException {
		generalDao.saveOrUpdate(entities);
	}

	@Override
	public void delete(T entity) throws DaoException {
		generalDao.delete(entity);
	}

	@Override
	public void delete(List<T> entities) throws DaoException {
		generalDao.delete(entities);
	}

	@Override
	public void update(T entity) throws DaoException {
		generalDao.update(entity);
	}

	@Override
	public void update(List<T> entities) throws DaoException {
		generalDao.update(entities);
	}

	@Override
	public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		return generalDao.updateAndReturnNum(entity);
	}

	@Override
	public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		generalDao.updateNotNullField(entity);
	}

	@Override
	public int deleteById(ID id) throws DaoException {
		return generalDao.deleteById(id);
	}

	@Override
	public int deleteById(List<ID> idList) throws DaoException {
		return generalDao.deleteById(idList);
	}

	@Override
	public int deleteById(ID[] ids) throws DaoException {
		return generalDao.deleteById(ids);
	}

	@Override
	public T findById(ID id) throws DaoException {
		return generalDao.findById(id);
	}

	@Override
	public T[] findById(List<ID> ids) throws DaoException {
		return generalDao.findById(ids);
	}

	@Override
	public T[] findByUpdateTime(Date startTime, Date endTime) throws DaoException {
		return null;
	}

	@Override
	public T[] findByUpdateTime(Date startTime, Date endTime, int firstResult, int maxResults) throws DaoException {
		return null;
	}

	@Override
	public T[] find(ID id) throws DaoException {
		return generalDao.find(id);
	}

	@Override
	public T[] findAll() throws DaoException {
		return generalDao.findAll();
	}

	@Override
	public T[] findAll(int firstResult, int maxResults) throws DaoException {
		return generalDao.findAll(firstResult, maxResults);
	}

	@Override
	public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
		return generalDao.paginationAll(currentPageNo, pageSize);
	}

	@Override
	public int countAll() throws DaoException {
		return generalDao.countAll();
	}

	@Override
	public int countByUpdateTime(Date startTime, Date endTime) throws DaoException {
		return 0;
	}

	@Override
	public Pagination<T> page(T entity, Integer currentPage, Integer pageSize) throws DaoException {
		return generalDao.page(entity, currentPage, pageSize);
	}

	@Override
	public List<Long> batchAdd(List<T> entities) throws DaoException {
		return generalDao.batchAdd(entities);
	}

	@Override
	public int batchDelete(List<Long> ids) {
		return generalDao.batchDelete(ids);
	}

	@Override
	public T findByString(String str) throws DaoException {
		return null;
	}

	@Override
	public T[] findBySystemId(Long id) throws DaoException {
		return null;
	}

	@Override
	public Long[] batchAdd(T[] entities) throws DaoException {
		return generalDao.batchAdd(entities);
	}

	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return generalDao.batchDelete(ids);
	}

	@Override
	public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
		return generalDao.page(pageCriteria);
	}

	@Override
	public Long[] add(T[] entities)
			throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException {
		return generalDao.add(entities);
	}

	@Override
	public int delete(Class<?> clazz, ID[] ids) throws DaoException {
		return 0;
	}

	@Override
	public int updateNotNullField(T[] entities)
			throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException {
		return generalDao.updateNotNullField(entities);
	}

	@Override
	public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
		return generalDao.find(pageCriteria);
	}

	@Override
	public Pagination<T> generalPage(PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException {
		return generalDao.generalPage(pageCriteria);
	}

}
