package cn.ekgc.itrip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import llc.iob.iobots.model.bots.system.Cache;
import llc.iob.iobots.model.enums.RunningEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

/**
 * 
 * 跨服务器的SFTP文件传输工具
 * @author LouisLiu
 * @date 2019/05/14
 *
 */
@Component
public class SFTPUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
	
	private static String host;
	private static int port;
	private static String username;
	private static String password;
	private static String secretKeyFilePath;

	private static ChannelSftp sftp = null;
	private static Session sshSession = null;


	/**
	 * 读取配置文件并赋值
	 */
	@Value("${SFTP.host}")
	public void setHost(String host){
		SFTPUtil.host = host;
	}
	
	@Value("${SFTP.port}")
	public void setPort(int port){
		SFTPUtil.port = port;
	}
	
	@Value("${SFTP.username}")
	public void setUsername(String username){
		SFTPUtil.username = username;
	}
	
	@Value("${SFTP.password}")
	public void setPassword(String password){
		SFTPUtil.password = password;
	}

	@Value("${SFTP.secretKeyFilePath}")
	public void setSecretKeyFilePath(String secretKeyFilePath){
//		// 获取项目绝对路径
//		String absolutePath = this.getClass().getResource("").getPath();
//		// 相对路径
//		absolutePath = absolutePath.replaceAll("\\\\", "/");
//		// 文件路径 = 绝对路径 + 相对路径
//		SFTPUtil.secretKeyFilePath = absolutePath + secretKeyFilePath;
		if (RunningEnvironment.DEV.toString().equals(Cache.runningEnvironment)) {
			// 开发环境是项目里的相对路径
			// 获取项目绝对路径
			String absolutePath = System.getProperty("user.dir");
			// 相对路径
			absolutePath = absolutePath.replaceAll("\\\\", "/");
			// 文件路径 = 绝对路径 + 相对路径
			SFTPUtil.secretKeyFilePath = absolutePath + "/src/main/resources/" + secretKeyFilePath;
		} else {
		    // 服务器上是绝对路径
			SFTPUtil.secretKeyFilePath = secretKeyFilePath;
		}
	}

	/**
	 * @Description 链接服务器
	 * @Title connect
	 * @Param []
	 * @Return com.jcraft.jsch.ChannelSftp
	 * @Author Louis
	 * @Date 2020/12/18 14:53
	 */
	public static ChannelSftp connect(){
		// 现在默认使用秘钥链接
		return connectFileServerBySecretKey();
	}


	/**
	 * 连接SFTP服务器
	 * @author louis
	 * @since 2018/12/29
	 */
	public static ChannelSftp connectFileServer() {
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("[Password] Successfully connected to " + host + " !");
		} catch (JSchException  e) {
			logger.error(e.toString());
			logger.error("[Password] Connection failed!");
		}
		return sftp;
	}

	/**
	 * @Description 使用秘钥链接远程服务器
	 * @Title connectFileServerBySecretKey
	 * @Param []
	 * @Return com.jcraft.jsch.ChannelSftp
	 * @Author Louis
	 * @Date 2020/12/18 14:45
	 */
	public static ChannelSftp connectFileServerBySecretKey() {
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(secretKeyFilePath);
			sshSession = jsch.getSession(username, host, port);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("[Secret Key] Successfully connected to " + host + " !");
		} catch (JSchException  e) {
			logger.error(e.toString());
			logger.error("[Secret Key] Connection failed!");
		}
		return sftp;
	}


	/** 
     * 上传单个文件 
     * @param remotePath：远程保存目录 
     * @param file：上传的文件(本地路径+名字)
     * @return boolean
     */ 
    public static boolean uploadFile(String remotePath, String file){
		connect();
        FileInputStream in = null;
        try {
            createDir(remotePath);  //远程保存目录
            in = new FileInputStream(file);
            sftp.put(in, file.substring(file.lastIndexOf(File.separator) + 1));
            logger.info("Successful upload!");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
        	disconnect();
            if (in != null){
                try{
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /** 
     * 创建目录
     * @param createpath 远程保存目录 
     * @return boolean
     */ 
    public static boolean createDir(String createpath){
        try {
        	//文件存在
            if (isDirExist(createpath)){
            	//cd()更改当前远程目录
                sftp.cd(createpath);  
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry){
                if ("".equals(path)){
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())){
                    sftp.cd(filePath.toString());
                } else {
                    //建立目录 
                    sftp.mkdir(filePath.toString()); //mkdir()创建一个新的远程目录 
                    // 进入并设置为当前目录 
                    sftp.cd(filePath.toString());
                }
            }
            sftp.cd(createpath);
            return true;
        } catch (SftpException e){
            e.printStackTrace();
        }
        return false;
    }
    
    /** 
     * 判断目录是否存在 
     * @param directory 
     * @return boolean
     */
    public static boolean isDirExist(String directory){
        boolean isDirExistFlag = false;
        try{
            SftpATTRS sftpATTRS = sftp.lstat(directory);  //lstat()检索文件或目录的文件属性
            isDirExistFlag = true;
            return sftpATTRS.isDir();  //isDir()检查此文件是否为目录
        }catch (Exception e){
            if ("no such file".equals(e.getMessage().toLowerCase())){
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }  
    
    /**
     * 下载单个文件 
     * @param directory 下载目录 
     * @param downloadFile 下载的文件 
     * @param saveFile 存在本地的路径 
     * @param sftp
     */
    public static void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory); //下载目录
            sftp.get(downloadFile,saveFile); //下载的文件 存在本地的路径 
        }catch (Exception e){
            e.printStackTrace();
        }
    }  
    
    /**
     * 批量上传文件
     * @param remotePath 远程保存目录
     * @param localPath 本地上传目录(以路径符号结束)
     * @param delete 上传后是否删除本地文件
     * @return boolean
     */
    public static boolean bacthUploadFile(String remotePath, String localPath,  boolean delete) {
    	try {
			connect();
    		File file = new File(localPath);
    		File[] files = file.listFiles();
    		for (int i = 0; i < files.length; i++){
    			if (files[i].isFile() && files[i].getName().indexOf("bak") == -1){
    				if (uploadFile(remotePath, (localPath + files[i].getName())) && delete){ //上传文件
    					deleteFile(localPath + files[i].getName()); //上传后删除本地文件
    				}
    			}
    		}
    		return true;
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		disconnect();
    	}
    	return false;
	}
    
    /**
     * 删除本地文件
     * @since 2019/01/02
     * @author louis
     * @param filePath
     * @return boolean
     */
    public static boolean deleteFile(String filePath){
    	File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 关闭连接SFTP服务器
     * @since 2019/01/02
     * @author louis
     */
    public static void disconnect(){
    	if (sftp != null){
    		if (sftp.isConnected()){
    			sftp.disconnect();
    			logger.info("Disconnect!");
            }
        }
        if (sshSession != null){
        	if (sshSession.isConnected()){
        		sshSession.disconnect();
        	}
        }
    }
    
    /**
     * 批量下载文件
     * @param remotPath 远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath 本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat 下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat 下载文件格式(文件格式)
     * @param del 下载后是否删除sftp文件
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static List<String> batchDownLoadFile(String remotePath, String localPath, String fileFormat, String fileEndFormat, boolean del) {
    	List<String> filenames = new ArrayList<String>();
    	try {
			connect();
    		//Vector v = listFiles(remotePath);
    		Vector v = sftp.ls(remotePath); //lstat(String path):列出远程目录的内容
    		// sftp.cd(remotePath);
    		if (v.size() > 0) {
    			System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
    			System.out.println("The number of files processed this time is not zero, start downloading...fileSize=" + v.size());
    			Iterator it = v.iterator();
    			while (it.hasNext()) {
					LsEntry entry = (LsEntry) it.next();
					String filename = entry.getFilename();
					SftpATTRS attrs = entry.getAttrs();
					if (!attrs.isDir()) {
						boolean flag = false;
						String localFileName = localPath + filename;
						fileFormat = fileFormat == null ? "" : fileFormat.trim();
						fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();
						// 三种情况
						if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
							if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
								flag = downloadFile(remotePath, filename,localPath, filename);
								if (flag) {
									filenames.add(localFileName);
									if (flag && del) {
										deleteSFTP(remotePath + filename);
									}
								}
							}
						} else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
							if (filename.startsWith(fileFormat)) {
								flag = downloadFile(remotePath, filename, localPath, filename);
								if (flag) {
									filenames.add(localFileName);
									if (flag && del) {
										deleteSFTP(remotePath + filename);
									}
								}
							}
						} else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
							if (filename.endsWith(fileEndFormat)) {
								flag = downloadFile(remotePath, filename,localPath, filename);
								if (flag) {
									filenames.add(localFileName);
									if (flag && del) {
										deleteSFTP(remotePath + filename);
									}
								}
							}
						} else {
							flag = downloadFile(remotePath, filename,localPath, filename);
							if (flag) {
								filenames.add(localFileName);
								if (flag && del) {
									deleteSFTP(remotePath + filename);
								}
							}
						}
					}
    			}
			}
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
    	return filenames;
    }
    
    /**
     * 下载单个文件
     * @param remotPath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public static boolean downloadFile(String remotePath, String remoteFileName,String localPath, String localFileName) {
		connect();
    	FileOutputStream fieloutput = null;
    	try {
    		// sftp.cd(remotePath);
    		File file = new File(localPath + localFileName);
    		// mkdirs(localPath + localFileName);
    		fieloutput = new FileOutputStream(file);
    		sftp.get(remotePath + remoteFileName, fieloutput);
    		return true;
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (SftpException e) {
    		e.printStackTrace();
    	} finally {
    		disconnect();
    		if (null != fieloutput) {
    			try {
    				fieloutput.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 删除stfp文件
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     * @param sftp
     */
    public static void deleteSFTP(String deleteFile) {
		connect();
		try {
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
    }
    
   /**
    * 列出目录下的文件
	* @throws SftpException
    * @param directory: 要列出的目录
    * @return Vector
    * @throws SftpException
    */
    @SuppressWarnings("rawtypes")
    public static Vector listFiles(String directory) throws SftpException {
		connect();
    	Vector files = sftp.ls(directory);
    	disconnect();
    	return files;
	}
    
}
