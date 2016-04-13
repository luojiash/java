package com.luojiash.qiniu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云服务 存储图片服务
 *
 */
public class QiNiuServer {
    private static final Log log = LogFactory.getLog(QiNiuServer.class);

    /**
     * TODO read from config
     */
    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    private static String SPACE_NAME;
    
    private static UploadManager uploadManager = new UploadManager();
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    
    public static void download(String url){
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(url,3600);
        System.out.println(downloadRUL);
      }

    /**
     * 上传数据
     *uploadManager.put
     * @param data     上传的数据 byte[]、File、filePath
     * @param key      上传数据保存的文件名
     * @param token    上传凭证
     * @param params   自定义参数，如 params.put("x:foo", "foo")
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @return key
     * @throws QiniuException
     */
    public static String upload(byte[] bytes,String fileName) {
        try {
            Response res = uploadManager.put(bytes, fileName, getUpToken(SPACE_NAME));
            MyRet ret = res.jsonToObject(MyRet.class);
            log.info("图片上传返回结果为:"+res.bodyString());
            return ret.key;
        } catch (QiniuException e) {
            // 请求失败时简单状态信息
            log.error("图片上传请求失败：代码"+e.code() + "：错误" + e.getMessage());
            try {
                Response r = e.response;
                // 响应的文本信息
                log.error("图片上传异常，响应文本："+r.bodyString());
            } catch (QiniuException e1) {
                log.error("图片上传异常......");
            }
        }
        return null;
    }
    
    /**
     * 
     * @param spaceName
     *          存储空间名字 如 test-ean
     * @return 生成的上传token
     */
    private static String getUpToken(String spaceName){
        return auth.uploadToken(spaceName);
    }

    /**
     * 覆盖上传
     * @param spaceName  存储空间名字
     * @param fileName   需要覆盖的文件名字
     * @return 生成的上传token
     */
    public static String getUpToken(String spaceName , String fileName){
        return auth.uploadToken(spaceName, fileName);
    }
    
    /**
    * 生成上传token
    *   uploadToken():
    * @param spaceName  空间名
    * @param fileName     key，可为 null
    * @param expires 有效时长，单位秒。默认3600s
    * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
    *        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
    * @param strict  是否去除非限定的策略字段，默认true
    * @return 生成的上传token
    */
    public static String getUpToken(String spaceName,String fileName ,int expires ,String policy){
        return auth.uploadToken(spaceName, null, 3600, new StringMap().put("insertOnly", 1)
             .put("callbackUrl", "").putNotEmpty("callbackHost", "")
             .put("callbackBody", "key=$(key)&hash=$(etag)"));
    }

    /**
     * 封装返回的数据 类
     */
    public class MyRet {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }
}
