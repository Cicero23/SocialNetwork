package socialnetwork.repository.paging;

import socialnetwork.domain.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginatorDBEvents extends Paginator<Event> {
    private String url;
    private String username;
    private String password;

    public PaginatorDBEvents(String url, String username, String password, Pageable pageable) {
        super(pageable);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Page<Event> paginate() {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            Map<Long,Event> entities = new HashMap<Long,Event>();
            String sqlSelect = "SELECT * FROM events ORDER BY id_event";
            PreparedStatement statementSelectEvent = connection.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statementSelectEvent.executeQuery();
            resultSet.absolute(Math.max((pageable.getPageNumber()-1) * pageable.getPageSize(),0));
            int nrOfRows = 0;
            while(resultSet.next() && nrOfRows < pageable.getPageSize()){
                Long id = resultSet.getLong("id_event");
                String name = resultSet.getString("name");
                String desc = resultSet.getString("description");
                LocalDateTime ld = resultSet.getTimestamp("date_event").toLocalDateTime();
                Long id_user = resultSet.getLong("id_user");
                Event event = new Event(name,desc,ld,id_user);
                event.setId(id);
                String sqlSelectPart = "SELECT * FROM events_participants WHERE id_event= ?";
                PreparedStatement statementSelectPart = connection.prepareStatement(sqlSelectPart);
                statementSelectPart.setLong(1,id);
                ResultSet resultSetDest = statementSelectPart.executeQuery();
                List<Long> part = new ArrayList<Long>();
                while (resultSetDest.next()){
                    Long idP = resultSetDest.getLong("id_user");
                    part.add(idP);
                }
                event.setParticipants(part);
                entities.put(id,event);
                nrOfRows++;
            }
            return new PageImplementation<Event>(super.pageable,entities.values().stream());
        }
        catch (SQLException e) {
            return null;
        }


    }
}
