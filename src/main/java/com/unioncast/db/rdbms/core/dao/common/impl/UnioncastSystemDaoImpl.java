package com.unioncast.db.rdbms.core.dao.common.impl;

import com.unioncast.common.page.PageCriteria;
import com.unioncast.common.page.Pagination;
import com.unioncast.common.user.model.UnioncastSystem;
import com.unioncast.db.rdbms.common.dao.CommonGeneralDao;
import com.unioncast.db.rdbms.common.dao.sqlBuild.SqlBuild;
import com.unioncast.db.rdbms.core.dao.common.UnioncastSystemDao;
import com.unioncast.db.rdbms.core.exception.DaoException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhe
 * @date 2017/2/15 16:44
 */
@Repository("unioncastSystemDao")
public class UnioncastSystemDaoImpl extends CommonGeneralDao<UnioncastSystem, Long> implements UnioncastSystemDao {

    private static String FIND_ALL = SqlBuild.select(UnioncastSystem.TABLE_NAME,
            UnioncastSystem.PROPERTIES);
    private static String DELETE_BY_ID = SqlBuild.delete(UnioncastSystem.TABLE_NAME);
    private static String COUNT_ALL = SqlBuild.countAll(UnioncastSystem.TABLE_NAME);

    @Override
    public UnioncastSystem[] find(Long id) throws DaoException {
        List<UnioncastSystem> list = new ArrayList<>();
        if (id != null) {
            list.add(jdbcTemplate.queryForObject(FIND_ALL + " where id = ?",
                    new UnioncastSystemDaoImpl.UnioncastSystemRowMapper(), id));
        } else {
            list = jdbcTemplate.query(FIND_ALL, new UnioncastSystemDaoImpl.UnioncastSystemRowMapper());
        }

        return list.toArray(new UnioncastSystem[]{});
    }

    @Override
    public UnioncastSystem[] findT(UnioncastSystem s) throws DaoException, IllegalAccessException {
        List<UnioncastSystem> list = new ArrayList<>();
        if (s != null) {
            UnioncastSystem[] sspOrders = find(s, new UnioncastSystemRowMapper(), UnioncastSystem.class);
            return sspOrders;
        } else {
            list = jdbcTemplate.query(FIND_ALL, new UnioncastSystemRowMapper());
        }
        return list.toArray(new UnioncastSystem[]{});
    }


    @Override
    public Pagination<UnioncastSystem> page(PageCriteria pageCriteria, Long userId)
            throws DaoException {
        pageCriteria.setEntityClass(UnioncastSystem.class);
        return page(pageCriteria, FIND_ALL, COUNT_ALL, new UnioncastSystemDaoImpl.UnioncastSystemRowMapper(), "account_id", userId);
    }

    @Override
    public int deleteById(Long id) throws DaoException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public int batchDelete(Long[] ids) throws DaoException {
        return delete(UnioncastSystem.class, ids);
    }

    public static final class UnioncastSystemRowMapper implements RowMapper<UnioncastSystem> {
        @Override
        public UnioncastSystem mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new UnioncastSystem(rs.getLong("id"), rs.getString("system_name"), rs.getTimestamp("update_time"),
                    rs.getTimestamp("create_time"), rs.getString("remark"));
        }
    }


}
