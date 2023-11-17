package kr.ac.kmu.dbp.controller.webSocket;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;
import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.service.chat.chatRoom.ChatRoomService;
import kr.ac.kmu.dbp.service.chat.chatRoom.ChatRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatRoomControllerApi {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomControllerApi(ChatRoomServiceImpl chatRoomServiceImpl) {
        this.chatRoomService = chatRoomServiceImpl;
    }

    @PostMapping("/chat")
    public void create(@RequestBody ChatRoomDtoCreate chatRoomDtoCreate) {
        chatRoomService.create(chatRoomDtoCreate);
    }

    @GetMapping("/chat/list")
    public List<ChatRoom> readAll() {
        return chatRoomService.readAll();
    }
}
