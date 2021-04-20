package GameEngine.Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio 
{
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	private Clip audioClip; // The audio clip width the music.
	//END REGION
	
	//REGION: CONSTRUCTORS
	public Audio(String path) 
	{
		//TODO: Get the audio clip, and save it.
		try 
		{
			AudioInputStream inputStream=AudioSystem.getAudioInputStream(new File(path));
			audioClip=AudioSystem.getClip();
			audioClip.open(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//END REGION
	
	//REGION: METHODS
	public void Play()
	{
		//TODO: Play the clip.
		if(!audioClip.isRunning()) 
		{
			audioClip.setFramePosition(0);
			audioClip.start();
		}
	}
	
	public void setVolume(double gain) 
	{
		//TODO: Set the volume of the clip.
		FloatControl gainControl=(FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue((float) (20d*Math.log10(gain)));
	}
	
	public void Stop()
	{
		//TODO: Stop the clip.
		if(audioClip.isRunning())
		{
			audioClip.stop();
		}
	}
	
	public void Loop()
	{
		//TODO: Loop the music infinity times.
		if(!audioClip.isRunning())
		{
			audioClip.setFramePosition(0);
			audioClip.loop(Integer.MAX_VALUE);
		}
	}
	
	public void Loop(int times)
	{
		//TODO: Loop the music specific number of times.
		if(!audioClip.isRunning())
		{
			audioClip.setFramePosition(0);
			audioClip.loop(times);
		}
	}
	//END REGION
	//END REGION
}
