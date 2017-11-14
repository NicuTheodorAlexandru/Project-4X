#version 330

in vec2 outTexCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec4 color;
uniform int owned;
uniform int selected;

void main()
{
	if(owned == 1)
	{
		fragColor = texture(textureSampler, outTexCoord);
		fragColor = color;
	}
	else
	{
		fragColor = texture(textureSampler, outTexCoord);
	}
	if(selected == 1)
	{
		fragColor = vec4(fragColor.x, fragColor.y, fragColor.z, 0.8);
	}
}