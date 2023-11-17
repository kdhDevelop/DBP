package kr.ac.kmu.dbp.handler;

import kr.ac.kmu.dbp.entity.chat.chatRoom.ChatRoom;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.service.chat.chatRoom.ChatRoomServiceImpl;
import kr.ac.kmu.dbp.service.employee.EmployeeService;
import kr.ac.kmu.dbp.service.employee.EmployeeServiceImpl;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    enum FunctionType {
        INIT, JOIN, LEAVE, CHAT
    }

    private final EmployeeService employeeService;
    private final ChatRoomServiceImpl chatRoomService;

    public CustomWebSocketHandler(EmployeeServiceImpl employeeServiceImpl, ChatRoomServiceImpl chatRoomServiceImpl) {
        this.employeeService = employeeServiceImpl;
        this.chatRoomService = chatRoomServiceImpl;
    }


    private static final ConcurrentHashMap<WebSocketSession, Employee> sessionEmployeeList = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Employee, WebSocketSession> employeeSessionList = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Employee, Integer> employeeChatRoomList = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Employee employee = sessionEmployeeList.get(session);
        int chatRoomPid = employeeChatRoomList.get(employee);
        ChatRoom chatRoom = chatRoomService.readByPid(chatRoomPid);
        chatRoom.removeEmployee(employee);
        employeeChatRoomList.remove(employee);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receiveMessage = new String(message.asBytes());
        JSONObject receiveMessageJsonObject = new JSONObject(receiveMessage);

        FunctionType functionType = FunctionType.valueOf(receiveMessageJsonObject.getString("function"));

        switch (functionType) {
            case INIT : initFunction(session, receiveMessageJsonObject.getJSONObject("data"));
                break;
            case JOIN : joinFunction(session, receiveMessageJsonObject.getJSONObject("data"));
                break;
            case LEAVE : leaveFunction(session, receiveMessageJsonObject.getJSONObject("data"));
                break;
            case CHAT : chatFunction(session, message);
                break;
        }
    }

    private void initFunction(WebSocketSession webSocketSession, JSONObject receiveDataJsonObject) throws IOException {
        int employeePid = receiveDataJsonObject.getInt("employeePid");
        Employee employee = employeeService.readByPid(employeePid);
        sessionEmployeeList.put(webSocketSession, employee);
        employeeSessionList.put(employee, webSocketSession);

        JSONObject sendMessageJsonObject = new JSONObject();
        JSONObject sendDataJsonObject = new JSONObject();
        sendDataJsonObject.put("sessionId", webSocketSession.getId());
        sendMessageJsonObject.put("data", sendDataJsonObject);

        webSocketSession.sendMessage(new TextMessage(sendMessageJsonObject.toString()));
    }

    private void joinFunction(WebSocketSession webSocketSession, JSONObject receiveDataJsonObject) {
        Employee employee = sessionEmployeeList.get(webSocketSession);
        int chatRoomPid = receiveDataJsonObject.getInt("chatRoomPid");
        employeeChatRoomList.put(employee, chatRoomPid);
        ChatRoom chatRoom = chatRoomService.readByPid(chatRoomPid);
        chatRoom.addEmployee(employee);
    }

    private void leaveFunction(WebSocketSession webSocketSession, JSONObject receiveDataJsonObject) {
        Employee employee = sessionEmployeeList.get(webSocketSession);
        int chatRoomPid = receiveDataJsonObject.getInt("chatRoomPid");
        employeeChatRoomList.remove(employee);
        ChatRoom chatRoom = chatRoomService.readByPid(chatRoomPid);
        chatRoom.removeEmployee(employee);
    }

    private void chatFunction(WebSocketSession webSocketSession, TextMessage textMessage) throws IOException {
        Employee sender = sessionEmployeeList.get(webSocketSession);
        int chatRoomPid = employeeChatRoomList.get(sender);
        ChatRoom chatRoom = chatRoomService.readByPid(chatRoomPid);
        List<Employee> chatRoomEmployeeList = chatRoom.getEmployeeList();

        for (Employee employee : chatRoomEmployeeList) {
            WebSocketSession session = employeeSessionList.get(employee);
            session.sendMessage(textMessage);
        }
    }
}
