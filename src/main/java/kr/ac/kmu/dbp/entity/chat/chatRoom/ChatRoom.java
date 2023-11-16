package kr.ac.kmu.dbp.entity.chat.chatRoom;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoom {

    private int pid;
    private String name;
    private final List<Integer> joinUserPidList = new ArrayList<>();

    public ChatRoom(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public ChatRoom(String name) {
        this.name = name;
    }

    public ChatRoom(ChatRoomDtoCreate chatRoomDtoCreate) {
        this.name = chatRoomDtoCreate.getName();
    }

    public void addUserPid(int userPid) {
        joinUserPidList.add(userPid);
    }

    public void removeUserPid(int userPid) {
        joinUserPidList.remove(userPid);
    }

    public List<Integer> getUserPidList() {
        return joinUserPidList;
    }
}
