package audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

public class SoundSource
{
	private int sourceID;
	
	public void cleanup()
	{
		stop();
		AL10.alDeleteSources(sourceID);
	}
	
	public void stop()
	{
		AL10.alSourceStop(sourceID);
	}
	
	public void pause()
	{
		AL10.alSourcePause(sourceID);
	}
	
	public boolean isPlaying()
	{
		return AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public void play()
	{
		AL10.alSourcePlay(sourceID);
	}
	
	public void setGain(float gain)
	{
		AL10.alSourcef(sourceID, AL10.AL_GAIN, gain);
	}
	
	public void setSpeed(Vector3f speed)
	{
		AL10.alSource3f(sourceID, AL10.AL_VELOCITY, speed.x, speed.y, speed.z);
	}
	
	public void setPosition(Vector3f position)
	{
		AL10.alSource3f(sourceID, AL10.AL_POSITION, position.x, position.y, position.z);
	}
	
	public void setBuffer(int bufferID)
	{
		stop();
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, bufferID);
	}
	
	public SoundSource(boolean loop, boolean relative)
	{
		sourceID = AL10.alGenSources();
		if(loop)
			AL10.alSourcei(sourceID, AL10.AL_LOOPING, AL10.AL_TRUE);
		if(relative)
			AL10.alSourcei(sourceID, AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
	}
}
