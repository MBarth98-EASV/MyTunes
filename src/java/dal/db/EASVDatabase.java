package dal.db;

import be.SongModel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EASVDatabase 
{
    private SQLServerDataSource dataSource;

    public EASVDatabase()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("CSe21A_29_MyTunes_3");
        dataSource.setUser("CSe21A_29");
        dataSource.setPassword("itsikkerhed");
        dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException
    {
        EASVDatabase databaseConnector = new EASVDatabase();

        try (Connection connection = databaseConnector.getConnection()) {
            System.out.println("Is it open? " + !connection.isClosed());
        }
    }


    /**
     * Add song to SQL database table.
      */
    public void addSong(String songName, String art, int dura, String sauce, String fpath, String genre, String album)
    {

        String sql = "INSERT INTO Songs (title, artists, duration, source, filepath, genre, album) VALUES ('" + songName + "', '" + art + "', '" + dura + "', '" + sauce + "', '" + fpath + "', '" + genre + "', '" + album + "')";

        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Remove Songs from SQL database.
      */
    public void removeSong(int id)
    {
        try {
            String sql = "DELETE FROM Songs WHERE id LIKE '%" + id + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSong(String songName)
    {
        try {
            String sql = "DELETE FROM Songs WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters from the SQL database.
     */

    public List<SongModel> getAllSongs()
    {
        String sql = "SELECT * FROM Songs";
        List<SongModel> songs = new ArrayList<>();

        String songTitle;
        int songId;
        String songArtist;
        String songLocation;
        String songAlbum;
        String songGenre;

        try
        {
            System.out.println("trying to get all songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");

                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, 0, "local", songLocation));
            }

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }
  
    /**
     * Updater for the SQL database.
     */
    public void updateSong(String column, String condition)
    {
        try {
            String sql = "SELECT * FROM Songs SET " + column + " WHERE LIKE '%" + condition + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Database sorter
     */
    public void sortList(String table, String column, String order)
    {
        try
        {
        String sql = "SELECT * FROM " + table + " ORDER BY " + column + order;

        Statement statement = dataSource.getConnection().createStatement();
        statement.executeQuery(sql);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

 //TODO: Make methods applicable for Title and Artist which takes two parameters.

    public List<SongModel> filterEqualsParameter(String filterType, String filterType2, String filterParameter){
        String sql = null;
        if (!filterType2.equals("NONE")){
            sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%' " +
                    "OR " + filterType2 + " LIKE '%" + filterParameter + "%'";
        }
        else sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%'";

        List<SongModel> songs = new ArrayList<>();

        String songTitle;
        int songId;
        String songArtist;
        String songLocation;
        String songGenre;
        String songAlbum;

        try
        {
            System.out.println("trying to get all filtered songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");

                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, 0, "local", songLocation));
            }

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }


    /** Select every value in the wanted column and returns it as a list of Strings.
     * @param filterType The column to select from. The filter is determined by
     * the user in the Combobox Filter.
     * @return
     */
    public List<String> allAvailableByParameter(String filterType){
        String sql = "SELECT DISTINCT " + filterType + " FROM dbo.Songs " ;
        List<String> SearchEntryFilter = new ArrayList<>();


        try
        {
            System.out.println("trying to get all songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                String chosenFilter = result.getString(1);
                if (chosenFilter == null || chosenFilter.isEmpty()){
                    chosenFilter = "N/A";
                }
                SearchEntryFilter.add(chosenFilter);
            }

            return SearchEntryFilter;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            ex.printStackTrace();
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }

    /**
     * Add song to SQL database table.
     */
    public void addAllSongsFromDir(String title, String artist, int dura, String source, String fpath, String genre, String album)
    {
            String sql = "INSERT INTO dbo.Songs (title, artists, duration, source, filepath, genre, album) " +
                    "VALUES ('" + title + "', '" + artist + "', '" + dura + "', '"
                    + source + "', '" + fpath + "', '" + genre + "', '" + album + "')";

        try {
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
