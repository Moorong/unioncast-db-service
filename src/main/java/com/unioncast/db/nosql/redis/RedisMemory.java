package com.unioncast.db.nosql.redis;

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.common.adx.model.AdxSspMedia;

import java.util.List;
import java.util.Map;

public interface RedisMemory {

    <T> String addByKey(String key, T object) throws Exception;

    <T> String add(T object) throws Exception;

    Object getObject(String key) throws Exception;

    <T> List<String> addList(List<T> list) throws Exception;


    Long deleteByKey(String key) throws Exception;

    Long batchDelete(List<String> strList) throws Exception;

    <T> Long addListKey(Map<String, T> map) throws Exception;


    Long batchAddAdxDspAdvertisersList(List<AdxDspAdvertisers> list) throws Exception;

    List<AdxDspAdvertisers> findAllByAdxDspAdvertisersIds(List<Long> ids) throws Exception;

    Long batchDeleteByAdxDspAdvertisersIds(List<Long> ids) throws Exception;


    Long batchAddAdxDspAdcreativeList(List<AdxDspAdcreative> list) throws Exception;

    List<AdxDspAdcreative> findAllByAdxDspAdcreativeIds(List<Long> ids) throws Exception;

    Long batchDeleteByAdxDspAdcreativeIds(List<Long> ids) throws Exception;

    Long batchAddAdxSspMediaList(List<AdxSspMedia> list) throws Exception;

    List<AdxSspMedia> findAllByAdxSspMediaIds(List<Long> ids) throws Exception;

    Long batchDeleteByAdxSspMediaIds(List<Long> ids) throws Exception;

    Long batchAddAdxSspAdvertisingPositionList(List<AdxSspAdvertisingPosition> list) throws Exception;

    List<AdxSspAdvertisingPosition> findAllByAdxSspAdvertisingPositionIds(List<Long> ids) throws Exception;

    Long batchDeleteByAdxSspAdvertisingPositionIds(List<Long> ids) throws Exception;


}
