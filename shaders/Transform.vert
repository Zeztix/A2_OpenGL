#version 330

layout(location=0) in vec3 vPos;
layout(location=1) in vec2 texCoord;
layout(location=2) in vec3 colour;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec2 vertTexCoord;
out vec4 vertColour;

void main() {
	gl_Position = projection * view * model * vec4(vPos, 1.0);
	vertTexCoord = texCoord;
	vertColour = vec4(colour.r, colour.g, colour.b, 1.0);
}