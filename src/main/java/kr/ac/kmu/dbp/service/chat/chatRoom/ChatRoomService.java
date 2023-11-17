package kr.ac.kmu.dbp.service.chat.chatRoom;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;
import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;


public interface ChatRoomService {
    public void create(ChatRoomDtoCreate chatRoomDtoCreate);
    public ChatRoom readByPid(int pid);
}
