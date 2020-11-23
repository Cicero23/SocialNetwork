package socialnetwork.repository.database;

import org.graalvm.compiler.core.phases.EconomyHighTier;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDBRepo implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;

    public MessageDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Message findOne(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM message WHERE id_message = ?";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            statementSelectMessage.setLong(1,id);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_message");
                Long from = resultSet.getLong("id_from");
                LocalDateTime ld = resultSet.getTimestamp("data").toLocalDateTime();
                String mesaj = resultSet.getString("mesaj");
                Long replayId = resultSet.getLong("replay_id");
                Message msg;
                if(replayId == null) {
                    msg= new Message(from,ld,mesaj);
                    msg.setId(id1);

                }
                else {
                    msg = new ReplayMessage(from,ld,mesaj,replayId);
                    msg.setId(id1);

                }
                String sqlSelectDest = "SELECT * FROM destinatie_message WHERE id_message = ?";
                PreparedStatement statementSelectDestinar = connection.prepareStatement(sqlSelectDest);
                statementSelectDestinar.setLong(1,id);
                ResultSet resultSetDest = statementSelectDestinar.executeQuery();
                List<Long> dest = new ArrayList<Long>();
                while (resultSetDest.next()){
                    Long idDest = resultSetDest.getLong("id_user");
                    dest.add(idDest);
                }
                msg.setTo(dest);
                return msg;

            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            Map<Long,Message> entities = new HashMap<Long,Message>();
            String sqlSelect = "SELECT * FROM message";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            while(resultSet.next()){
                Long id = resultSet.getLong("id_message");
                Long from = resultSet.getLong("id_from");
                LocalDateTime ld = resultSet.getTimestamp("data").toLocalDateTime();
                String mesaj = resultSet.getString("mesaj");
                Long replayId = resultSet.getLong("replay_id");
                Message msg;
                if(replayId == 0) {
                    msg= new Message(from,ld,mesaj);
                    msg.setId(id);

                }
                else {
                    msg = new ReplayMessage(from,ld,mesaj,replayId);
                    msg.setId(id);

                }
                String sqlSelectDest = "SELECT * FROM destinatie_message WHERE id_message = ?";
                PreparedStatement statementSelectDestinar = connection.prepareStatement(sqlSelectDest);
                statementSelectDestinar.setLong(1,id);
                ResultSet resultSetDest = statementSelectDestinar.executeQuery();
                List<Long> dest = new ArrayList<Long>();
                while (resultSetDest.next()){
                    Long idDest = resultSetDest.getLong("id_user");
                    dest.add(idDest);
                }
                msg.setTo(dest);
                entities.put(id,msg);

            }
            entities.forEach((x,y)->{
                if(y.getClass() == ReplayMessage.class){
                    ((ReplayMessage) y).setMsg(entities.get(((ReplayMessage) y).getMsg().getId()));
                }
            });
            return entities.values();
        }
        catch (SQLException e) {
            return null;
        }



    }

    @Override
    public Message save(Message entity) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            PreparedStatement statementInsert;
            if(entity.getClass() == Message.class) {
                String sqlInsert = "INSERT INTO message(id_from, data, mesaj) VALUES (?, ?, ?)";
                statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setLong(1, entity.getFrom());
                statementInsert.setObject(2, entity.getData());
                statementInsert.setString(3, entity.getMesaj());
            }
            else{
                String sqlInsert = "INSERT INTO message(id_from, data, mesaj, replay_id) VALUES (?, ?, ?, ?)";
                statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setLong(1, entity.getFrom());
                statementInsert.setObject(2, entity.getData());
                statementInsert.setString(3, entity.getMesaj());
                statementInsert.setLong(4, ((ReplayMessage) entity).getMsg().getId());
            }
            statementInsert.executeUpdate();

            String sqlSelectId = "SELECT id_message FROM message WHERE id_from = ? AND data = ? AND mesaj = ?  ";
            PreparedStatement statementSelectId = connection.prepareStatement(sqlSelectId);
            statementSelectId.setLong(1,entity.getFrom());
            statementSelectId.setObject(2,entity.getData());
            statementSelectId.setString(3,entity.getMesaj());

            ResultSet resultSet = statementSelectId.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id_message");
                entity.setId(id);
                entity.getTo().forEach(x -> {
                    try {
                        String sqlInsertM = "INSERT INTO destinatie_message(id_user,id_message) VALUES (?, ?)";
                        PreparedStatement statementInsertM = connection.prepareStatement(sqlInsertM);
                        statementInsertM.setLong(1, x);
                        statementInsertM.setLong(2, entity.getId());
                        statementInsertM.executeUpdate();

                    }
                    catch (SQLException e){
                        ;
                    }
                });


            }
            return entity;
        }
        catch (SQLException ex){
            return entity;
        }

    }

    @Override
    public Message delete(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM message WHERE id_message = ?";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            statementSelectMessage.setLong(1,id);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_message");
                Long from = resultSet.getLong("id_from");
                LocalDateTime ld = resultSet.getTimestamp("data").toLocalDateTime();
                String mesaj = resultSet.getString("mesaj");
                Long replayId = resultSet.getLong("replay_id");
                Message msg;
                if(replayId == null) {
                    msg= new Message(from,ld,mesaj);
                    msg.setId(id1);

                }
                else {
                    msg = new ReplayMessage(from,ld,mesaj,replayId);
                    msg.setId(id1);

                }
                String sqlSelectDest = "SELECT * FROM destinatie_message WHERE id_message = ?";
                PreparedStatement statementSelectDestinar = connection.prepareStatement(sqlSelectDest);
                statementSelectDestinar.setLong(1,id);
                ResultSet resultSetDest = statementSelectDestinar.executeQuery();
                List<Long> dest = new ArrayList<Long>();
                while (resultSetDest.next()){
                    Long idDest = resultSetDest.getLong("id_user");
                    dest.add(idDest);
                }
                msg.setTo(dest);
                String sqlDelete = "DELETE FROM message WHERE id_message = ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setLong(1, id);
                statementDelete.executeUpdate();
                return  msg;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }

    }

    @Override
    public Message update(Message entity) {
        return null;
    }
}
