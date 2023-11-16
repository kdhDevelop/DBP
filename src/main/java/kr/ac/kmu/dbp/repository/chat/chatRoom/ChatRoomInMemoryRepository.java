package kr.ac.kmu.dbp.repository.chat.chatRoom;

import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.entity.employee.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatRoomInMemoryRepository implements ChatRoomRepository {

    //채팅방(ChatRoom)와 해당 채팅방에 있는 사람들의 목록(Employee - Vector<Employee)
    private static final ConcurrentHashMap<ChatRoom, Vector<Employee>> chatRoomList = new ConcurrentHashMap<>();

    //사람(Employee)에 해당하는 연결(WebSocketSession)
    private static final ConcurrentHashMap<Employee, WebSocketSession> userSessionList = new ConcurrentHashMap<>();

}
