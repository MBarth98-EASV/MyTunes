package dal.db;

import be.PlaylistModel;
import be.SongModel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
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

    private void execute(String sql)
    {
        try
        {
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet query(String sql)
    {
        try {
            Statement statement = dataSource.getConnection().createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Add song and playlist to SQL database table.
     */
    public void addSong(SongModel song)
    {
        String sql = """
                INSERT INTO Songs (title, artists, duration, source, filepath, genre, album)
                VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')
                """.formatted(song.getTitle(), song.getArtists(), song.getDuration(), song.getTag(), song.getLocation(), song.getGenre(), song.getAlbum());

       this.execute(sql);
    }

    /**
     * Add song or playlist to SQL database table.
      */
    public void addSong(String songName, String art, int dura, String sauce, String fpath, String genre, String album)
    {
        String sql = "INSERT INTO Songs (title, artists, duration, source, filepath, genre, album) VALUES ('" + songName + "', '" + art + "', '" + dura + "', '" + sauce + "', '" + fpath + "', '" + genre + "', '" + album + "')";
        this.execute(sql);
    }

    public void addPlaylist(String playlistName){
        String sql = "INSERT INTO dbo.PlayList (name) VALUES ('" + playlistName + "')";
        this.execute(sql);
    }

    public void addSongToPlaylist(SongModel song, PlaylistModel playlist){
        String sql = "INSERT INTO dbo.Playlist_entry (playlistID, SongID) VALUES"
                + "(" + playlist.getOrderID() + ", " + song.getId() + ")";
        this.execute(sql);
    }

    /**
     * Remove Songs or Playlists from SQL database.
      */
    public void removeSong(int id)
    {
        this.execute("DELETE FROM Songs WHERE id = " + id);
    }

    public void removeSong(String songName)
    {
        this.execute("DELETE FROM Songs WHERE title LIKE '%" + songName + "%'");
    }

    public void removePlaylist(PlaylistModel playlist) {
        String sql = "DELETE FROM dbo.PlayList WHERE id = " + playlist.getOrderID();
        String sqlSongs = "DELETE FROM dbo.Playlist_entry WHERE playlistID = " + playlist.getOrderID();
        this.execute(sql);
        this.execute(sqlSongs);
    }

    public void removeSongFromPlaylist(PlaylistModel playlist, SongModel song){
        String sql = "DELETE FROM dbo.Playlist_entry WHERE playlistID = " + playlist.getOrderID() + " AND SongID = " + song.getId();
        this.execute(sql);
    }

    /**
     * Updaters for songs and playlists.
     */
    public void updateSong(SongModel song){
        String sql = "UPDATE dbo.Songs SET title = '" + song.getTitle() + "', artists = '"
                + song.getArtists() + "', genre = '" + song.getGenre() + "', album = '" + song.getAlbum() + "' WHERE id = " + song.getId();
    }

    public void updatePlaylist(PlaylistModel playlist){
        String sql = "UPDATE dbo.PlayList SET name='" + playlist.getName() + "' WHERE id=" + playlist.getOrderID();
        this.execute(sql);
    }

    /**
     * Getters from the SQL database.
     */
    public ObservableList<SongModel> getAllSongs() {return getAllSongs("SELECT * FROM Songs"); }

    public ObservableList<SongModel> getAllSongs(String sql)
    {
        try
        {
            ObservableList<SongModel> songs = FXCollections.observableArrayList();

            int songId, duration;
            String songTitle, songArtist, songLocation, songAlbum, songGenre, source;

            ResultSet result = this.query(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                duration = result.getInt("duration");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");
                source = result.getString("source");

                if (Path.of(songLocation).toFile().isFile()){
                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, duration, source, songLocation));
            }}

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());

            // return empty array - garentee not null
            return FXCollections.observableArrayList();
        }
    }
    public List<PlaylistModel> getAllPlaylists(){
        ArrayList<PlaylistModel> returnList = new ArrayList<>();
        String sql = "SELECT * FROM dbo.PlayList";
        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int playlistID = result.getInt("id");
                String playlistName = result.getString("name");
                List<SongModel> playlistSongs = getAllSongsInPlaylist(playlistID);
                if (!playlistSongs.isEmpty()){
                    returnList.add(new PlaylistModel(playlistID, getAllSongsInPlaylist(playlistID), 0, false, playlistName));
                }}
            return returnList;

        } catch (Exception e) {
            e.printStackTrace();
            return returnList;
        }

    }

    public List<SongModel> getAllSongsInPlaylist(int playlistID) throws SQLException {
        ArrayList<SongModel> returnList = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Playlist_entry WHERE playlistID = " + playlistID;
        ResultSet result = this.query(sql);
        while (result.next()) {
            int songId = result.getInt("id");
            for (SongModel s : getAllSongs()) {
                if (s.getId() == songId);
                returnList.add(s);
            }
        }
        return returnList;
    }


    public ListProperty<SongModel> filterEqualsParameter(String filterType, String filterType2, String filterParameter){
        String sql = null;

        if (!filterType2.equals("NONE"))
        {
            sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%' " +
                    "OR " + filterType2 + " LIKE '%" + filterParameter + "%'";
        }
        else
        {
            sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%'";
        }

        return new SimpleListProperty<>(this.getAllSongs(sql));
    }


    /** Select every value in the wanted column and returns it as a list of Strings.
     * @param columnName The column to select from. The filter is determined by
     * the user in the Combobox Filter.
     * @return
     */
    public List<String> getUniqueValuesIn(String columnName)
    {
        try
        {
        List<String> searchableEntities = new ArrayList<>();
            ResultSet result = this.query("SELECT DISTINCT " + columnName + " FROM dbo.Songs ");

            while (result.next())
            {
                String chosenFilter = result.getString(1);
                if (chosenFilter == null || chosenFilter.isEmpty()){
                    chosenFilter = "N/A";
                }
                searchableEntities.add(chosenFilter);
            }

            return searchableEntities;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }


}
