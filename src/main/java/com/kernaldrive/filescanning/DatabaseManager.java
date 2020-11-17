package com.kernaldrive.filescanning;

import com.kernaldrive.metadata.Movie;

import java.sql.*;

public class DatabaseManager {

    private Connection con;
    private boolean hasData = false;

    public DatabaseManager() throws SQLException, ClassNotFoundException {
        getConnection();
    }

    public ResultSet displayMovies() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM movies");
        return res;
    }

    public ResultSet findMovie(String path) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM movies WHERE path = "+path);
        if(res!=null){
            return res;
        }
        else{
            return null;
        }
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Media.db");
        initialise();
    }

    private void initialise() throws SQLException{
        if(!hasData){
            hasData = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='movies'");
            if(!res.next()){
                System.out.println("Building the user table with prepopulated values.");
                //building table
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE  movies(id integer, tmdbID integer, title varchar(60), year varchar(60), genre varchar(60), cover varchar(60), path varchar(300), primary key(id));");

            }
        }
    }

    public void addMovie(Movie movie) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement(("INSERT  INTO movies values(?, ?, ?, ?, ?, ?, ?)"));
        prep.setInt(2, movie.getTmdbID());
        prep.setString(3, movie.getTitle());
        prep.setString(4, movie.getYear());
        prep.setString(5, "WIP");
        prep.setString(6, movie.getPosterPath());
        prep.setString(6,  movie.getFilePath());
        prep.execute();
    }
}