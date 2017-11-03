package com.unioncast.db.nosql.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ESUtils {

	private static final Logger LOG = LogManager.getLogger(ESUtils.class);

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Client client = getClient();

	public static String object2Json(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到访问es的客户端
	 * 
	 * @author zylei
	 * @data 2016年10月21日 下午6:39:09
	 * @return
	 */
	public static Client getClient() {
		Settings settings = Settings.settingsBuilder().put("client.transport.sniff", true)
				.put("cluster.name", "bigData-cluster").build();
		// ESSettings es = new ESSettings();
		// String address = es.getAddress();
		// Integer port = Integer.valueOf(es.getPort());

		try {
			client = TransportClient.builder().settings(settings).build().addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName("192.168.10.116"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 单个创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月27日 下午3:28:44
	 * @param jsonSource
	 */
	public void add(String index, String type, String jsonSource) {
		IndexResponse res = client.prepareIndex().setIndex(index).setType(type).setSource(jsonSource).execute()
				.actionGet();
		LOG.info("res:{}", res.getVersion());
	}

	/**
	 * 批量创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月31日 上午11:57:37
	 * @param index
	 * @param type
	 * @param objects
	 */
	public void batchAdd(String index, String type, List<Object> objects) {
		BulkRequestBuilder bulkRequest = client.prepareBulk().setRefresh(true);
		for (Object object : objects) {
			String jsonSource = object2Json(object);
			IndexRequestBuilder irb = client.prepareIndex(index, type).setSource(jsonSource);
			bulkRequest.add(irb);
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		LOG.info("res:{}", bulkResponse.getItems().toString());
	}

	/**
	 * 预定义一个索引的mapping，更方面设置字段等的属性
	 * 
	 * @author zylei
	 * @data 2016年10月21日 下午7:05:51
	 * @throws Exception
	 */
	public void getMapping() throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("test01")
				.startObject("properties")

				.startObject("id").field("type", "long").field("store", "yes").endObject()

				.startObject("name").field("type", "string").field("store", "yes").endObject()

				.startObject("age").field("type", "string").field("store", "yes").endObject()

				.startObject("sex").field("type", "string").field("store", "yes").endObject()

				.startObject("tel").field("type", "string").field("store", "yes").endObject()

				.endObject().endObject().endObject();

		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate("test");
		cirb.addMapping("test01", mapping);
		cirb.execute().actionGet();
		// client.admin().indices().create(new CreateIndexRequest("test"))
		// .actionGet();

		// PutMappingRequest putMappingRequest = Requests
		// .putMappingRequest("test").type("test01")
		// .source(mapping);
		// client.admin().indices().putMapping(putMappingRequest).actionGet();

		// CreateIndexRequestBuilder cirb =
		// client.admin().indices().prepareCreate("test").setSource(mapping);
		// CreateIndexResponse response = cirb.execute().actionGet();
		// cirb.addMapping("test01", mapping);
		// cirb.execute().actionGet();

		// CreateIndexResponse indexresponse =
		// geclient.admin().indices().prepareCreate("test").execute().actionGet();
		// LOG.info("indexresponse:{}", indexresponse.isAcknowledged());
		//
		// PutMappingRequestBuilder builder =
		// client.admin().indices().preparePutMapping("test");
		//
		// builder.setType("tes01");
		// builder.setSource(mapping);
		//
		// PutMappingResponse response = builder.execute().actionGet();
		// LOG.info("response:{}", response.isAcknowledged());

		// if (response.isAcknowledged()) {
		// LOG.info("response：{}", "创建成功！");
		// } else {
		// LOG.error("response：{}", "创建失败！");
		// }
	}

	/**
	 * 创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午10:17:39
	 */
	public void addindex01() {
		UserModel user = new UserModel();
		user.setId((long) 66);
		user.setName("测试1");
		user.setAge("66");
		user.setSex("男");
		user.setTel("666");
		String jsondata = object2Json(user);
		LOG.info("jsondata:{}", jsondata);

		IndexResponse res = client.prepareIndex().setIndex("test").setType("test01").setId("77").setSource(jsondata)
				.execute().actionGet();
		LOG.info("res:{}", res.getVersion());
	}

	/**
	 * 创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午10:29:54
	 * @throws IOException
	 */
	public void addindex02() throws IOException {
		IndexResponse response = client.prepareIndex("test", "test01", "77")
				.setSource(XContentFactory.jsonBuilder().startObject().field("id", "77").field("name", "测试2")
						.field("age", 77).field("sex", "女").field("tel", 777).endObject())
				.execute().actionGet();
		LOG.info("response:{}", response.getVersion());
	}

	/**
	 * 创建索引 id可以不指定，由es自动创建
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午10:19:31
	 * @param index
	 * @param type
	 * @param id
	 * @param jsonSource
	 */
	public void createIndex(String index, String type, String id, String jsonSource) {
		IndexRequestBuilder indexRequestBuilder = client.prepareIndex(index, type, id);
		indexRequestBuilder.setSource(jsonSource);
		indexRequestBuilder.execute().actionGet();
	}

	/**
	 * 初始化批量数据
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午10:37:23
	 * @return
	 */
	public List<String> getInitJsonDataList() {
		List<String> list = new ArrayList<String>();
		String data1 = ESUtils.object2Json(new UserModel(1, "111", "111", "111", "888"));
		String data2 = ESUtils.object2Json(new UserModel(2, "222", "222", "222", "888"));
		String data3 = ESUtils.object2Json(new UserModel(3, "333", "333", "333", "888"));
		String data4 = ESUtils.object2Json(new UserModel(4, "444", "444", "444", "888"));
		String data5 = ESUtils.object2Json(new UserModel(5, "555", "555", "555", "888"));
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		list.add(data5);
		return list;
	}

	/**
	 * 批量添加索引
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午11:08:41
	 * @param index
	 * @param type
	 * @param id
	 * @param dataList
	 */
	public void indexList(String index, String type, String id, List<String> dataList) {
		if (dataList != null) {
			int size = dataList.size();
			if (size > 0) {
				BulkRequestBuilder bulkRequest = client.prepareBulk();
				for (int i = 0; i < size; ++i) {
					String data = dataList.get(i);
					String jsonSource = object2Json(data);
					if (jsonSource != null) {
						bulkRequest.add(client.prepareIndex(index, type, id).setRefresh(true).setSource(jsonSource));
					}
				}

				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				if (bulkResponse.hasFailures()) {
					Iterator<BulkItemResponse> iter = bulkResponse.iterator();
					while (iter.hasNext()) {
						BulkItemResponse itemResponse = iter.next();
						if (itemResponse.isFailed()) {
							LOG.error(itemResponse.getFailureMessage());
						}
					}
				}
			}
		}
	}

	/**
	 * 批量创建索引
	 * 
	 * @author zylei
	 * @data 2016年10月24日 上午11:43:30
	 * @param index
	 * @param type
	 * @param id
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map createIndexList(String index, String type, String id, List<String> dataList) {
		BulkRequestBuilder bulkRequest = client.prepareBulk().setRefresh(true);
		Map resultMap = new HashMap();
		for (Object object : dataList) {
			String jsonSource = object2Json(object);
			// 如果没有指定id，es自动生成
			if (StringUtils.isBlank(id)) {
				IndexRequestBuilder irb = client.prepareIndex(index, type).setSource(jsonSource);
				bulkRequest.add(irb);
			} else {
				IndexRequestBuilder irb = client.prepareIndex(index, type, id).setSource(jsonSource);
				bulkRequest.add(irb);
			}
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			LOG.info("bulkResponse:{}", bulkResponse.getItems().toString());
			resultMap.put("500", "保存ES失败！");
		}
		bulkRequest = client.prepareBulk();
		resultMap.put("200", "保存ES成功！");
		return resultMap;
	}

	/**
	 * 查询索引
	 * 
	 * @author zylei
	 * @data 2016年10月24日 下午2:45:48
	 * @param queryBuilder
	 * @param index
	 * @param type
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List searcher(QueryBuilder queryBuilder, String index, String type) {
		List list = new ArrayList();
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).execute()
				.actionGet();
		SearchHits hits = searchResponse.getHits();
		LOG.info("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				Long id = Long.valueOf(String.valueOf(hit.getSource().get("id")));
				String name = (String) hit.getSource().get("name");
				String age = (String) hit.getSource().get("age");
				String sex = (String) hit.getSource().get("sex");
				String tel = (String) hit.getSource().get("tel");
				list.add(new UserModel(id, name, age, sex, tel));
			}
		}
		return list;
	}

	/**
	 * 查询索引
	 * 
	 * @author zylei
	 * @data 2016年10月27日 下午3:46:30
	 * @param index
	 * @param type
	 * @return
	 */
	public void search(String index, String type, QueryBuilder queryBuilder) {
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).setFrom(0)
				.setSize(100).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		LOG.info("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				LOG.info("score:" + hit.getScore() + ":\t" + hit.getSource());
			}
		} else {
			LOG.warn("搜到0条结果！");
		}
	}

	/**
	 * 使用multi get API可以通过索引名、类型名、文档id一次得到一个文档集合，文档可以来自同一个索引库，也可以来自不同索引库
	 * @author zylei
	 * @data   2016年11月1日 下午5:31:55
	 */
	public void getDoc() {
		MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
				.add("test", "test01", "1") // 通过单一的ID获取一个文档
				.add("test", "test01", "2", "3", "4") // 传入多个id，从相同的索引名/类型名中获取多个文档
				.add("another", "type", "foo") // 可以同时获取不同索引中的文档. 
				.get();
		for (MultiGetItemResponse itemResponse : multiGetItemResponses) { // 遍历
			GetResponse response = itemResponse.getResponse();
			if (response.isExists()) { // 判断是否存在
				String json = response.getSourceAsString(); // 获取文档源
				LOG.info("json:{}", json);
			}
		}
	}

}
