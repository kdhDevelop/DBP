package kr.ac.kmu.dbp.controller.webSocket;

import kr.ac.kmu.dbp.service.chat.chatRoom.ChatRoomService;
import kr.ac.kmu.dbp.service.chat.chatRoom.ChatRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatControllerApi {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatControllerApi(ChatRoomServiceImpl chatRoomServiceImpl) {
        this.chatRoomService = chatRoomServiceImpl;
    }
}
