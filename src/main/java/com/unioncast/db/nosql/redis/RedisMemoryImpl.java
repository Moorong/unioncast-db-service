package com.unioncast.db.nosql.redis;

import com.unioncast.common.adx.model.AdxDspAdcreative;
import com.unioncast.common.adx.model.AdxDspAdvertisers;
import com.unioncast.common.adx.model.AdxSspAdvertisingPosition;
import com.unioncast.common.adx.model.AdxSspMedia;
import com.unioncast.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.Map.Entry;

@Repository
public class RedisMemoryImpl implements RedisMemory {
    public static final String ADXDSPADVERTISERS = "adxdspadvertisers:";
    public static final String ADXDSPADCREATIVE = "adxdspadcreative:";
    public static final String ADXSSPMEDIA = "adxsspmedia:";
    public static final String ADXSSPADVERTISINGPOSITION = "adxsspadvertisingposition:";
    @Autowired
    JedisCluster jedisCluster;

    public <T> String addByKey(String key, T object) throws Exception {
        String object2JsonString = JsonUtil.object2JsonString(object);
        String set = jedisCluster.set(key, object2JsonString);
        return set;
    }

    public <T> String add(T object) throws Exception {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String object2JsonString = JsonUtil.object2JsonString(object);
        jedisCluster.set(uuid, object2JsonString);
        return uuid;
    }

    public Object getObject(String key) throws Exception {
        String string = jedisCluster.get(key);
        Object json2Object = JsonUtil.json2Object(string, Object.class);
        return json2Object;
    }

    public <T> List<String> addList(List<T> list) throws Exception {
        List<String> sum = new ArrayList<>(70);
        String uuid = null;
        String str = null;
        for (int i = 0; i < list.size(); i++) {
            uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            str = JsonUtil.object2JsonString(list.get(i));
            jedisCluster.set(uuid, str);
            sum.set(i, uuid);
        }
        return sum;
    }

    public <T> Long addListKey(Map<String, T> map) throws Exception {
        Long sum = (long) 0;
        String str = null;
        Iterator<Entry<String, T>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, T> entry = (Entry<String, T>) iterator.next();
            String key = entry.getKey();
            T object = entry.getValue();
            str = JsonUtil.object2JsonString(object);
            jedisCluster.set(key, str);
            sum = sum + 1;
        }

        return sum;
    }


    public Long deleteByKey(String key) throws Exception {
        Long del = jedisCluster.del(key);

        return del;
    }

    public Long batchDelete(List<String> strList) throws Exception {
        long sum = 0;
        for (int i = 0; i < strList.size(); i++) {
            Long del = jedisCluster.del(strList.get(i));
            sum = sum + del;
        }

        return sum;
    }

    @Override
    public Long batchAddAdxDspAdvertisersList(List<AdxDspAdvertisers> list) throws Exception {
        long sum = 0;
        String key = null;
        String value = null;
        for (int i = 0; i < list.size(); i++) {
            key = ADXDSPADVERTISERS + list.get(i).getId();
            value = JsonUtil.object2JsonString(list.get(i));
            jedisCluster.set(key, value);
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public List<AdxDspAdvertisers> findAllByAdxDspAdvertisersIds(List<Long> ids) throws Exception {
        List<AdxDspAdvertisers> list = new ArrayList<>();
        String key = null;
        String value = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXDSPADVERTISERS + ids.get(i);
            value = jedisCluster.get(key);

            AdxDspAdvertisers adxDspAdvertisers = JsonUtil.json2Object(value, AdxDspAdvertisers.class);

            list.add(adxDspAdvertisers);
        }

        return list;

    }

    @Override
    public Long batchDeleteByAdxDspAdvertisersIds(List<Long> ids) throws Exception {
        long sum = 0;
        String key = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXDSPADVERTISERS + ids.get(i);
            sum = sum + jedisCluster.del(key);
        }

        return sum;

    }


    @Override
    public Long batchAddAdxDspAdcreativeList(List<AdxDspAdcreative> list) throws Exception {
        long sum = 0;
        String key = null;
        String value = null;
        for (int i = 0; i < list.size(); i++) {
            key = ADXDSPADCREATIVE + list.get(i).getId();
            value = JsonUtil.object2JsonString(list.get(i));
            jedisCluster.set(key, value);
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public List<AdxDspAdcreative> findAllByAdxDspAdcreativeIds(List<Long> ids) throws Exception {
        List<AdxDspAdcreative> list = new ArrayList<>();
        String key = null;
        String value = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXDSPADCREATIVE + ids.get(i);
            value = jedisCluster.get(key);

            AdxDspAdcreative adxDspAdvertisers = JsonUtil.json2Object(value, AdxDspAdcreative.class);

            list.add(adxDspAdvertisers);
        }

        return list;

    }

    @Override
    public Long batchDeleteByAdxDspAdcreativeIds(List<Long> ids) throws Exception {
        long sum = 0;
        String key = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXDSPADCREATIVE + ids.get(i);
            sum = sum + jedisCluster.del(key);
        }

        return sum;

    }


    @Override
    public Long batchAddAdxSspMediaList(List<AdxSspMedia> list) throws Exception {
        long sum = 0;
        String key = null;
        String value = null;
        for (int i = 0; i < list.size(); i++) {
            key = ADXSSPMEDIA + list.get(i).getId();
            value = JsonUtil.object2JsonString(list.get(i));
            jedisCluster.set(key, value);
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public List<AdxSspMedia> findAllByAdxSspMediaIds(List<Long> ids) throws Exception {
        List<AdxSspMedia> list = new ArrayList<>();
        String key = null;
        String value = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXSSPMEDIA + ids.get(i);
            value = jedisCluster.get(key);

            AdxSspMedia adxDspAdvertisers = JsonUtil.json2Object(value, AdxSspMedia.class);

            list.add(adxDspAdvertisers);
        }

        return list;

    }

    @Override
    public Long batchDeleteByAdxSspMediaIds(List<Long> ids) throws Exception {
        long sum = 0;
        String key = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXSSPMEDIA + ids.get(i);
            sum = sum + jedisCluster.del(key);
        }

        return sum;

    }


    @Override
    public Long batchAddAdxSspAdvertisingPositionList(List<AdxSspAdvertisingPosition> list) throws Exception {
        long sum = 0;
        String key = null;
        String value = null;
        for (int i = 0; i < list.size(); i++) {
            key = ADXSSPADVERTISINGPOSITION + list.get(i).getId();
            value = JsonUtil.object2JsonString(list.get(i));
            jedisCluster.set(key, value);
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public List<AdxSspAdvertisingPosition> findAllByAdxSspAdvertisingPositionIds(List<Long> ids) throws Exception {
        List<AdxSspAdvertisingPosition> list = new ArrayList<>();
        String key = null;
        String value = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXSSPADVERTISINGPOSITION + ids.get(i);
            value = jedisCluster.get(key);

            AdxSspAdvertisingPosition adxDspAdvertisers = JsonUtil.json2Object(value, AdxSspAdvertisingPosition.class);

            list.add(adxDspAdvertisers);
        }

        return list;

    }

    @Override
    public Long batchDeleteByAdxSspAdvertisingPositionIds(List<Long> ids) throws Exception {
        long sum = 0;
        String key = null;
        for (int i = 0; i < ids.size(); i++) {
            key = ADXSSPADVERTISINGPOSITION + ids.get(i);
            sum = sum + jedisCluster.del(key);
        }

        return sum;

    }


}

// package com.unioncast.db.rdbms.core.dao.commonDBDao.impl;
//
// import java.io.Exception;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
// import java.util.UUID;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Repository;
//
// import com.unioncast.common.util.JsonUtil;
// import com.unioncast.db.rdbms.core.dao.commonDBDao.RedisDao;
//
// import redis.clients.jedis.Jedis;
// import redis.clients.jedis.JedisPool;
//
// @Repository("redisDao")
// public class RedisDaoImpl implements RedisDao {
// @Autowired
// JedisPool jedisPool;
//
// @Override
// public <T> String addByKey(String key, T object) throws Exception {
// Jedis jedis = jedisPool.getResource();
// String object2JsonString = JsonUtil.object2JsonString(object);
// String set = jedis.set(key, object2JsonString);
// return set;
// }
//
// @Override
// public <T> String add(T object) throws Exception {
// Jedis jedis = jedisPool.getResource();
// String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
// String object2JsonString = JsonUtil.object2JsonString(object);
// jedis.set(uuid, object2JsonString);
// return uuid;
// }
//
// @Override
// public Object getObject(String key) throws Exception {
// Jedis jedis = jedisPool.getResource();
// String string = jedis.get(key);
// Object json2Object = JsonUtil.json2Object(string, Object.class);
// return json2Object;
// }
//
// @Override
// public <T> List<String> addList(List<T> list) throws Exception {
// Jedis jedis = jedisPool.getResource();
// List<String> sum = new ArrayList<>(70);
// String uuid = null;
// String str = null;
// for (int i = 0; i < list.size(); i++) {
// uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
// str = JsonUtil.object2JsonString(list.get(i));
// jedis.set(uuid, str);
// sum.set(i, uuid);
// }
// return sum;
// }
//
// @Override
// public <T> String addListKey(List<String> strList, List<T> list) throws
// Exception {
// return null;
// }
//
// @Override
// public <T> Long addListKey(Map<String, T> map) throws Exception {
// Jedis jedis = jedisPool.getResource();
// Long sum = (long) 0;
// String str = null;
// Iterator<Entry<String, T>> iterator = map.entrySet().iterator();
// while (iterator.hasNext()) {
// Entry<String, T> entry = (Entry<String, T>) iterator.next();
// String key = entry.getKey();
// T object = entry.getValue();
// str = JsonUtil.object2JsonString(object);
// jedis.set(key, str);
// sum = sum + 1;
// }
//
// return sum;
// }
//
// @Override
// public Long deleteByKey(String key) throws Exception {
// Jedis jedis = jedisPool.getResource();
// Long del = jedis.del(key);
//
// return del;
// }
//
// @Override
// public Long batchDelete(List<String> strList) throws Exception {
// Jedis jedis = jedisPool.getResource();
// Long sum = (long) 0;
// Long del = (long) 0;
// for (int i = 0; i < strList.size(); i++) {
// del = jedis.del(strList.get(i));
// sum = sum + del;
// }
//
// return sum;
// }
//
// }
