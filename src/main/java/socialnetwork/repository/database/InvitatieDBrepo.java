package socialnetwork.repository.database;

import socialnetwork.domain.Invitatie;
import socialnetwork.domain.Message;
import socialnetwork.domain.ReplayMessage;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitatieDBrepo implements Repository<Long, Invitatie> {
    private String url;
    private String username;
    private String password;

    public InvitatieDBrepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Invitatie findOne(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM invitatie WHERE id_invitatie = ?";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            statementSelectMessage.setLong(1,id);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_invitatie");
                Long from = resultSet.getLong("id_from");
                Long to = resultSet.getLong("id_to");
                LocalDate ld = resultSet.getDate("data").toLocalDate();
                int stare = resultSet.getInt("stare");
                Invitatie invitatie = new Invitatie(from,to,ld,stare);
                invitatie.setId(id1);
                return invitatie;
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Iterable<Invitatie> findAll() {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            Map<Long,Invitatie> entities = new HashMap<Long,Invitatie>();
            String sqlSelect = "SELECT * FROM invitatie";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            while(resultSet.next()){
                Long id1 = resultSet.getLong("id_invitatie");
                Long from = resultSet.getLong("id_from");
                Long to = resultSet.getLong("id_to");
                LocalDate ld = resultSet.getDate("data").toLocalDate();
                int stare = resultSet.getInt("stare");
                Invitatie invitatie = new Invitatie(from,to,ld,stare);
                invitatie.setId(id1);
                entities.put(id1,invitatie);

            }
            return entities.values();
        }
        catch (SQLException e) {
            return null;
        }



    }

    @Override
    public Invitatie save(Invitatie entity) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlInsert = "INSERT INTO invitatie(id_from, id_to,data,stare) VALUES (?, ?, ?, ?)";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setLong(1, entity.getId_from());
            statementInsert.setLong(2, entity.getId_to());
            statementInsert.setObject(3, entity.getData());
            statementInsert.setLong(4, entity.getStare());
            statementInsert.executeUpdate();
            return null;

        }
        catch (SQLException ex){
            return entity;
        }

    }

    @Override
    public Invitatie delete(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM invitatie WHERE id_invitatie = ?";
            PreparedStatement statementSelectMessage = connection.prepareStatement(sqlSelect);
            statementSelectMessage.setLong(1,id);
            ResultSet resultSet = statementSelectMessage.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_invitatie");
                Long from = resultSet.getLong("id_from");
                Long to = resultSet.getLong("id_to");
                LocalDate ld = resultSet.getDate("data").toLocalDate();
                Integer stare = resultSet.getInt("stare");
                Invitatie invitatie=  new Invitatie(from,to,ld,stare);
                invitatie.setId(id1);
                String sqlDelete = "DELETE FROM invitatie WHERE id_invitatie= ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setLong(1, id);
                statementDelete.executeUpdate();
                return  invitatie;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Invitatie update(Invitatie entity) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlInsert = "UPDATE invitatie SET id_from = ?, id_to = ?,data = ?,stare = ? WHERE id_invitatie = ? ";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setLong(1, entity.getId_from());
            statementInsert.setLong(2, entity.getId_to());
            statementInsert.setObject(3, entity.getData());
            statementInsert.setLong(4, entity.getStare());
            statementInsert.setLong(5, entity.getId());
            statementInsert.executeUpdate();
            return null;

        }
        catch (SQLException ex){
            return entity;
        }
    }
}
