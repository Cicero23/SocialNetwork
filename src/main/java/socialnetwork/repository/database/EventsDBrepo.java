package socialnetwork.repository.database;

import socialnetwork.domain.Event;
import socialnetwork.domain.Invitatie;
import socialnetwork.domain.Message;
import socialnetwork.domain.ReplayMessage;
import socialnetwork.repository.Repository;
import socialnetwork.repository.paging.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventsDBrepo implements PagingRepository<Long, Event> {
    private String url;
    private String username;
    private String password;

    public EventsDBrepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Event findOne(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM events WHERE id_event = ?";
            PreparedStatement statementSelectEvent = connection.prepareStatement(sqlSelect);
            statementSelectEvent.setLong(1,id);
            ResultSet resultSet = statementSelectEvent.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_event");
                String name = resultSet.getString("name");
                String desc = resultSet.getString("description");
                LocalDateTime ld = resultSet.getTimestamp("date_event").toLocalDateTime();
                Long id_user = resultSet.getLong("id_user");
                Event event = new Event(name,desc,ld,id_user);
                event.setId(id);
                String sqlSelectPart = "SELECT * FROM events_participants WHERE id_event = ?";
                PreparedStatement statementSelectPart = connection.prepareStatement(sqlSelectPart);
                statementSelectPart.setLong(1,id);
                ResultSet resultSetDest = statementSelectPart.executeQuery();
                List<Long> part = new ArrayList<Long>();
                while (resultSetDest.next()){
                    Long idP = resultSetDest.getLong("id_user");
                    part.add(idP);
                }
                event.setParticipants(part);
                return event;

            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Event> findAll() {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            Map<Long,Event> entities = new HashMap<Long,Event>();
            String sqlSelect = "SELECT * FROM events";
            PreparedStatement statementSelectEvent = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = statementSelectEvent.executeQuery();
            while(resultSet.next()){
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

            }
            return entities.values();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event save(Event entity) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlInsert = "INSERT INTO events(name,description,date_event,id_user) VALUES (?,?,?,?)";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setString(1, entity.getName());
            statementInsert.setString(2, entity.getDescription());
            statementInsert.setObject(3, entity.getDate());
            statementInsert.setLong(4,entity.getId_user());
            System.out.println(statementInsert.toString());
            statementInsert.executeUpdate();
            return null;

        }
        catch (SQLException ex){
            return entity;
        }

    }

    /**
     *
     * @param idPart
     * @param idEvent
     * @return
     */

    public Long savePart(Long idPart,Long idEvent){
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlInsert = "INSERT INTO events_participants(id_user,id_event) VALUES (?,?)";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setLong(1, idPart);
            statementInsert.setLong(2, idEvent);

            System.out.println(statementInsert.toString());
            statementInsert.executeUpdate();
            return null;

        }
        catch (SQLException ex){
            return idPart;
        }
    }

    public boolean findPart(Long idPart,Long idEvent){
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlExist= "SELECT * FROM events_participants WHERE id_user = ? AND id_event= ?";
            PreparedStatement statementExist = connection.prepareStatement(sqlExist);
            statementExist.setLong(1, idPart);
            statementExist.setLong(2, idEvent);
            ResultSet resultSet = statementExist.executeQuery();
            if(resultSet.next()){
                return  true;
            }

            return false;

        }
        catch (SQLException ex){
            return false;
        }
    }

    @Override
    public Event delete(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM events WHERE id_event = ?";
            PreparedStatement statementSelectEvent = connection.prepareStatement(sqlSelect);
            statementSelectEvent.setLong(1,id);
            ResultSet resultSet = statementSelectEvent.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_event");
                String name = resultSet.getString("name");
                String desc = resultSet.getString("description");
                LocalDateTime ld = resultSet.getTimestamp("date_event").toLocalDateTime();
                Long id_user = resultSet.getLong("id_user");
                Event event = new Event(name,desc,ld,id_user);
                event.setId(id);
                String sqlSelectPart = "DELETE FROM events_participants WHERE id_event= ?";
                PreparedStatement statementSelectPart = connection.prepareStatement(sqlSelectPart);
                statementSelectPart.setLong(1,id);
                statementSelectPart.executeUpdate();


                String sqlDelete = "DELETE FROM events WHERE id_event= ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setLong(1, id);
                statementDelete.executeUpdate();
                return event;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Event update(Event entity) {
        return null;
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        PaginatorDBEvents paginator = new PaginatorDBEvents(this.url,this.username,this.password,pageable);
        return paginator.paginate();
    }
}
