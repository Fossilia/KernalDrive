package com.kernaldrive.filescanning;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;

import java.io.File;
import java.sql.*;

public class GroupDBManager {

    private Connection con;
    private boolean hasData = false;

    public GroupDBManager() throws SQLException, ClassNotFoundException {
        getConnection();
    }

    /**
     * Create connection with database file
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Media.db");
        initialise();
    }

    /**
     * Creates the group table and the groupPath tables
     * @throws SQLException
     */
    private void initialise() throws SQLException{
        if(!hasData){
            hasData = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='movieGroup'");
            if(!res.next()){ //if the table does not exit
                System.out.println("Building the group table with prepopulated values.");
                //building table
                Statement state2 = con.createStatement();
                Statement state3 = con.createStatement();
                state2.execute("CREATE TABLE movieGroup(id integer, name varchar(60), primary key(id));");
                state3.execute("CREATE TABLE groupPath(id integer, groupName varchar(60), path varchar(300), primary key(id));");
                state2.close();
                state3.close();
            }
        }
    }

    /**
     * Used to add a Movie group to the group table and adds its paths to the group paths table,
     * it gets the groups name to store in the group table, and gets its paths to add to the
     * groupPath table
     * @param group movie group which will have its data added to the group
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addGroup(MovieGroup group) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement(("INSERT  INTO movieGroup values(?, ?)")); //insert group name into group table
        prep.setString(2, group.getName());
        prep.execute();
        prep.close();
        for(File path: group.getPaths()){ //inserting paths into grouppath table
            PreparedStatement prep2 = con.prepareStatement(("INSERT  INTO groupPath values(?, ?, ?)"));
            prep2.setString(2, group.getName());
            prep2.setString(3, path.getPath());
            prep2.execute();
            prep2.close();
        }
    }

    /**
     * Gets all the groups in the table
     * @return resultset which has all the groups in it
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet getGroups() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM movieGroup");
        //state.close();
        return res;
    }

    /**
     * Gets all the paths with the inputted group name
     * @param groupName group name the paths that are fetched are supposed to be associated with
     * @return resultset with all paths of inputted group name
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet getGroupPaths(String groupName)  {
        try {
            if (con == null) {
                getConnection();
            }


            PreparedStatement state = con.prepareStatement("SELECT * FROM groupPath WHERE groupName = ?");
            state.setString(1, groupName);
            ResultSet res = state.executeQuery();
            //state.close();
            return res;
        }
         catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}