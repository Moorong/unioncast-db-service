//package com.unioncast.db.nosql.elasticsearch;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.action.admin.indices.mapping.put.PutMappingRequestBuilder;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.FilterBuilder;
//import org.elasticsearch.index.query.FilterBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ElasticsearchUtil {
//
//	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchUtil.class);
//
//	private Client client;
//
//	private static ObjectMapper objectMapper = new ObjectMapper();
//
//	public static String toJson(Object object) {
//		try {
//			return objectMapper.writeValueAsString(object);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	public void init() {
//
//		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("192.168.101.126", 9200));
//
//		LOG.info("client:{}", client);
//	}
//
//	public void close() {
//		client.close();
//	}
//
//	static XContentBuilder getMapping() throws Exception {
//		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("test")
//				.startObject("properties")
//
//				.startObject("id").field("type", "long").field("store", "yes").endObject()
//
//				.startObject("name").field("type", "string").field("store", "yes").endObject()
//
//				.startObject("age").field("type", "string").field("store", "yes").endObject()
//
//				.startObject("sex").field("type", "string").field("store", "yes").endObject()
//
//				.startObject("tel").field("type", "string").field("store", "yes").endObject()
//
//				.endObject().endObject().endObject();
//
//		ElasticsearchUtil es = new ElasticsearchUtil();
//
//		CreateIndexResponse indexresponse = es.client.admin().indices().prepareCreate("test").execute().actionGet();
//		LOG.info("indexresponse:{}", indexresponse.acknowledged());
//
//		PutMappingRequestBuilder builder = es.client.admin().indices().preparePutMapping("test");
//
//		builder.setType("tes01");
//		builder.setSource(mapping);
//
//		PutMappingResponse response = builder.execute().actionGet();
//		LOG.info("response:{}", response.acknowledged());
//		
//		if (response.acknowledged()) {
//			System.out.println("创建成功！");
//		} else {
//			System.err.println("创建失败！");
//		}
//		return mapping;
//	}
//
//	public void addindex01() {
//		UserModel user = new UserModel();
//		user.setId(66);
//		user.setName("测试");
//		user.setAge("99");
//		user.setSex("男");
//		user.setTel("911");
//		String jsondata = ElasticsearchUtil.toJson(user);
//		LOG.info("jsondata:{}", jsondata);
//
//		IndexResponse res = client.prepareIndex().setIndex("test").setType("test01").setId("66").setSource(jsondata)
//				.execute().actionGet();
//		LOG.info("res:{}", res.getVersion());
//	}
//
//	public void search04() {
//		// FilterBuilder fb = FilterBuilders.prefixFilter("sex", "222");
//		FilterBuilder fb2 = FilterBuilders.rangeFilter("id").from(1).to(10);
//		// FilterBuilder fb3 = FilterBuilders
//		// .andFilter(FilterBuilders.rangeFilter("id").from(1).to(10),
//		// FilterBuilders.prefixFilter("sex", "333"))
//		// .cache(true);
//		SearchResponse res = client.prepareSearch("test").setTypes("test01")
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setFilter(fb2).execute().actionGet();
//
//		SearchHits shs = res.hits();
//		LOG.info("shs:{}", shs.getHits().length);
//		for (SearchHit it : shs) {
//			LOG.info("it:{}", it.getScore());
//		}  
//	}
//	
//	public void searchIndex() {
//		GetResponse res = client.prepareGet().setIndex("test")
//				.setType("test01").setId("1").execute().actionGet();
////		GetResponse res1 = client.prepareGet("test", "test01", "1").execute().actionGet();
//		LOG.info("res:{}", res.getSource());
//	}
//
//	public static List<String> getInitJsonData() {
//		List<String> list = new ArrayList<String>();
//		String data1 = ElasticsearchUtil.toJson(new UserModel(1, "111", "111", "111", "111"));
//		String data2 = ElasticsearchUtil.toJson(new UserModel(2, "222", "222", "222", "222"));
//		String data3 = ElasticsearchUtil.toJson(new UserModel(3, "333", "333", "333", "333"));
//		String data4 = ElasticsearchUtil.toJson(new UserModel(4, "444", "444", "444", "444"));
//		String data5 = ElasticsearchUtil.toJson(new UserModel(5, "555", "555", "555", "555"));
//		list.add(data1);
//		list.add(data2);
//		list.add(data3);
//		list.add(data4);
//		list.add(data5);
//		return list;
//	}
//	
//	public void delete01() {
//
//		DeleteResponse res = client.prepareDelete().setIndex("test2")
//				.setType("test02").setId("4").execute().actionGet();
//		LOG.info("res:{}", res.getVersion());
//	}
//
//	public void update01() {
////		UpdateRequest updateRequest = new UpdateRequest();
////		updateRequest.index(indexName);
////		updateRequest.type(type);
////		updateRequest.id("1");
////		updateRequest.doc(jsonBuilder()
////				.startObject()
////				.field("type", "file") 
////				.endObject());
////		client.update(updateRequest).get();
//	}
//	public static void main(String[] args) throws Exception {
//		ElasticsearchUtil es = new ElasticsearchUtil();
//		es.init();
//		ElasticsearchUtil.getMapping();
//		es.search04();
//
////		List<String> jsonData = ElasticsearchUtil.getInitJsonData();
////
////		for (int i = 0; i < jsonData.size(); i++) {
////			 IndexRequestBuilder response = es.client.prepareIndex("user",
////			 "test01").setSource(jsonData.get(i));
////			 if (response != null) {
////			 System.out.println("创建成功!");
////			 }
////		}
//		es.close();
//	}
//
//	// public static void main(String[] args) throws Exception {
//	// ElasticsearchUtil es = new ElasticsearchUtil();
//	// es.init();
//	//
//	// CreateIndexResponse indexresponse =
//	// es.client.admin().indices().prepareCreate("test4").execute().actionGet();
//	// LOG.info("indexresponse:{}", indexresponse.acknowledged());
//	//
//	// PutMappingRequestBuilder builder =
//	// es.client.admin().indices().preparePutMapping("test2");
//	//
//	// builder.setType("tes04");
//	// XContentBuilder mapping = ElasticsearchUtil.getMapping();
//	// builder.setSource(mapping);
//	//
//	// PutMappingResponse response = builder.execute().actionGet();
//	// LOG.info("response:{}", response.acknowledged());
//	//
//	// es.close();
//	// }
//
//}
