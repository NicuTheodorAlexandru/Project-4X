package audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import misc.Utils;

public class SoundBuffer
{
	private final int bufferID;
	private ShortBuffer pcm = null;
	private ByteBuffer vorbis = null;
	
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info)
	{
		MemoryStack stack = MemoryStack.stackPush();
		vorbis = Utils.ioResourceToByteBuffer(resource, bufferSize);
		IntBuffer error = stack.mallocInt(1);
		long decoder = STBVorbis.stb_vorbis_open_memory(vorbis, error, null);
		if(decoder == MemoryUtil.NULL)
			System.err.println("Failed to open ogg vorbis file: " + error.get(0));
		STBVorbis.stb_vorbis_get_info(decoder, info);
		int channels = info.channels();
		int lengthSamples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder);
		pcm = MemoryUtil.memAllocShort(lengthSamples);
		pcm.limit(STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
		STBVorbis.stb_vorbis_close(decoder);
		return pcm;
	}
	
	public void cleanup()
	{
		AL10.alDeleteBuffers(bufferID);
		if(pcm != null)
			MemoryUtil.memFree(pcm);
	}
	
	public int getBufferID()
	{
		return bufferID;
	}
	
	public SoundBuffer(String filename)
	{
		bufferID = AL10.alGenBuffers();
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = readVorbis(filename, 1024 * 1024, info);
		AL10.alBufferData(bufferID, info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16
				, pcm, info.sample_rate());
	}
}
