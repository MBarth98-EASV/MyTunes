import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class musicPlayer {

    static musicPlayer player = new musicPlayer();

    static Clip clip;

    private musicPlayer()
    {

    }

    /**
     * Returns static player for access.
     * @return
     */
    public static musicPlayer getInstance()
    {
        return player;
    }


    /**
     * Locates music file and loads into memory.
     * @param filepath
     */
    public static void loadMusic(String filepath)
    {
        try
        {
            File musicPath = new File(filepath);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to load");
        }
    }
}
