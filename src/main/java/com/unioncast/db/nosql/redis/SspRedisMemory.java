package com.unioncast.db.nosql.redis;

import java.util.List;
import java.util.Map;

import com.unioncast.common.ssp.model.*;
import com.unioncast.common.user.model.User;

public interface SspRedisMemory {

	Long batchAddSspAdvertiser(SspAdvertiser[] list) throws Exception;

	SspAdvertiser[] findAllBySspAdvertiserIds(Long[] ids) throws Exception;

	SspAdvertiser findSspAdvertiserById(Long id) throws Exception;

	Long batchDeleteBySspAdvertiserIds(Long[] ids) throws Exception;

	Long batchAddSspOrder(SspOrder[] list) throws Exception;

	SspOrder[] findAllBySspOrderIds(Long[] ids) throws Exception;

	SspOrder findSspOrderById(Long id) throws Exception;

	Long batchDeleteBySspOrderIds(Long[] ids) throws Exception;

	Long batchAddSspPlan(SspPlan[] list) throws Exception;

	SspPlan[] findAllBySspPlanIds(Long[] ids) throws Exception;

	SspPlan findSspPlanById(Long id) throws Exception;

	Long batchDeleteBySspPlanIds(Long[] ids) throws Exception;

	Long batchAddSspCreative(SspCreative[] list) throws Exception;

	SspCreative[] findAllBySspCreativeIds(Long[] ids) throws Exception;

	SspCreative findSspCreativeById(Long id) throws Exception;

	Long batchDeleteBySspCreativeIds(Long[] ids) throws Exception;

	SspAdvertiser[] findSspAdvertiser() throws Exception;

	SspOrder[] findSspOrder() throws Exception;

	SspPlan[] findSspPlan() throws Exception;

	SspCreative[] findSspCreative() throws Exception;

	Long batchAddSspPlanCreativeRelation(SspPlanCreativeRelation[] list)
			throws Exception;

	SspPlanCreativeRelation[] findAllBySspPlanCreativeRelationIds(Long[] ids)
			throws Exception;

	SspPlanCreativeRelation findSspPlanCreativeRelationById(Long id)
			throws Exception;

	Long batchDeleteBySspPlanCreativeRelationIds(Long[] ids) throws Exception;

	SspPlanCreativeRelation[] findSspPlanCreativeRelation() throws Exception;

	Map<Long, SspAdvertiser> findAllAdvertiser() throws Exception;

	Map<Long, SspOrder> findAllOrder() throws Exception;

	Map<Long, SspPlan> findAllPlan() throws Exception;

	Map<Long, SspCreative> findAllCreative() throws Exception;

	Map<Long, List<Long>> findAllPlanCreativeIds() throws Exception;

	String batchAddSspPlanCreativeRelationByPlanId(Map<Long, String> map)
			throws Exception;

	Long batchAddCreativeDayConsume(SspCreativeReport[] list) throws Exception;

	Long batchDeleteCreativeDayConsumeByCreativeIds(Long[] ids) throws Exception;

	Map<Long, SspCreativeReport> findCreativeDayConsumeMap() throws Exception;

	void deleteAllCreativeDayConsume() throws Exception;

	List<Long> findByAdvIdOrders(Long id) throws Exception;

	void addByAdvIdOrders(Long id, List<Long> orderIds) throws Exception;

	Map<Long, List<Long>> findAllAdvertiserOrderIds() throws Exception;

	String updateEcpmRedis(Map<Long, Double> map);

	Map<Long, Double> findCreativeEcpmMap()throws Exception;

	Long updateRedisConsume(RedisConsume redisConsume)  throws Exception;

	Map<Long, SspDayOrHourReport> findOrderConsumeMap()  throws Exception;

	Map<Long, SspDayOrHourReport> findPlanConsumeMap()  throws Exception;
    String findPlanCreativeRelationByPlanId(Long planId);

	Long batchAddSspAppInfo(SspAppInfo[] list) throws Exception;

	Long batchDeleteBySspAppInfoIds(Long[] ids) throws Exception;

	Long batchAddSspAdPositionInfo(SspAdPositionInfo[] list) throws Exception;

	Long batchDeleteBySspAdPositionInfoIds(Long[] ids) throws Exception;

	Long batchAddUserInfo(User[] list) throws Exception;

	Long batchDeleteByUserIds(Long[] ids) throws Exception;
}
