package io.renren.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unfbx.chatgpt.entity.chat.ChatChoice;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import io.renren.commom.AsyncService;
import io.renren.commom.CommonService;
import io.renren.entity.UserEntity;
import io.renren.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

/**
 * 描述：OpenAI流式输出Socket接收
 *
 * @author https:www.unfbx.com
 * @date 2023-03-23
 */
@Slf4j
public class OpenAIWebSocketEventSourceListener extends EventSourceListener {

    private Session session;
    private Long userId;
    private String msg;
    private String lastMessage = "";


    private AsyncService asyncService;
    public OpenAIWebSocketEventSourceListener(Session session,Long userId,AsyncService asyncService,String msg) {
        this.session = session;
        this.userId = userId;
        this.msg = msg;
        this.asyncService = asyncService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI建立sse连接...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI返回数据：{}", data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI返回数据结束了");
            session.getBasicRemote().sendText("[DONE]");
            //存提问记录和回答
            log.info("问题是:"+msg);
            log.info("回答是:"+lastMessage);
            asyncService.saveQuestionAndAnswer(userId,msg,lastMessage);
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // 读取Json
        List<ChatChoice> choices = completionResponse.getChoices();
        if (choices == null || choices.isEmpty()) {
            return;
        }
        Message delta = choices.get(0).getDelta();
        String text = delta.getContent();
        if (text != null) {
            lastMessage += text;
            session.getBasicRemote().sendText(text);
        }
//        String delta = mapper.writeValueAsString(completionResponse.getChoices().get(0).getDelta());
//        session.getBasicRemote().sendText(delta);
//        if(delta != null){
////            lastMessage += delta.getc;
//        }
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI关闭sse连接...");
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }
}
