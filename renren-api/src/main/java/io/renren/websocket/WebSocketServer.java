package io.renren.websocket;

import com.alibaba.fastjson.JSONObject;
import io.renren.commom.CommonService;
import io.renren.common.exception.RenException;
import io.renren.entity.TokenEntity;
import io.renren.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/api/websocket/{token}")
public class WebSocketServer {
    //在线总数
    private static int onlineCount;
    //当前会话
    public Session session;
    //用户token
    public String token;
    public Long userId;

    private static TokenService tokenService;
    private static CommonService commonService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    /**
     * 用来存放每个客户端对应的WebSocketServer对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap();


    /**
     * 建立连接
     * @param session
     * @param token
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        //查询token信息
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if(tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
            RenException rrException = new RenException("token失效，请重新登录");
            rrException.setCode(8000);
            throw rrException;
        }
        this.userId = tokenEntity.getUserId();
        this.session = session;
        this.token = token;
        webSocketSet.add(this);
        if (webSocketMap.containsKey(token)) {
            webSocketMap.remove(token);
            webSocketMap.put(token, this);
        } else {
            webSocketMap.put(token, this);
            addOnlineCount();
        }
        log.info("[连接ID:{}] 建立连接, 当前连接数:{}", this.token, getOnlineCount());
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(token)) {
            webSocketMap.remove(token);
            subOnlineCount();
        }
        log.info("[连接ID:{}] 断开连接, 当前连接数:{}", token, getOnlineCount());
    }
    /**
     * 发送错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[连接ID:{}] 错误原因:{}", this.token, error.getMessage());
        error.printStackTrace();
    }
    /**
     * 接收到客户端消息
     * @param msg
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("[连接ID:{}] 收到消息:{}", this.token, message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String modelId = jsonObject.getString("modelId");
        String msg = jsonObject.getString("msg");
        //判断用户是否为VIP或者是否有充足次数
        commonService.onMessage(modelId,msg,this);
    }
    /**
     * 获取当前连接数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 当前连接数加一
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 当前连接数减一
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
