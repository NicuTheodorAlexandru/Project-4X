package audio;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;

import graphics.Camera;
import graphics.Transformation;

public class SoundManager
{
	private long device;
	private long context;
	private SoundListener listener;
	private final List<SoundBuffer> soundBuffers;
	private final Map<String, SoundSource> soundSources;
	private Matrix4f cameraMatrix;
	
	public void setAttenuationModel(int model)
	{
		AL10.alDistanceModel(model);
	}
	
	public void setListener(SoundListener listener)
	{
		this.listener = listener;
	}
	
	public SoundListener getListener()
	{
		return listener;
	}
	
	public void addSoundBuffer(SoundBuffer buffer)
	{
		soundBuffers.add(buffer);
	}
	
	public void removeSoundSource(String name)
	{
		soundSources.remove(name);
	}
	
	public void playSoundSource(String name)
	{
		SoundSource source = soundSources.get(name);
		if(source != null && !source.isPlaying())
			source.play();
	}
	
	public SoundSource getSoundSource(String name)
	{
		return soundSources.get(name);
	}
	
	public void addSoundSource(String name, SoundSource source)
	{
		soundSources.put(name, source);
	}
	
	public void updateListener(Camera camera)
	{
		Transformation.updateGenericViewMatrix(camera.getPosition(), camera.getRotation(), cameraMatrix);
		listener.setPosition(camera.getPosition());
		Vector3f at = new Vector3f();
		cameraMatrix.positiveZ(at).negate();
		Vector3f up = new Vector3f();
		cameraMatrix.positiveY(up);
		listener.setOrientation(at, at);
		if(soundSources.get("Diplomacy").isPlaying())
			System.out.println("!");
	}
	
	public void init()
	{
		device = ALC10.alcOpenDevice(ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER));
		if(device == MemoryUtil.NULL)
			System.err.println("Could not open default OpenAL device!");
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = ALC10.alcCreateContext(device, (IntBuffer) null);
		if(context == MemoryUtil.NULL)
			System.err.println("Failed to create OpenAL context!");
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
	}
	
	public SoundManager()
	{
		soundBuffers = new ArrayList<>();
		soundSources = new HashMap<>();
		cameraMatrix = new Matrix4f();
	}
}
