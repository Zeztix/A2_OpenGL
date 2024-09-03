#version 330

layout(location=0) in vec3 pos;
layout(location=1) in vec2 texCoord;
layout(location=2) in vec3 colour;

out vec2 vertTexCoord;
out vec4 vertColour;

void main() {
    gl_Position = vec4(pos, 1.0);
    vertTexCoord = texCoord;
    vertColour = vec4(colour.r, colour.g, colour.b, 1.0);
}
