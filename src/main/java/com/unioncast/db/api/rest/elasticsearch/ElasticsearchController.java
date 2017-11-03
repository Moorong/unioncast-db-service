package com.unioncast.db.api.rest.elasticsearch;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.db.nosql.elasticsearch.ESUtils;

@RestController
@RequestMapping("/elasticsearch")
public class ElasticsearchController {

	private static final Logger LOG = LogManager.getLogger(ElasticsearchController.class);
	private static final String index = "test";
	private static final String type = "test01";
	
	/**
	 * 创建索引库
	 * @author zylei
	 * @data   2016年11月1日 下午4:23:43
	 * @param index
	 */
	@RequestMapping(value = "/createIndex", method = RequestMethod.POST)
	public void createIndex(@RequestParam String index) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
        // 创建索引库
		if (isIndexExists(index)) {
			LOG.info("索引库" + index + "已存在！");
		} else {
		    CreateIndexRequest createIndexRequest = new CreateIndexRequest("indexName");
		    CreateIndexResponse createIndexResponse = client.admin().indices().create(createIndexRequest)
		            .actionGet();
		    if (createIndexResponse.isAcknowledged()) {
		    	LOG.info("创建索引库成功！");
		    } else {
		    	LOG.info("创建索引库失败！");
		    }
		}
    }

	/**
	 * 单个创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月31日 上午11:25:43
	 * @param object
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@RequestBody Object object) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		String jsonSource = ESUtils.object2Json(object);
		LOG.info("object:{}", jsonSource);
		ESUtils es = new ESUtils();
		es.add(index, type, jsonSource);
		client.close();
	}

	/**
	 * 批量创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月31日 上午11:59:24
	 * @param objects
	 */
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	public void batchAdd(@RequestBody List<Object> objects) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		ESUtils es = new ESUtils();
		es.batchAdd(index, type, objects);
		client.close();
	}

	/**
	 * 更新索引 如果索引不存在则创建新的索引
	 * 
	 * @author zylei
	 * @data 2016年11月1日 下午2:41:37
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update() {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		// upsert
		// 如果文档不存在则创建新的索引(文档存在:index操作无效、update有效,文档不存在:update无效、做出index操作)
		try {
			// 先构建一个IndexRequest
			IndexRequest indexRequest = new IndexRequest(index, type, "1").source(XContentFactory.jsonBuilder()
					.startObject().field("title", "Git安装10").field("content", "学习目标 git。。。10").endObject());
			// 再构建一个UpdateRequest，并用IndexRequest关联
			UpdateRequest updateRequest = new UpdateRequest(index, type, "1").doc(XContentFactory.jsonBuilder()
					.startObject().field("title", "Git安装").field("content", "学习目标 git。。。").endObject())
					.upsert(indexRequest);
			client.update(updateRequest).get();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		client.close();
	}

	/**
	 * 更新数据
	 * 
	 * @author zylei
	 * @data 2016年11月1日 下午2:35:32
	 */
	@RequestMapping(value = "/update1", method = RequestMethod.POST)
	public void update1() {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		try {
			// 创建一个UpdateRequest,然后将其发送给client.
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index(index);
			updateRequest.type(type);
			updateRequest.id("22");
			updateRequest.doc(XContentFactory.jsonBuilder().startObject().field("tel", "6666").endObject());
			client.update(updateRequest).get();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		client.close();
	}

	/**
	 * 更新数据
	 * 
	 * @author zylei
	 * @data 2016年11月1日 下午2:35:44
	 */
	@RequestMapping(value = "/update2", method = RequestMethod.POST)
	public void update2() {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		// prepareUpdate() 使用doc更新索引
		try {
			client.prepareUpdate(index, type, "1")
					.setDoc(XContentFactory.jsonBuilder().startObject().field("tel", "6666").endObject()).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.close();
	}

	/**
	 * 更新数据
	 * 
	 * @author zylei
	 * @data 2016年11月1日 下午2:35:56
	 */
	@RequestMapping(value = "/update3", method = RequestMethod.POST)
	public void update3() {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		// 增加新的字段
		try {
			UpdateRequest updateRequest = new UpdateRequest(index, type, "1")
					.doc(XContentFactory.jsonBuilder().startObject().field("commet", "0").endObject());
			client.update(updateRequest).get();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		client.close();
	}

	/**
	 * 查询索引
	 * 
	 * @author zylei
	 * @data 2016年11月1日 上午11:46:43
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void search(@RequestParam String startTime, @RequestParam String endTime) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		// 查询tel字段中包含888关键字的文档
		QueryBuilder qb1 = QueryBuilders.termQuery("tel", "888");
		// 查询tel字段或age字段中包含888关键字的文档
		QueryBuilder qb2 = QueryBuilders.multiMatchQuery("888", "tel", "age");
		// 组合查询 多条件查询
		QueryBuilder qb3 = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("tel", "666"))
				.mustNot(QueryBuilders.termQuery("age", "20")).should(QueryBuilders.termQuery("name", "测试1"));
		// 区间查询 可以把 queryBuilder拼接的条件放入组合函数boolQuery中
		// QueryBuilder qb4 = QueryBuilders.filteredQuery(qb1,
		// QueryBuilders.rangeQuery("data")
		// .from(startTime)
		// .to(endTime)
		// .includeUpper(false));

		ESUtils es = new ESUtils();
		es.search(index, type, qb1);
		client.close();
	}

	/**
	 * 根据索引id删除索引
	 * @author zylei
	 * @data   2016年11月1日 下午3:57:22
	 * @param id
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam String id) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		DeleteResponse deleteResponse = client.prepareDelete(index, type, id).execute()
		        .actionGet();
		if (deleteResponse.isFound()) {
			LOG.info("删除成功！");
		} else {
			LOG.info("删除失败！");
		}
	}
	
	/**
	 * 删除索引库（一般不建议）
	 * @author zylei
	 * @data   2016年11月1日 下午4:15:01
	 * @param index
	 */
	@RequestMapping(value = "/deleteIndex", method = RequestMethod.POST)
	public void deleteIndex(@RequestParam String index) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
        if (!isIndexExists(index)) {
			LOG.warn(index + "不存在！");
		} else {
		    DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index)
		            .execute().actionGet();
		    if (deleteIndexResponse.isAcknowledged()) {
		    	LOG.info("删除索引库：" + index + "成功！");
		    }else{
		    	LOG.info("删除索引库：" + index + "失败！");
		    }
		}
    }

	/**
	 * 根据索引名称判断索引库是否存在
	 * @author zylei
	 * @data   2016年11月1日 下午4:06:54
	 * @param index
	 * @return
	 */
	private boolean isIndexExists(String index) {
		Client client = ESUtils.client;
		LOG.info("client:{}", client);
		boolean flag = false;
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);

		IndicesExistsResponse inExistsResponse = client.admin().indices()
		        .exists(inExistsRequest).actionGet();
		if (inExistsResponse.isExists()) {
		    flag = true;
		} else {
		    flag = false;
		}
        return flag;
	}

//	public static void main(String[] args) throws Exception {
//		Client client = ESUtils.client;
//		LOG.info("client:{}", client);
//		LOG.trace("TRACE: " + 111);
//		LOG.debug("DEBUG: " + 222);
//		LOG.info("INFO: " + 333);
//		LOG.warn("WARN: " + 444);
//		LOG.error("ERROR: " + 555);
//		LOG.fatal("FATAL: " + 666);
//		client.close();
//	}

}
