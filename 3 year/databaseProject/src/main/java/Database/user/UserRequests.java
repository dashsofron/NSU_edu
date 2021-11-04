package Database.user;

import Database.UserRole;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addUser(User user){

        int userId=getNextUserId();
        user.setUserId(userId);

        String sqlAddUser = "INSERT INTO libraryAdmin.user_table VALUES (" +
                userId+", " +
                "'" + user.getUsername() + "' ," +
                "'" + user.getPassword() + "' ," +
                "'" + user.getUserRole() + "' ," +
                user.getReaderId() +
                ")";
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddUser);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new user info");
            throwables.printStackTrace();

        }
        return  userId;


    }

    public User getUser(Integer userId){

        String sqlGetUser = "SELECT  * FROM libraryAdmin.user_table " +
                "WHERE user_id=" + userId;
        User user = new User();
        ResultSet rs = null;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetUser);
            rs = preStatementReader.executeQuery(sqlGetUser);

        } catch (SQLException throwables) {
            System.err.println("can't get user");
            throwables.printStackTrace();

        }
        try {
            if (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(UserRole.valueOf(rs.getString("user_role")));
                user.setReaderId(rs.getInt("reader_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;


    }

    public List<User> getUsers(){

        String sqlGetUser = "SELECT  * FROM libraryAdmin.user_table ";
        List<User> users = new LinkedList<>();
        ResultSet rs = null;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetUser);
            rs = preStatementReader.executeQuery(sqlGetUser);

            while (rs.next()) {
                User user=new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(UserRole.valueOf(rs.getString("user_role")));
                user.setReaderId(rs.getInt("reader_id"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get user");
            throwables.printStackTrace();
        }
        return users;


    }


    public User getUserByName(String username){

        String sqlGetUser = "SELECT  * FROM libraryAdmin.user_table " +
                "WHERE username='" + username+"'";
        User user = new User();
        ResultSet rs = null;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetUser);
            rs = preStatementReader.executeQuery(sqlGetUser);

        } catch (SQLException throwables) {
            System.err.println("can't get user");
            throwables.printStackTrace();

        }
        try {
            if (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(UserRole.valueOf(rs.getString("user_role")));
                user.setReaderId(rs.getInt("reader_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;


    }

    public void deleteUser(Integer userId){
        String sqlDeleteReader = "DELETE FROM libraryAdmin.user_table WHERE user_id=" + userId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteReader);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete user info");
        }
    }

    public void updateUser(User user){

        String sqlUpdateUser = "UPDATE libraryAdmin.user_table SET ";
        sqlUpdateUser += "username= '" + user.getUsername() + "' ";
        sqlUpdateUser += ",password= '" + user.getPassword()+ "' ";
        sqlUpdateUser += ",user_role= '" + user.getUserRole()+ "' ";
        sqlUpdateUser += ",reader_id= " + user.getReaderId();
        sqlUpdateUser += "WHERE user_id=" + user.getUserId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateUser);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update user info");
            throwables.printStackTrace();
        }

    }


    public Integer getNextUserId(){
        String sql = "select sq_user.nextval from DUAL";
        Integer nextID=null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }

    public void createNewUser(String login, String password) throws SQLException {
        conn.setAutoCommit(false);
        String createSql="create user "+login+" identified by "+password;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(createSql);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't create new user");
            throwables.printStackTrace();

        }

        String grantSql="grant library_user to  "+login;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(grantSql);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't grant role");
            throwables.printStackTrace();

        }
        conn.commit();
        conn.setAutoCommit(true);
    }


    public Integer createNewUser(User user) throws SQLException {
        conn.setAutoCommit(false);
        String createSql="create user "+user.getUsername()+" identified by "+user.getPassword();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(createSql);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't create new user");
            conn.rollback();
            conn.setAutoCommit(true);
            throwables.printStackTrace();

        }

        String grantSql="grant library_user to  "+user.getUsername();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(grantSql);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't grant role");
            throwables.printStackTrace();
            conn.rollback();
            conn.setAutoCommit(true);

        }
        Integer userId=addUser(user);
        conn.commit();
        conn.setAutoCommit(true);
        return userId;
    }

}
