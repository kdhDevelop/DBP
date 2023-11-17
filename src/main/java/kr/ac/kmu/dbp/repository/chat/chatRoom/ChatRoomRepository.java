package kr.ac.kmu.dbp.repository.chat.chatRoom;

import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;

import java.util.List;

public interface ChatRoomRepository {
    public void create(ChatRoom chatRoom);
    public ChatRoom readByPid(int pid);
    public List<ChatRoom> readAll();
}
