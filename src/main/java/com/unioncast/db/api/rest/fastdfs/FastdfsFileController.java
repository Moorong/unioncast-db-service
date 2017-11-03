package com.unioncast.db.api.rest.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.MyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unioncast.common.adx.model.UploadFileInfo;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.api.rest.GeneralController;
import com.unioncast.db.util.FastdfsFileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api("Fastdfs文件操作")
@RestController
@RequestMapping("/rest/image")
public class FastdfsFileController extends GeneralController {
	private static final Logger LOG = LogManager.getLogger(FastdfsFileController.class);

	/**
	 * 上传文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月14日 上午11:06:31
	 * @param fileInfo
	 *            上传文件信息
	 * @return 文件访问路径：组名/文件名
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@ApiOperation(value = "上传文件", httpMethod = "POST", response = String.class)
	@ApiImplicitParam(name = "fileInfo", value = "上传文件信息", required = true, dataType = "UploadFileInfo", paramType = "body")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public RestResponse upload(@RequestBody UploadFileInfo fileInfo) throws FileNotFoundException, IOException {
		RestResponse response = new RestResponse();
		try {
			String[] uploadResults = null;
			String groupName = null;
			String remoteFileName = null;
			uploadResults = FastdfsFileUtil.uploadFile(fileInfo);
			groupName = uploadResults[0];
			remoteFileName = uploadResults[1];
			LOG.info("image upload results:{}", (Object[]) uploadResults);
			response.setStatus(RestResponse.OK);
			response.setResult(groupName + "/" + remoteFileName);
			return response;
		} catch (IOException e) {
			LOG.error("文件上传失败!",e);
			response.setStatus(RestResponse.FAIL);
			response.setResult("上传失败");
			return response;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月14日 上午9:26:48
	 * @param groupName
	 *            组名
	 * @param remoteFileName
	 *            远程文件名
	 * @param saveFileName
	 *            保存的文件名
	 * @return 实体body即downloadResults中包含有文件的相关属性
	 * @throws MyException
	 * @throws IOException
	 */
	@ApiOperation(value = "下载文件", httpMethod = "POST", response = RestResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "groupName", value = "组名", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "remoteFileName", value = "远程文件名", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "saveFileName", value = "保存的文件名", required = false, dataType = "String", paramType = "body")})
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<byte[]> downloadFile(@RequestParam("groupName") String groupName,
			@RequestParam("remoteFileName") String remoteFileName, @RequestParam(value = "saveFileName", required = false) String saveFileName)
			throws IOException, MyException {
		byte[] downloadResults = null;
		downloadResults = FastdfsFileUtil.saveFile(groupName, remoteFileName);
		LOG.info("image download results:{}", downloadResults);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", saveFileName == null ? remoteFileName : saveFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(downloadResults, headers, HttpStatus.CREATED);
	}

	/**
	 * 删除远程文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月14日 上午10:26:08
	 * @param groupName
	 *            组名
	 * @param remoteFileName
	 *            远程文件名
	 * @throws Exception
	 */
	@ApiOperation(value = "删除远程文件", httpMethod = "POST")
	@ApiImplicitParams({@ApiImplicitParam(name = "groupName", value = "组名", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "remoteFileName", value = "远程文件名", required = true, dataType = "String", paramType = "body")})
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteFile(@RequestParam("groupName") String groupName,
			@RequestParam("remoteFileName") String remoteFileName) throws Exception {
		FastdfsFileUtil.deleteFile(groupName, remoteFileName);
		LOG.info("File delete success!!");
	}

}
