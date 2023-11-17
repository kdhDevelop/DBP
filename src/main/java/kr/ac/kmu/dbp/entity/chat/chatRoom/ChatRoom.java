package kr.ac.kmu.dbp.entity.chat.chatRoom;

import kr.ac.kmu.dbp.dto.chat.chatRoom.ChatRoomDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoom {

    private int pid;
    private String name;
    private final List<Employee> joinEmployeeList = new ArrayList<>();

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

    public void addEmployee(Employee employee) {
        joinEmployeeList.add(employee);
    }

    public void removeEmployee(Employee employee) {
        joinEmployeeList.remove(employee);
    }

    public List<Employee> getEmployeeList() {
        return joinEmployeeList;
    }
}
