#version 330

in vec2 vertTextCoord;
in vec4 vertColour;

out vec4 fragColour;

void main() {
    fragColour = vertColour;
}
