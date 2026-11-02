#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1; // sprite min corner in atlas pixels (sent in the overlay slot)
in ivec2 UV2; // sprite max corner in atlas pixels (sent in the lightmap slot)

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;
out vec2 texCoord0;
flat out ivec4 spriteBounds;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color;
    texCoord0 = UV0;
    spriteBounds = ivec4(UV1, UV2);
}
