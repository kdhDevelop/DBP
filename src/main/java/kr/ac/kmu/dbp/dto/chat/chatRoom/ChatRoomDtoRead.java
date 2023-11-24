package kr.ac.kmu.dbp.dto.chat.chatRoom;

import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRoomDtoRead {
    private int pid;
    private String name;
    private final List<Integer> joinEmployeePidList = new ArrayList<>();

    public ChatRoomDtoRead(ChatRoom chatRoom) {
        this.pid = chatRoom.getPid();
        this.name = chatRoom.getName();

        for (Employee employee : chatRoom.getEmployeeList()) {
            joinEmployeePidList.add(employee.getPid());
        }
    }
}
