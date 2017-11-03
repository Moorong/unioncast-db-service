package com.unioncast.db.rdbms.common.dao;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DAO基类，采用jdbcTemplate实现
 *
 * @param <T>
 * @param <ID>
 * @author juchaochao
 */
public interface GeneralDao<T extends Serializable, ID extends Serializable> {

    public String getEntityName();

    public Class<T> getEntityClass();

    public String getPkName();

    /**
     * 新增
     *
     * @param entity
     * @return
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author liutiejun
     * @date 2015-4-22 下午4:29:51
     */
    public ID save(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

    /**
     * 新增
     *
     * @param entities
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 上午10:49:28
     */
    public void save(List<T> entities) throws DaoException;

    /**
     * 新增或者更新
     *
     * @param entity
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:34:47
     */
    public void saveOrUpdate(T entity) throws DaoException;

    /**
     * 新增或者更新
     *
     * @param entities
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:34:58
     */
    public void saveOrUpdate(List<T> entities) throws DaoException;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2016年8月30日 下午10:14:56
     */
    public int deleteById(ID id) throws DaoException;

    /**
     * 删除
     *
     * @param idList
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2016年8月30日 下午10:14:48
     */
    public int deleteById(List<ID> idList) throws DaoException;

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2016年8月30日 下午10:14:35
     */
    public int deleteById(ID[] ids) throws DaoException;

    /**
     * 删除
     *
     * @param entity
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:30:04
     */
    public void delete(T entity) throws DaoException;

    /**
     * 删除
     *
     * @param entities
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:30:54
     */
    public void delete(List<T> entities) throws DaoException;

    /**
     * 更新，更新所有的属性
     *
     * @param entity
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:32:30
     */
    public void update(T entity) throws DaoException;

    /**
     * 更新
     *
     * @param entities
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 上午10:51:30
     */
    public void update(List<T> entities) throws DaoException;

    /**
     * 更新
     *
     * @param hql
     * @param values
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 下午3:14:00
     */
    public int update(String hql) throws DaoException;

    /**
     * @param entity
     * @return
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author 刘蓉
     * @date 2016年10月10日 下午5:27:13
     */
    public int updateAndReturnNum(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

    /**
     * 更新
     *
     * @param hql
     * @param values
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 下午3:14:18
     */
    public int update(String hql, Map<String, Object> valuesMap) throws DaoException;

    /**
     * 更新，更新非空属性
     *
     * @param entity
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author liutiejun
     * @date 2015年6月3日 上午11:24:28
     */
    public void updateNotNullField(T entity) throws DaoException, IllegalArgumentException, IllegalAccessException;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:37:08
     */
    public T findById(ID id) throws DaoException;

    /**
     * 根据String查询
     *
     * @param str
     * @return
     * @throws DaoException
     * @author zhangzhe
     * @date 2016年10月8日下午3:20:59
     */
    public T findByString(String str) throws DaoException;

    /**
     * 根据ID查询
     *
     * @param ids
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:37:17
     */
    public T[] findById(List<ID> ids) throws DaoException;

    /**
     * 按属性查找对象列表
     *
     * @param property
     * @param value
     * @return
     * @author liutiejun
     * @date 2016年1月27日 下午4:10:29
     */
    public List<T> findByProperty(String property, Object value);

    /**
     * 按属性查找对象列表
     *
     * @param property
     * @param value
     * @param maxResults
     * @return
     * @author liutiejun
     * @date 2016年1月27日 下午4:10:38
     */
    public List<T> findByProperty(String property, Object value, int maxResults);

    /**
     * 按属性查找唯一对象
     *
     * @param property
     * @param value
     * @return
     * @author liutiejun
     * @date 2016年1月27日 下午4:10:50
     */
    public T findUniqueByProperty(String property, Object value);

    /**
     * 查询所有数据
     *
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午4:38:19
     */
    public T[] findAll() throws DaoException;

    /**
     * 分页查询
     *
     * @param firstResult
     * @param maxResults
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午5:58:24
     */
    public T[] findAll(int firstResult, int maxResults) throws DaoException;

    public T[] find(String hql) throws DaoException;

    /**
     * 条件查询
     *
     * @param hql
     * @param values
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 下午5:35:52
     */
    public T[] find(String hql, Map<String, Object> valuesMap) throws DaoException;

    /**
     * 分页查询
     *
     * @param hql
     * @param firstResult
     * @param maxResults
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-5-12 下午12:00:56
     */
    public T[] find(String hql, int firstResult, int maxResults) throws DaoException;

    /**
     * 分页查询
     *
     * @param hql
     * @param firstResult
     * @param maxResults
     * @param values
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-23 下午6:09:09
     */
    public T[] find(String hql, int firstResult, int maxResults, Map<String, Object> valuesMap) throws DaoException;

    /**
     * 分页查询
     *
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015年6月17日 下午3:04:37
     */
    public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException;

    /**
     * 分页查询
     *
     * @param hql
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015年6月17日 下午3:07:15
     */
    public Pagination<T> pagination(String hql, Integer currentPageNo, Integer pageSize) throws DaoException;

    /**
     * 分页查询
     *
     * @param hql
     * @param currentPageNo
     * @param pageSize
     * @param valuesMap
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015年6月17日 下午3:08:53
     */
    public Pagination<T> pagination(String hql, Integer currentPageNo, Integer pageSize, Map<String, Object> valuesMap)
            throws DaoException;

    /**
     * 获取所有数据的数量
     *
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-4-22 下午6:00:13
     */
    public int countAll() throws DaoException;

    /**
     * 获得查询结果的数量
     *
     * @param hql
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015年6月3日 下午2:59:43
     */
    public int count(String hql) throws DaoException;

    /**
     * 获得查询结果的数量
     *
     * @param hql
     * @param values
     * @return
     * @throws DaoException
     * @author liutiejun
     * @date 2015-5-12 上午10:37:02
     */
    public int count(String hql, Map<String, Object> valuesMap) throws DaoException;

    /**
     * 条件分页查询
     *
     * @param entity
     * @param currentPage
     * @param pageSize
     * @return
     * @throws DaoException
     * @author 琚超超
     * @date 2016年10月8日 下午2:26:09
     */
    Pagination<T> page(T entity, Integer currentPage, Integer pageSize) throws DaoException;

    /**
     * 批量添加
     *
     * @param entities
     * @return 新增记录的id
     * @throws DaoException
     * @author 琚超超
     * @date 2016年10月9日 上午10:56:05
     */
    List<Long> batchAdd(List<T> entities) throws DaoException;

    /**
     * 批量删除
     *
     * @param ids
     * @return 删除了多少条
     * @author 琚超超
     * @date 2016年10月9日 上午10:56:29
     */
    int batchDelete(List<Long> ids);

    /**
     * 批量增加
     *
     * @param entities
     * @return a list of id
     * @throws DaoException
     * @author zhangzhe
     * @date 2016年11月3日 上午11:15:31
     */
    Long[] batchAdd(T[] entities) throws DaoException;

    /**
     * 批量删除
     *
     * @param ids
     * @return 删除了多少条
     * @throws DaoException
     * @author zhangzhe
     * @date 2016年11月3日 上午11:15:31
     */
    int batchDelete(Long[] ids) throws DaoException;

    /**
     * 条件分页查询加强版
     *
     * @param pageCriteria
     * @throws DaoException
     * @author zhangzhe
     * @date 2016年11月4日 下午2:24:08
     */
    Pagination<T> page(PageCriteria pageCriteria) throws DaoException;


    Pagination<T> page(PageCriteria pageCriteria, Long userId) throws DaoException ;

    Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper) throws DaoException;

    Pagination<T> page(PageCriteria pageCriteria, String findAllSql, String countAllSql, RowMapper<T> rowMapper, String userFieldName, Long userId) throws DaoException;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws DaoException
     * @author zhanzhe
     * @date 2016-11-7 下午4:37:17
     */
    T[] find(ID id) throws DaoException;
    T[] findT(T id) throws DaoException, IllegalAccessException;
    /**
     * 根据输入的条件进行查找(不分页)
     *
     * @param id
     * @return
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @author juchaochao
     * @date 2016年11月8日 下午4:54:32
     */
    T[] find(PageCriteria pageCriteria) throws DaoException, InstantiationException, IllegalAccessException;

    /**
     * 条件分页查询
     *
     * @param pageCriteria
     * @return
     * @throws DaoException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @author juchaochao
     * @date 2016年11月9日 下午6:54:31
     */
    Pagination<T> generalPage(PageCriteria pageCriteria)
            throws DaoException, InstantiationException, IllegalAccessException;

    /**
     * 增加（适用于单个和批量）
     *
     * @param entities
     * @return
     * @throws SQLException
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author juchaochao
     * @date 2016年11月10日 上午9:58:52
     */
    Long[] add(T[] entities) throws SQLException, DaoException, IllegalArgumentException, IllegalAccessException;

    /**
     * 删除（适用于单个和批量）
     *
     * @param clazz
     * @param ids
     * @return 删除了多少条
     * @throws DaoException
     * @author juchaochao
     * @date 2016年11月10日 下午3:09:25
     */
    int delete(Class<?> clazz, ID[] ids) throws DaoException;

    /**
     * 修改（适用于单个和批量）
     *
     * @param entity
     * @return 更新了多少条
     * @throws DaoException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SQLException
     * @author juchaochao
     * @date 2016年11月11日 上午10:13:36
     */
    int updateNotNullField(T[] entities) throws DaoException, IllegalArgumentException, IllegalAccessException, SQLException;
}
