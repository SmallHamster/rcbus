package com.leoman.wechat.pay.controller;

import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.core.Result;
import com.leoman.entity.Configue;
import com.leoman.pay.RequestHandler;
import com.leoman.pay.util.ConstantUtil;
import com.leoman.pay.util.XMLUtil;
import com.leoman.user.entity.WeChatUser;
import com.leoman.user.service.WeChatUserService;
import com.leoman.utils.CommonUtils;
import com.leoman.utils.WebUtil;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 退款
 * Created by 史龙 on 2016/10/12.
 */

@Controller
@RequestMapping(value = "wechat/refund")
public class WeChatRefundController {

    @Autowired
    private CarRentalService carRentalService;

    @RequestMapping(value="/refundPay")
    @ResponseBody
    public Result wxRefund(HttpServletRequest request, HttpServletResponse response, Long rentalId,Double refund){
        System.out.println("----------rentalId：" + rentalId);
        CarRental carRental = carRentalService.queryByPK(rentalId);
        System.out.println("----------order：" + carRental.getOrder().getOrderNo());
        //订单号
        String orderNo = carRental.getOrder().getOrderNo();
//       String path = "E:\\projects\\rcbus\\server\\src\\main\\webapp\\cert\\apiclient_cert.p12";
        String path = "//usr//local//tomcat//webapps//leoman_rcbus//cert//apiclient_cert.p12";
        String refundId = CommonUtils.generateUUID();
        String nonce_str = System.currentTimeMillis() + "";
         /*-----  1.生成预支付订单需要的的package数据-----*/
        TreeMap packageParams = new TreeMap();
//        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", ConstantUtil.APP_ID);
        packageParams.put("mch_id", ConstantUtil.PARTNER_ID);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("op_user_id", ConstantUtil.PARTNER_ID);
        packageParams.put("out_trade_no", orderNo);
        packageParams.put("out_refund_no", refundId);
        packageParams.put("total_fee",(int)(carRental.getIncome() * 100.0D) + "");
        packageParams.put("refund_fee", (int)(refund * 100.0D) + "");


//        RequestHandler reqHandler = new RequestHandler(request, response);
//        reqHandler.init(appId, secret, key);
//        String sign1 = reqHandler.createSign(packageParams);

        String sign = WxCryptUtil.createSign(packageParams, ConstantUtil.PARTNER_KEY);
        System.out.println("----------sign：" + sign);
        String xml="<xml>"
                +"<appid>"+ConstantUtil.APP_ID+"</appid>"
                + "<mch_id>"+ConstantUtil.PARTNER_ID+"</mch_id>"
                + "<nonce_str>"+nonce_str+"</nonce_str>"
                + "<op_user_id>"+ConstantUtil.PARTNER_ID+"</op_user_id>"
                + "<out_trade_no>"+orderNo+"</out_trade_no>"
                + "<out_refund_no>"+refundId+"</out_refund_no>"
                + "<refund_fee>"+(int)(carRental.getIncome() * 100.0D) + ""+"</refund_fee>"
                + "<total_fee>"+(int)(refund * 100.0D) + ""+"</total_fee>"
                + "<sign>"+sign+"</sign>"
                +"</xml>";
        System.out.println("----------xml：" + xml);
        try{
             /*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(path));
            try {
                keyStore.load(instream, ConstantUtil.PARTNER_ID.toCharArray());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, ConstantUtil.PARTNER_ID.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            /*----5.发送数据到微信的退款接口---- */
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            httpPost.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse weixinResponse = httpClient.execute(httpPost);
            String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
            Map map = XMLUtil.doXMLParse(jsonStr);
            if("success".equalsIgnoreCase((String) map.get("return_code"))){
                return Result.success();
            }else{
                System.out.print("----------错误:"+(String) map.get("return_msg"));
                return Result.failure();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }

    }


}
