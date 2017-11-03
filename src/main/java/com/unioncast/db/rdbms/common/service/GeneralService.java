package com.unioncast.db.rdbms.common.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.core.exception.DaoException;

/**
 * service层封装
 * 
 * @author liutiejun
 * @date 2015-4-28 下午4:39:50
 * 
 * @param <T>
 * @param <ID>
 */
public interface GeneralService<T extends Serializable, ID extends Serializable> {

	/**
	 * 新增
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:29:51
	 * 
	 * @param entity
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 新增
	 * 
	 * @author liutiejun
	 * @date 2015-4-23 上午10:49:28
	 * 
	 * @param entities
	 * @throws DaoException
	 */
	public void save(List<T> entities) throws DaoException;

	/**
	 * 新增或者更新
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:34:47
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	public void saveOrUpdate(T entity) throws DaoException;

	/**
	 * 新增或者更新
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:34:58
	 * 
	 * @param entities
	 * @throws DaoException
	 */
	public void saveOrUpdate(List<T> entities) throws DaoException;

	/**
	 * 删除
	 * 
	 * @author liutiejun
	 * @date 2016年8月30日 下午10:15:38
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public int deleteById(ID id) throws DaoException;

	/**
	 * 删除
	 * 
	 * @author liutiejun
	 * @date 2016年8月30日 下午10:16:44
	 *
	 * @param idList
	 * @return
	 * @throws DaoException
	 */
	public int deleteById(List<ID> idList) throws DaoException;

	/**
	 * 删除
	 * 
	 * @author liutiejun
	 * @date 2016年8月30日 下午10:16:55
	 *
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	public int deleteById(ID[] ids) throws DaoException;

	/**
	 * 删除
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:30:04
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	public void delete(T entity) throws DaoException;

	/**
	 * 删除
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:30:54
	 * 
	 * @param entities
	 * @throws DaoException
	 */
	public void delete(List<T> entities) throws DaoException;

	/**
	 * 更新，更新所有的属性
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:32:30
	 * 
	 * @param entity
	 * @throws DaoException
	 */
	public void update(T entity) throws DaoException;

	/**
	 * 更新
	 * 
	 * @author liutiejun
	 * @date 2016年3月1日 下午2:54:51
	 *
	 * @param entity
	 * @return 更新了多少数据
	 */
	public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 更新
	 * 
	 * @author liutiejun
	 * @date 2015-4-23 上午10:51:30
	 * 
	 * @param entities
	 * @throws DaoException
	 */
	public void update(List<T> entities) throws DaoException;

	/**
	 * 更新，更新非空属性
	 * 
	 * @author liutiejun
	 * @date 2015年6月3日 下午1:03:25
	 *
	 * @param entity
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 根据ID查询
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:37:08
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public T findById(ID id) throws DaoException;

	/**
	 * 根据String查询
	 * 
	 * @author zhangzhe
	 * @date 2016年10月8日下午3:20:59
	 * 
	 * @param str
	 * @return
	 * @throws DaoException
	 * 
	 */
	public T findByString(String str) throws DaoException;

	/**
	 * 根据ID查询
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:37:17
	 * 
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	public T[] findById(List<ID> ids) throws DaoException;

	/**
	 * 通过systemId查找
	 * 
	 * @author 刘蓉
	 * @date 2016年10月9日 下午5:45:59
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public T[] findBySystemId(Long id) throws DaoException;

	/**
	 * 根据数据更新时间段查询
	 * 
	 * @author liutiejun
	 * @date 2015年5月19日 上午11:39:20
	 *
	 * @param startTime
	 *            ：包含开始时间
	 * @param endTime
	 *            ：不包含结束时间
	 * @return
	 * @throws DaoException
	 */
	public T[] findByUpdateTime(Date startTime, Date endTime) throws DaoException;

	/**
	 * 分页查询
	 * 
	 * @author liutiejun
	 * @date 2015年5月19日 上午11:39:32
	 *
	 * @param startTime
	 *            ：包含开始时间
	 * @param endTime
	 *            ：不包含结束时间
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DaoException
	 */
	public T[] findByUpdateTime(Date startTime, Date endTime, int firstResult, int maxResults) throws DaoException;

	/**
	 * 查询所有数据
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午4:38:19
	 * 
	 * @return
	 * @throws DaoException
	 */
	public T[] findAll() throws DaoException;

	/**
	 * 分页查询
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午5:58:24
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DaoException
	 */
	public T[] findAll(int firstResult, int maxResults) throws DaoException;

	/**
	 * 分页查询
	 * 
	 * @author liutiejun
	 * @date 2015年6月17日 下午3:04:37
	 *
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws DaoException
	 */
	public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException;

	/**
	 * 获取所有数据的数量(为了分页使用的)
	 * 
	 * @author liutiejun
	 * @date 2015-4-22 下午6:00:13
	 * 
	 * @return
	 * @throws DaoException
	 */
	public int countAll() throws DaoException;

	/**
	 * 根据更新时间进行统计
	 * 
	 * @author liutiejun
	 * @date 2015年5月19日 上午11:40:11
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws DaoException
	 */
	public int countByUpdateTime(Date startTime, Date endTime) throws DaoException;

	/**
	 * 条件分页查询
	 * 
	 * @author 琚超超
	 * @date 2016年10月8日 下午2:24:08
	 *
	 * @param qualification
	 * @param currentPage
	 * @param pageSize
	 * @throws DaoException
	 */
	Pagination<T> page(T entity, Integer currentPage, Integer pageSize) throws DaoException;

	/**
	 * 批量增加
	 * 
	 * @author 琚超超
	 * @date 2016年10月9日 上午9:15:31
	 *
	 * @param entity
	 * @return a list of id
	 * @throws DaoException
	 */
	List<Long> batchAdd(List<T> entitys) throws DaoException;

	/**
	 * 批量删除
	 * 
	 * @author 琚超超
	 * @date 2016年10月9日 上午10:54:18
	 *
	 * @param ids
	 * @return 删除了多少条
	 */
	int batchDelete(List<Long> ids) throws DaoException;

	/**
	 * 批量增加
	 *
	 * @author zhangzhe
	 * @date 2016年11月3日 上午11:15:31
	 *
	 * @param entitys
	 * @return a list of id
	 * @throws DaoException
	 */
	Long[] batchAdd(T[] entitys) throws DaoException;

	/**
	 * 批量删除
	 *
	 * @author zhangzhe
	 * @date 2016年11月3日 上午11:15:31
	 *
	 * @param ids
	 * @return 删除了多少条
	 * @throws DaoException
	 */
	int batchDelete(Long[] ids) throws DaoException;

	/**
	 * 条件分页查询加强版
	 *
	 * @author zhangzhe
	 * @date 2016年11月4日 下午2:24:08
	 *
	 * @param pageCriteria
	 * @throws DaoException
	 */
	Pagination<T> page(PageCriteria pageCriteria) throws DaoException;

	/**
	 * 根据ID查询
	 *
	 * @author zhanzhe
	 * @date 2016-11-7 下午4:37:17
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	T[] find(ID id) throws DaoException;
    T[] findT(T t) throws DaoException, IllegalAccessException;
	/**
	 * 根据输入的条件进行查找(不分页)
	 * 
	 * @author juchaochao
	 * @date 2016年11月9日 下午1:07:09
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	// T[] find(PageCriteria1<T> pageCriteria) throws DaoException, InstantiationException, IllegalAccessException;

	/**
	 * 条件分页查询
	 * 
	 * @author juchaochao
	 * @date 2016年11月9日 下午7:59:47
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	// Pagination<T> page(PageCriteria1<T> pageCriteria)
	// throws DaoException, InstantiationException, IllegalAccessException;

	/**
	 * 新增（适用于单个和批量）
	 * 
	 * @author juchaochao
	 * @date 2016年11月10日 上午9:53:20
	 *
	 * @param sysDics
	 * @return
	 * @throws SQLException
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	Long[] add(T[] entitys) throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException;

	/**
	 * 删除（适用于单个和批量）
	 * 
	 * @author juchaochao
	 * @date 2016年11月10日 下午1:58:42
	 *
	 * @param ids
	 * @return 返回删除了多少条
	 * @throws DaoException
	 */
	int delete(Class<?> clazz, ID[] ids) throws DaoException;

	/**
	 * 修改（适用于单个和批量）
	 * 
	 * @author juchaochao
	 * @date 2016年11月11日 上午10:13:36
	 *
	 * @param entity
	 * @return 更新了多少条
	 * @throws DaoException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	int updateNotNullField(T[] entities)
			throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException;

	/**
	 * 根据输入的条件进行查找(不分页)
	 * 
	 * @author juchaochao
	 * @date 2016年11月8日 下午4:54:32
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException;

	/**
	 * 条件分页查询
	 * 
	 * @author juchaochao
	 * @date 2016年11月9日 下午6:54:31
	 *
	 * @param pageCriteria
	 * @return
	 * @throws DaoException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	Pagination<T> generalPage(PageCriteria pageCriteria)
			throws DaoException, InstantiationException, IllegalAccessException;
}
