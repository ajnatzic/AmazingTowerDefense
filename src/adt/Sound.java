package adt;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This Class plays various sounds from the sounds folder. The sounds folder is
 * specified by the static string DIR. This class should be used in conjunction
 * with TDModel classes that do various user actions such as attacking an enemy,
 * starting a round etc.
 */
public class Sound {

	/**
	 * String that points from main repository directory to sounds folder. Should
	 * not be changed unless the sounds directory changes
	 */
	private static String DIR = "src/adt/sounds/";

	/**
	 * This method takes a sound file name as input and plays the requested sound.
	 * File names should be written without slashes or directories, instead just
	 * write the filename with the extension. The DIR string in this class should
	 * point to the sounds directory already. Also, only WAV files work with this
	 * method.
	 * 
	 * Example use: sound(bellStart.wav);
	 * 
	 * @param fileName	The specified sound file with just the filename and extension (.wav)
	 */
	public static void sound(String fileName) {
		try {
			trySound(new File(DIR + fileName));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method ensures exception checking when a sound is invoked.
	 * CAUTION: This method should not be used directly, use sound() 
	 * when you want to invoke a sound
	 * 
	 * @param wavFile	Full directory path that points to the sound we want
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private static void trySound(File wavFile)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile.getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

}
