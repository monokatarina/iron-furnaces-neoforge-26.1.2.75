package ironfurnaces.gui.furnaces;

import ironfurnaces.IronFurnaces;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class AugmentButton extends Button {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "augment_button");
    private static final int SPRITE_WIDTH = 11;
    private static final int SPRITE_HEIGHT = 11;

    public AugmentButton(int x, int y, OnPress onPress) {
        super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT, Component.empty(), onPress, DEFAULT_NARRATION);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        // Desenha o sprite do botão - NÃO chama super.extractContents
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TEXTURE, this.getX(), this.getY(), SPRITE_WIDTH, SPRITE_HEIGHT);
    }
}