package com.unioncast.db.rdbms.common.service;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.common.dao.SspGeneralDao;
import com.unioncast.db.rdbms.core.exception.DaoException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class SspGeneralService<T extends Serializable, ID extends Serializable> implements
		GeneralService<T, ID> {

	protected SspGeneralDao<T, ID> generalDao;

	public SspGeneralDao<T, ID> getGeneralDao() {
		return generalDao;
	}

	public abstract void setGeneralDao(SspGeneralDao<T, ID> generalDao);

	@Override
	public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		return generalDao.save(entity);
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
	public void delete(T entity) throws DaoException {
	}

	@Override
	public void delete(List<T> entities) throws DaoException {
	}

	@Override
	public void update(T entity) throws DaoException {
		generalDao.update(entity);
	}

	@Override
	public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		return generalDao.updateAndReturnNum(entity);
	}

	@Override
	public void update(List<T> entities) throws DaoException {
	}

	@Override
	public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException {
		generalDao.updateNotNullField(entity);
	}

	@Override
	public T findById(ID id) throws DaoException {
		return generalDao.findById(id);
	}

	@Override
	public T findByString(String str) throws DaoException {
		return generalDao.findByString(str);
	}

	@Override
	public T[] findById(List<ID> ids) throws DaoException {
		return generalDao.findById(ids);
	}

	@Override
	public T[] findBySystemId(Long id) throws DaoException {
		return generalDao.findBySystemId(id);
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
	public T[] findAll() throws DaoException {
		return generalDao.findAll();
	}

	@Override
	public T[] findAll(int firstResult, int maxResults) throws DaoException {
		return null;
	}

	@Override
	public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
		return null;
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
	public List<Long> batchAdd(List<T> entitys) throws DaoException {
		return generalDao.batchAdd(entitys);
	}

	@Override
	public int batchDelete(List<Long> ids) {
		return generalDao.batchDelete(ids);
	}

	@Override
	public Long[] batchAdd(T[] entitys) throws DaoException {
		return generalDao.batchAdd(entitys);
	}

	@Override
	public int batchDelete(Long[] ids) throws DaoException {
		return generalDao.batchDelete(ids);
	}

	@Override
	public Pagination<T> page(PageCriteria pageCriteria) throws DaoException {
		return generalDao.page(pageCriteria);
	}

	public T[] find(ID id) throws DaoException {
		return generalDao.find(id);
	}

	public T[] findT(T t) throws DaoException, IllegalAccessException {
		return generalDao.findT(t);
	}

	@Override
	public Long[] add(T[] entitys) {
		return null;
	}

	@Override
	public int delete(Class<?> clazz, ID[] ids) throws DaoException {
		return 0;
	}

	@Override
	public int updateNotNullField(T[] entities) throws DaoException, IllegalArgumentException, IllegalAccessException,
			SQLException {
		return generalDao.updateNotNullField(entities);
	}

	@Override
	public T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException {
		return generalDao.find(pageCriteria);
	}

	@Override
	public Pagination<T> generalPage(PageCriteria pageCriteria) throws DaoException, InstantiationException,
			IllegalAccessException {
		return generalDao.generalPage(pageCriteria);
	}

}
