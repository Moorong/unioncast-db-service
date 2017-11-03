package com.unioncast.db;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.unioncast.common.page.Pagination;
import com.unioncast.common.user.model.Qualification;
import com.unioncast.db.rdbms.core.exception.DaoException;
import com.unioncast.db.rdbms.core.service.common.QualificationService;

@RestController
@RequestMapping("/restClient/")
public class RestClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(RestClientTest.class);

	@Autowired
	private QualificationService qualificationService;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	/**
	 * 查找所有
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午6:59:59
	 *
	 * @return
	 * @throws DaoException
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Qualification> findAll() throws DaoException {
		Qualification[] qualifications = qualificationService.findAll();
		return Arrays.asList(qualifications);
	}

	/**
	 * 分页条件查找
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午6:59:46
	 *
	 * @return
	 * @throws DaoException
	 */
	@RequestMapping(value = "/page/{currentPage}/{pageSize}", method = RequestMethod.POST)
	public Pagination<Qualification> page(@RequestBody Qualification qualification, @PathVariable Integer currentPage,
			@PathVariable Integer pageSize) throws DaoException {
		return qualificationService.page(qualification, currentPage, pageSize);
	}

	/**
	 * 根据id查找
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:09:03
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	// @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	// public Qualification findById(@PathVariable Long id) throws DaoException
	// {
	// return qualificationService.findById(id);
	// }
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	public Qualification findById(@PathVariable Long id) throws DaoException {
		RestTemplate restTemplate = new RestTemplate();
		// Qualification qualification =
		// restTemplate.getForObject("http://127.0.0.1:9330/rest/qualification/findById/3",
		// Qualification.class);
		// System.out.println(qualification);

		ResponseEntity<?> responseEntity = restTemplate
				.getForEntity("http://127.0.0.1:9330/rest/qualification/findById/3", Qualification.class);
		System.out.println(responseEntity);
		System.out.println(responseEntity.getBody());

		// com.unioncast.db.api.rest.Error error = restTemplate.getForObject(
		// "http://127.0.0.1:9330/rest/qualification/findById/3", com.unioncast.db.api.rest.Error.class);
		// System.out.println(error);
		return null;
	}

	/**
	 * 根据userId进行查找
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:11:47
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	@RequestMapping(value = "/findByUserId/{userId}", method = RequestMethod.GET)
	public Qualification[] findByUserId(@PathVariable Long userId) throws DaoException {
		return qualificationService.findByUserId(userId);
	}

	/**
	 * 增加
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:18:52
	 *
	 * @param name
	 * @return 返回新增实体的id，在HttpHeader的url属性中包含了新增记录的访问路径
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Long> add(@RequestBody Qualification qualification, UriComponentsBuilder uriComponentsBuilder)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		Long id = qualificationService.save(qualification);
		HttpHeaders httpHeaders = new HttpHeaders();
		URI uri = uriComponentsBuilder.path("/rest/qualification/findById/").path(String.valueOf(id)).build().toUri();
		httpHeaders.setLocation(uri);
		return new ResponseEntity<Long>(id, httpHeaders, HttpStatus.CREATED);
	}

	/**
	 * 批量增加
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:23:40
	 *
	 * @param qualification
	 * @return 新增实体的list集合
	 * @throws DaoException
	 */
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Long> batchAdd(@RequestBody List<Qualification> qualifications) throws DaoException {
		return qualificationService.batchAdd(qualifications);
	}

	/**
	 * 修改
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:26:08
	 *
	 * @param qualification
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody Qualification qualification)
			throws DaoException, IllegalArgumentException, IllegalAccessException {
		qualificationService.updateNotNullField(qualification);
	}

	/**
	 * 删除
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:27:57
	 *
	 * @param id
	 * @return 删除了多少条
	 * @throws DaoException
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public int delete(@PathVariable Long id) throws DaoException {
		return qualificationService.deleteById(id);
	}

	/**
	 * 批量删除
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午7:29:22
	 *
	 * @param id
	 * @return 删除了多少条
	 * @throws DaoException
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public int batchDelete(@RequestBody List<Long> ids) throws DaoException {
		return qualificationService.batchDelete(ids);
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public void test() {
		HttpHeaders requestHeaders = new HttpHeaders();
		HttpEntity<Long> httpEntity = new HttpEntity<Long>(1L, requestHeaders);
		RestTemplate restTemplate = restTemplateBuilder.build();
//		ModuleArrayResponse result = restTemplate.postForObject("http://127.0.0.1:9330/v1/rest/module/find", httpEntity,
//				ModuleArrayResponse.class);
//		ModuleArrayResponse result = (ModuleArrayResponse) restTemplate.postForObject("http://127.0.0.1:9330/v1/rest/module/find", httpEntity,
//				RestResponse.class);
//		RestResponse result = restTemplate.postForObject("http://127.0.0.1:9330/v1/rest/module/find", httpEntity,
//				RestResponse.class);
//		ApiInfoArrayResponse result = restTemplate.postForObject("http://127.0.0.1:9330/v1/rest/apiInfo/find", httpEntity,
//				ApiInfoArrayResponse.class);
//		System.out.println(result); 
	}
}
