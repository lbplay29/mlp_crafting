#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
flat in ivec4 spriteBounds; // xy = min corner, zw = max corner (exclusive), in atlas pixels

out vec4 fragColor;

// Glow thickness in texture pixels. MUST match GLOW_RADIUS in MagicAuraRenderer.java.
const int GLOW_RADIUS = 2;

bool insideSprite(ivec2 p) {
    return p.x >= spriteBounds.x && p.y >= spriteBounds.y && p.x < spriteBounds.z && p.y < spriteBounds.w;
}

bool isOpaque(ivec2 p) {
    return insideSprite(p) && texelFetch(Sampler0, p, 0).a >= 0.1;
}

void main() {
    ivec2 texel = ivec2(floor(texCoord0 * vec2(textureSize(Sampler0, 0))));

    // The real item is drawn here; the aura only goes around it.
    if (isOpaque(texel)) {
        discard;
    }

    // Distance (in texels) to the nearest opaque texel of this sprite.
    float nearest = 1000.0;
    for (int dy = -GLOW_RADIUS; dy <= GLOW_RADIUS; dy++) {
        for (int dx = -GLOW_RADIUS; dx <= GLOW_RADIUS; dx++) {
            if (isOpaque(texel + ivec2(dx, dy))) {
                nearest = min(nearest, length(vec2(dx, dy)));
            }
        }
    }
    if (nearest > float(GLOW_RADIUS) + 0.5) {
        discard;
    }

    // 1.0 right next to the item, fading out toward the edge of the glow.
    float fade = clamp(1.0 - (nearest - 1.0) / float(GLOW_RADIUS), 0.0, 1.0);
    if (fade <= 0.0) {
        discard;
    }
    fragColor = vec4(vertexColor.rgb, vertexColor.a * fade) * ColorModulator;
}
