package com.kernaldrive.filescanning;

import com.kernaldrive.metadata.Movie;

import java.sql.*;

public class MovieDBManager {

    private Connection con;
    private boolean hasData = false;

    public MovieDBManager() throws SQLException, ClassNotFoundException {
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

        PreparedStatement state = con.prepareStatement("SELECT * FROM movies WHERE path = ?");
        state.setString(1, path);
        ResultSet res = state.executeQuery();
        if(res.next()){
            PreparedStatement state2 = con.prepareStatement("UPDATE movies SET scanned = 1 WHERE path = ?");
            state2.setString(1, path);
            state2.executeUpdate();
            //state2.close();
            return res;
        }
        else{
            return null;
        }
    }

    public void clearDatabaseScans() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        state.execute("UPDATE movies SET scanned = 0");
        state.close();

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
                System.out.println("Building the Movie table with prepopulated values.");
                //building table
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE  movies(id integer, tmdbID integer, title varchar(60), year varchar(60), genre varchar(60), cover varchar(60), banner varchar(100), mainposter varchar(100), path varchar(300), scanned integer, primary key(id));");
                state2.close();
            }
        }
    }

    public void addMovie(Movie movie) throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        /*
        PreparedStatement prep = con.prepareStatement(("INSERT  INTO movies values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
        prep.setInt(2, movie.getTmdbID());
        prep.setString(3, movie.getTitle());
        prep.setString(4, movie.getYear());
        prep.setString(5, "WIP");
        prep.setString(6, movie.getPosterPath());
        prep.setString(7, movie.getBannerPath());
        prep.setString(8, movie.getMoviePagePoster());
        prep.setString(9, movie.getFilePath());
        prep.setInt(10,  1);
        prep.execute();
        prep.close();
         */


        PreparedStatement prep = con.prepareStatement(("INSERT  INTO movies values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
        prep.setInt(2, movie.getTmdbID());
        prep.setString(3, movie.getTitle());
        prep.setString(4, movie.getYear());
        prep.setString(5, "WIP");
        prep.setString(6, movie.getPosterPath());
        prep.setString(7, movie.getBannerPath());
        prep.setString(8, movie.getMoviePagePoster());
        prep.setString(9, movie.getFilePath());
        prep.setInt(10,  1);
        prep.execute();
        prep.close();
    }

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}