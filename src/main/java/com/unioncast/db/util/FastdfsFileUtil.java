package com.unioncast.db.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import com.unioncast.common.util.CommonUtil;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unioncast.common.adx.model.UploadFileInfo;

public class FastdfsFileUtil {

	private static String configFileName = null;
	private static StorageClient storageClient = null;

	private static final Logger LOG = LoggerFactory.getLogger(FastdfsFileUtil.class);

	static {
		configFileName = FastdfsFileUtil.class.getClassLoader().getResource("").getPath() + "fdfs_client.conf";
		LOG.info("初始化FastFDFS客户端，读取配置为"+configFileName);
		if(CommonUtil.isNotNull(configFileName)) {
			try {
				if(configFileName.contains(":"))
					configFileName = configFileName.replaceFirst("^\\/", "");
				configFileName = URLDecoder.decode(configFileName,"UTF-8");
			} catch (Exception e){}
		}
		// 加载配置文件的方式
		try {
			ClientGlobal.init(configFileName);
		} catch (Exception e) {
			LOG.error("客户端初始化失败!配置文件为"+configFileName,e);
		}
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;

		TrackerClient tracker = new TrackerClient();
		try {
			trackerServer = tracker.getConnection();
		} catch (IOException e) {
			LOG.info("获取连接失败！");
			e.printStackTrace();
		}
		storageClient = new StorageClient(trackerServer, storageServer);
	}

	/**
	 * 上传文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月13日 下午5:13:10
	 * @param fileInfo
	 *            上传文件的路径
	 * @return 组名,文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[] uploadFile(UploadFileInfo fileInfo) throws FileNotFoundException, IOException {
		byte[] fileBuff = null;
		fileBuff = fileInfo.getFileData();
		String fileExtName = "";
		String fileName = fileInfo.getFileName();
		if (fileName.contains(".")) {
			fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			LOG.info("Fail to upload file, because the format of filename is illegal.");
			return null;
		}

		// 设置元信息
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(fileInfo.getFileLength()));

		// 上传文件
		String[] files = null;
		try {
			synchronized (storageClient) {
				files = storageClient.upload_file(fileBuff, fileExtName, metaList);
			}
		} catch (Exception e) {
			LOG.info("Upload file \"" + fileName + "\"fails");
		}
		LOG.info("upload files:{}", (Object[])files);
		return files;
	}

	/**
	 * 下载文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月13日 下午5:15:46
	 * @param groupName
	 *            组名
	 * @param remoteFileName
	 *            需要下载的远程文件名
	 * @return 文件内容
	 * @throws MyException
	 * @throws IOException
	 */
	public static byte[] saveFile(String groupName, String remoteFileName) throws IOException, MyException {
		byte[] content;
		content = storageClient.download_file(groupName, remoteFileName);
		LOG.info("saveFile content:{}", content);
		return content;
	}

	/**
	 * 删除远程文件
	 * 
	 * @author 刘蓉
	 * @date 2016年10月14日 上午9:43:15
	 * @param groupName
	 *            组名
	 * @param remoteFileName
	 *            远程文件名
	 * @throws Exception
	 */
	public static void deleteFile(String groupName, String remoteFileName) throws Exception {
		storageClient.delete_file(groupName, remoteFileName);
	}
}
