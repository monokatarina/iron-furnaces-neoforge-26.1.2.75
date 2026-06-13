package ironfurnaces.gui.furnaces;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ConfigButton extends Button {
    private final int btnWidth;
    private final int btnHeight;
    private final int type;  // 0 = direcional, 1 = auto/redstone
    private int currentMode;
    private boolean isOn;
    private Identifier customTexture;
    private Identifier[] modeSprites;
    private int direction;

    // Construtor para botões direcionais (com tamanho customizado)
    public ConfigButton(int x, int y, int direction, int initialMode, Identifier[] modeSprites, int width, int height, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.type = 0;
        this.direction = direction;
        this.currentMode = initialMode;
        this.modeSprites = modeSprites;
        this.btnWidth = width;
        this.btnHeight = height;
    }

    // Construtor para botões de auto-input/output e redstone (com tamanho customizado)
    public ConfigButton(int x, int y, Identifier texture, boolean initialState, int width, int height, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.type = 1;
        this.customTexture = texture;
        this.isOn = initialState;
        this.btnWidth = width;
        this.btnHeight = height;
    }

    public void setMode(int mode) {
        this.currentMode = mode;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        if (type == 0) {
            Identifier modeSprite = modeSprites[currentMode];
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, modeSprite, this.getX(), this.getY(), btnWidth, btnHeight);
        } else {
            Identifier textureToUse = customTexture;
            if (isOn && !customTexture.getPath().contains("_on")) {
                textureToUse = Identifier.fromNamespaceAndPath(customTexture.getNamespace(), customTexture.getPath() + "_on");
            } else if (!isOn && customTexture.getPath().contains("_on")) {
                textureToUse = Identifier.fromNamespaceAndPath(customTexture.getNamespace(), customTexture.getPath().replace("_on", ""));
            }
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureToUse, this.getX(), this.getY(), btnWidth, btnHeight);
        }
    }
}