package kr.ac.kmu.dbp.service.chat.chatRoom;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;


public interface ChatRoomService {
    public void create(ChatRoomDtoCreate chatRoomDtoCreate);
    public void readByPid(int pid);
}
