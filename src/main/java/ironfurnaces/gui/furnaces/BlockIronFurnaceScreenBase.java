package ironfurnaces.gui.furnaces;

import ironfurnaces.IronFurnaces;
import ironfurnaces.capability.ClientShowConfig;
import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
import ironfurnaces.network.Messages;
import ironfurnaces.network.PacketFurnaceSettings;
import ironfurnaces.network.PacketShowConfig;
import ironfurnaces.util.gui.FurnaceGuiButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockIronFurnaceScreenBase<T extends BlockIronFurnaceContainerBase> extends AbstractContainerScreen<T> {

    public Identifier GUI = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace.png");
    public static final Identifier GUI_NETHERITE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_netherite.png");
    public static final Identifier GUI_ATM = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_allthemodium.png");
    public static final Identifier GUI_VIB = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_vibranium.png");
    public static final Identifier GUI_UNOB = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_unobtainium.png");
    public static final Identifier GUI_FACTORY = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_factory.png");
    public static final Identifier GUI_GENERATOR = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_generator.png");
    public static final Identifier GUI_GENERATOR_NETHERITE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_generator_netherite.png");
    public static final Identifier GUI_GENERATOR_ALLTHEMODIUM = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_generator_allthemodium.png");
    public static final Identifier GUI_GENERATOR_VIBRANIUM = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_generator_vibranium.png");
    public static final Identifier GUI_GENERATOR_UNOBTAINIUM = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/furnace_generator_unobtainium.png");
    public static final Identifier GUI_AUGMENTS = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/augment.png");
    public static final Identifier WIDGETS = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "textures/gui/widgets.png");

    public final List<FurnaceGuiButton> sideButtons = new ArrayList<>();
    public FurnaceGuiButton autoSplitButton;
    public FurnaceGuiButton augmentButton;
    public FurnaceGuiButton autoInputButton;
    public FurnaceGuiButton autoOutputButton;
    public FurnaceGuiButton topButton;
    public FurnaceGuiButton leftButton;
    public FurnaceGuiButton frontButton;
    public FurnaceGuiButton rightButton;
    public FurnaceGuiButton bottomButton;
    public FurnaceGuiButton backButton;
    public FurnaceGuiButton redstoneIgnoredButton;
    public FurnaceGuiButton redstoneLowButton;
    public FurnaceGuiButton redstoneHighButton;
    public FurnaceGuiButton comparatorButton;
    public FurnaceGuiButton comparatorSubButton;
    public FurnaceGuiButton addButton;
    public FurnaceGuiButton subButton;

    public BlockIronFurnaceScreenBase(T t, Inventory inv, Component name) {
        super(t, inv, name);
    }

    @Override
    protected void init() {
        super.init();
        int left = this.leftPos;
        int top = this.topPos;

        this.sideButtons.clear();
        this.autoSplitButton = new FurnaceGuiButton(left, top, 9, 56, 14, 14, 112, 189);
        this.augmentButton = new FurnaceGuiButton(left, top, 161, 4, 11, 11);
        this.autoInputButton = new FurnaceGuiButton(left, top, -47, 12, 14, 14, 0, 189);
        this.autoOutputButton = new FurnaceGuiButton(left, top, -29, 12, 14, 14, 14, 189);
        this.redstoneIgnoredButton = new FurnaceGuiButton(left, top, -47, 70, 14, 14, 28, 189);
        this.redstoneLowButton = new FurnaceGuiButton(left, top, -31, 70, 14, 14, 84, 189, 98, 189, 98, 189);
        this.redstoneHighButton = new FurnaceGuiButton(left, top, -31, 70, 14, 14, 42, 189);
        this.comparatorButton = new FurnaceGuiButton(left, top, -15, 70, 14, 14, 56, 189);
        this.comparatorSubButton = new FurnaceGuiButton(left, top, -47, 86, 14, 14, 70, 189);
        this.addButton = new FurnaceGuiButton(left, top, -31, 86, 14, 14, 0, 14, 14, 14, 28, 14);
        this.subButton = new FurnaceGuiButton(left, top, -31, 86, 14, 14, 0, 0, 14, 0, 28, 0);
        this.sideButtons.add(this.bottomButton = new FurnaceGuiButton(left, top, -32, 55, 10, 10));
        this.sideButtons.add(this.topButton = new FurnaceGuiButton(left, top, -32, 31, 10, 10));
        this.sideButtons.add(this.frontButton = new FurnaceGuiButton(left, top, -32, 43, 10, 10));
        this.sideButtons.add(this.backButton = new FurnaceGuiButton(left, top, -20, 55, 10, 10));
        this.sideButtons.add(this.leftButton = new FurnaceGuiButton(left, top, -44, 43, 10, 10));
        this.sideButtons.add(this.rightButton = new FurnaceGuiButton(left, top, -20, 43, 10, 10));
    }

    private boolean showInventoryButtons() {
        return getShowConfig() == 1;
    }

    public void setShowConfig(int value) {
        ClientShowConfig.set(value);
        Messages.sendToServer(new PacketShowConfig(value));
    }

    public int getShowConfig() {
        return ClientShowConfig.getShowConfig();
    }

    private Identifier getBackgroundTexture() {
        if (this.menu.getAugmentGUI()) {
            return GUI_AUGMENTS;
        }
        if (this.menu.getIsFactory()) {
            return GUI_FACTORY;
        }
        if (this.menu.getIsGenerator()) {
            return getGeneratorTexture();
        }
        return GUI;
    }

    private Identifier getGeneratorTexture() {
        if (GUI.equals(GUI_NETHERITE)) {
            return GUI_GENERATOR_NETHERITE;
        }
        if (GUI.equals(GUI_ATM)) {
            return GUI_GENERATOR_ALLTHEMODIUM;
        }
        if (GUI.equals(GUI_VIB)) {
            return GUI_GENERATOR_VIBRANIUM;
        }
        if (GUI.equals(GUI_UNOB)) {
            return GUI_GENERATOR_UNOBTAINIUM;
        }
        return GUI_GENERATOR;
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int titleWidth = this.minecraft.font.width(this.title.getString());
        int titleX = this.menu.getIsFurnace() ? 7 + this.imageWidth / 2 - titleWidth / 2 : this.imageWidth / 2 - titleWidth / 2;
        int titleY = this.menu.getIsFactory() ? -10 : 6;
        int titleColor = this.menu.getIsFactory() ? 16777215 : 4210752;

        graphics.text(this.font, this.title, titleX, titleY, titleColor, false);
        graphics.text(this.font, this.playerInventoryTitle, 7, this.imageHeight - 93, 4210752, false);

        if (showInventoryButtons() && this.menu.getRedstoneMode() == 4) {
            int comSub = this.menu.getComSub();
            int x = (comSub > 9 ? 28 : 31) - 42;
            graphics.text(this.font, Component.literal(String.valueOf(comSub)), x, 90, 4210752, false);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        Identifier background = getBackgroundTexture();
        graphics.blit(RenderPipelines.GUI_TEXTURED, background, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        renderFurnaceProgress(graphics);
        renderGeneratorProgress(graphics);
        renderFactoryProgress(graphics);

        int actualMouseX = mouseX - this.leftPos;
        int actualMouseY = mouseY - this.topPos;
        renderFactoryButtons(graphics, actualMouseX, actualMouseY);
        renderInventoryButtons(graphics, actualMouseX, actualMouseY);
        renderRedstoneButtons(graphics, actualMouseX, actualMouseY);

        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderFurnaceProgress(GuiGraphicsExtractor graphics) {
        if (!this.menu.getIsFurnace() || this.menu.getAugmentGUI()) {
            return;
        }

        int fireHeight = this.menu.getBurnLeftScaled(13);
        if (this.menu.isBurning() && fireHeight > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI, this.leftPos + 56, this.topPos + 36 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight + 1, 256, 256);
        }

        int arrowWidth = this.menu.getCookScaled(24);
        if (arrowWidth > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI, this.leftPos + 79, this.topPos + 34, 176, 14, arrowWidth + 1, 16, 256, 256);
        }
    }

    private void renderGeneratorProgress(GuiGraphicsExtractor graphics) {
        if (!this.menu.getIsGenerator() || this.menu.getAugmentGUI()) {
            return;
        }

        Identifier generatorTexture = getGeneratorTexture();
        int fireHeight = this.menu.getGeneratorBurnScaled(13);
        if (this.menu.isGeneratorBurning() && fireHeight > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, generatorTexture, this.leftPos + 56, this.topPos + 23 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight + 1, 256, 256);
        }

        int energyHeight = this.menu.getEnergyScaled(42);
        if (energyHeight > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, generatorTexture, this.leftPos + 109, this.topPos + 22 + 42 - energyHeight, 176, 14 + 42 - energyHeight, 14, energyHeight, 256, 256);
        }
    }

    private void renderFactoryProgress(GuiGraphicsExtractor graphics) {
        if (!this.menu.getIsFactory() || this.menu.getAugmentGUI()) {
            return;
        }

        addFactorySlots(graphics, this.menu.getTier());

        int energyHeight = this.menu.getEnergyScaled(42);
        if (energyHeight > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 9, this.topPos + 7 + 42 - energyHeight, 176, 22 + 42 - energyHeight, 14, energyHeight, 256, 256);
        }

        for (int index = 0; index < this.menu.getFactoryCooktimeSize(); index++) {
            int progress = this.menu.getFactoryCookScaled(index, 22);
            if (progress > 0) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 29 + (21 * index), this.topPos + 27, 176, 0, 15, progress + 1, 256, 256);
            }
        }
    }

    private void addFactorySlots(GuiGraphicsExtractor graphics, int amount) {
        if (amount > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 48, this.topPos + 5, 176, 64, 18, 67, 256, 256);
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 111, this.topPos + 5, 176, 64, 18, 67, 256, 256);
            if (amount == 2) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 27, this.topPos + 5, 176, 64, 18, 67, 256, 256);
                graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_FACTORY, this.leftPos + 132, this.topPos + 5, 176, 64, 18, 67, 256, 256);
            }
        }
    }

    private void renderFactoryButtons(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.menu.getIsFactory() && !this.menu.getAugmentGUI()) {
            renderButton(graphics, this.autoSplitButton, mouseX, mouseY, true, this.menu.isAutoSplit());
        }
    }

    private void renderInventoryButtons(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (!showInventoryButtons()) {
            blitWidgets(graphics, this.leftPos - 20, this.topPos + 4, 0, 28, 23, 26);
            return;
        }

        blitWidgets(graphics, this.leftPos - 56, this.topPos + 4, 0, 54, 59, 107);
        renderButton(graphics, this.autoInputButton, mouseX, mouseY, true, this.menu.getAutoInput());
        renderButton(graphics, this.autoOutputButton, mouseX, mouseY, true, this.menu.getAutoOutput());
        blitIO(graphics);
    }

    private void renderRedstoneButtons(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (!showInventoryButtons()) {
            return;
        }

        boolean shift = isShiftKeyDown();
        int setting = this.menu.getRedstoneMode();
        if (setting == 0) {
            renderButton(graphics, this.redstoneIgnoredButton, mouseX, mouseY, true, true);
        }
        if (shift) {
            renderButton(graphics, this.redstoneLowButton, mouseX, mouseY, true, setting == 2);
        }
        if (!shift) {
            renderButton(graphics, this.redstoneHighButton, mouseX, mouseY, true, setting == 1);
        }
        if (setting == 3) {
            renderButton(graphics, this.comparatorButton, mouseX, mouseY, true, true);
        }
        if (setting == 4) {
            renderButton(graphics, this.comparatorSubButton, mouseX, mouseY, true, true);
            renderButton(graphics, this.addButton, mouseX, mouseY, !shift, this.menu.getComSub() == 15);
            renderButton(graphics, this.subButton, mouseX, mouseY, shift, this.menu.getComSub() == 0);
        }
    }

    private void blitIO(GuiGraphicsExtractor graphics) {
        int[] settings = new int[]{
                this.menu.getSettingBottom(),
                this.menu.getSettingTop(),
                this.menu.getSettingFront(),
                this.menu.getSettingBack(),
                this.menu.getSettingLeft(),
                this.menu.getSettingRight()
        };

        for (int i = 0; i < settings.length && i < this.sideButtons.size(); i++) {
            if (settings[i] == 0) {
                continue;
            }
            FurnaceGuiButton button = this.sideButtons.get(i);
            button.changeEnabledUV((10 * settings[i]) - 10, 161);
            renderButton(graphics, button, 0, 0, true, true);
        }

        if (this.menu.getAugmentGUI()) {
            return;
        }

        boolean input = false;
        boolean output = false;
        boolean both = false;
        boolean fuel = false;
        for (int set : settings) {
            input |= set == 1;
            output |= set == 2;
            both |= set == 3;
            fuel |= set == 4;
        }

        if (input || both) {
            if (this.menu.getIsFurnace()) {
                blitWidgets(graphics, this.leftPos + 55, this.topPos + 16, 0, 171, 18, 18);
            }
            if (this.menu.getIsFactory()) {
                blitFactoryInputOverlays(graphics);
            }
        }

        if (output || both) {
            if (this.menu.getIsFurnace()) {
                blitWidgets(graphics, this.leftPos + 111, this.topPos + 30, 0, 203, 26, 26);
            }
            if (this.menu.getIsFactory()) {
                blitFactoryOutputOverlays(graphics);
            }
        }

        if (fuel) {
            if (this.menu.getIsFurnace()) {
                blitWidgets(graphics, this.leftPos + 55, this.topPos + 52, 18, 171, 18, 18);
            }
            if (this.menu.getIsGenerator()) {
                blitWidgets(graphics, this.leftPos + 55, this.topPos + 39, 18, 171, 18, 18);
            }
        }
    }

    private void blitFactoryInputOverlays(GuiGraphicsExtractor graphics) {
        blitWidgets(graphics, this.leftPos + 69, this.topPos + 5, 0, 171, 18, 18);
        blitWidgets(graphics, this.leftPos + 90, this.topPos + 5, 0, 171, 18, 18);
        if (this.menu.getTier() > 0) {
            blitWidgets(graphics, this.leftPos + 48, this.topPos + 5, 0, 171, 18, 18);
            blitWidgets(graphics, this.leftPos + 111, this.topPos + 5, 0, 171, 18, 18);
            if (this.menu.getTier() > 1) {
                blitWidgets(graphics, this.leftPos + 27, this.topPos + 5, 0, 171, 18, 18);
                blitWidgets(graphics, this.leftPos + 132, this.topPos + 5, 0, 171, 18, 18);
            }
        }
    }

    private void blitFactoryOutputOverlays(GuiGraphicsExtractor graphics) {
        blitWidgets(graphics, this.leftPos + 69, this.topPos + 54, 36, 171, 18, 18);
        blitWidgets(graphics, this.leftPos + 90, this.topPos + 54, 36, 171, 18, 18);
        if (this.menu.getTier() > 0) {
            blitWidgets(graphics, this.leftPos + 48, this.topPos + 54, 36, 171, 18, 18);
            blitWidgets(graphics, this.leftPos + 111, this.topPos + 54, 36, 171, 18, 18);
            if (this.menu.getTier() > 1) {
                blitWidgets(graphics, this.leftPos + 27, this.topPos + 54, 36, 171, 18, 18);
                blitWidgets(graphics, this.leftPos + 132, this.topPos + 54, 36, 171, 18, 18);
            }
        }
    }

    private void renderButton(GuiGraphicsExtractor graphics, FurnaceGuiButton button, int mouseX, int mouseY, boolean visible, boolean enabled) {
        if (!visible || button == null) {
            return;
        }

        int u = button.u;
        int v = button.v;
        if (enabled && button.hasUVEnabled()) {
            u = button.u_enabled;
            v = button.v_enabled;
        } else if (button.hovering(mouseX, mouseY) && button.hasUVHover()) {
            u = button.u_hover;
            v = button.v_hover;
        }

        if (u >= 0 && v >= 0) {
            blitWidgets(graphics, this.leftPos + button.x, this.topPos + button.y, u, v, button.width, button.height);
        }
    }

    private void blitWidgets(GuiGraphicsExtractor graphics, int x, int y, int u, int v, int width, int height) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, WIDGETS, x, y, u, v, width, height, 256, 256);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double actualMouseX = event.x() - this.leftPos;
        double actualMouseY = event.y() - this.topPos;
        this.mouseClickedRedstoneButtons(actualMouseX, actualMouseY);
        this.mouseClickedInventoryButtons(event.button(), actualMouseX, actualMouseY);
        this.mouseClickedAugmentButton(actualMouseX, actualMouseY);
        this.mouseClickedAutoSplitButton(actualMouseX, actualMouseY);
        return super.mouseClicked(event, doubleClick);
    }

    public void mouseClickedAutoSplitButton(double mouseX, double mouseY) {
        if (!this.menu.isAutoSplit()) {
            this.autoSplitButton.onClick(mouseX, mouseY, this.menu.getPos(), 11, 1, this.menu.getIsFactory());
        } else {
            this.autoSplitButton.onClick(mouseX, mouseY, this.menu.getPos(), 11, 0, this.menu.getIsFactory());
        }
    }

    public void mouseClickedAugmentButton(double mouseX, double mouseY) {
        if (!this.menu.getAugmentGUI()) {
            this.augmentButton.onClick(mouseX, mouseY, this.menu.getPos(), 10, 1, true);
        } else {
            this.augmentButton.onClick(mouseX, mouseY, this.menu.getPos(), 10, 0, true);
        }
    }

    public void mouseClickedInventoryButtons(int button, double mouseX, double mouseY) {
        if (!showInventoryButtons()) {
            if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                setShowConfig(1);
            }
            return;
        }

        if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
            setShowConfig(0);
        }
        if (!this.menu.getAutoInput()) {
            this.autoInputButton.onClick(mouseX, mouseY, this.menu.getPos(), 6, 1, true);
        } else {
            this.autoInputButton.onClick(mouseX, mouseY, this.menu.getPos(), 6, 0, true);
        }
        if (!this.menu.getAutoOutput()) {
            this.autoOutputButton.onClick(mouseX, mouseY, this.menu.getPos(), 7, 1, true);
        } else {
            this.autoOutputButton.onClick(mouseX, mouseY, this.menu.getPos(), 7, 0, true);
        }
        clickInvButton(mouseX, mouseY, this.topButton, button, this.menu.getSettingTop(), Direction.UP.ordinal());
        clickInvButton(mouseX, mouseY, this.bottomButton, button, this.menu.getSettingBottom(), Direction.DOWN.ordinal());
        clickInvButton(mouseX, mouseY, this.frontButton, button, this.menu.getSettingFront(), this.menu.getIndexFront(), isShiftKeyDown());
        clickInvButton(mouseX, mouseY, this.backButton, button, this.menu.getSettingBack(), this.menu.getIndexBack());
        clickInvButton(mouseX, mouseY, this.leftButton, button, this.menu.getSettingLeft(), this.menu.getIndexLeft());
        clickInvButton(mouseX, mouseY, this.rightButton, button, this.menu.getSettingRight(), this.menu.getIndexRight());
    }

    protected void clickInvButton(double mouseX, double mouseY, FurnaceGuiButton button, int buttonId, int setting, int index) {
        clickInvButton(mouseX, mouseY, button, buttonId, setting, index, false);
    }

    protected void clickInvButton(double mouseX, double mouseY, FurnaceGuiButton button, int buttonId, int setting, int index, boolean shift) {
        int next = setting == 4 ? 0 : setting + 1;
        button.onClick(mouseX, mouseY, this.menu.getPos(), index, next, buttonId == GLFW.GLFW_MOUSE_BUTTON_1);

        int previous = setting == 0 ? 4 : setting - 1;
        button.onRightClick(mouseX, mouseY, buttonId, this.menu.getPos(), index, previous, true);

        if (shift && this.frontButton.hovering(mouseX, mouseY)) {
            for (int i = 0; i < this.sideButtons.size(); i++) {
                Messages.sendToServer(new PacketFurnaceSettings(this.menu.getPos().getX(), this.menu.getPos().getY(), this.menu.getPos().getZ(), i, 0));
            }
        }
    }

    public void mouseClickedRedstoneButtons(double mouseX, double mouseY) {
        if (!showInventoryButtons()) {
            return;
        }

        boolean shift = isShiftKeyDown();
        this.addButton.onClick(mouseX, mouseY, this.menu.getPos(), 9, this.menu.getComSub() + 1, !shift && this.menu.getComSub() < 15);
        this.subButton.onClick(mouseX, mouseY, this.menu.getPos(), 9, this.menu.getComSub() - 1, shift && this.menu.getComSub() > 0);
        this.redstoneIgnoredButton.onClick(mouseX, mouseY, this.menu.getPos(), 8, 0, this.menu.getRedstoneMode() != 0);
        this.redstoneLowButton.onClick(mouseX, mouseY, this.menu.getPos(), 8, 2, this.menu.getRedstoneMode() != 2 && shift);
        this.redstoneHighButton.onClick(mouseX, mouseY, this.menu.getPos(), 8, 1, this.menu.getRedstoneMode() != 1 && !shift);
        this.comparatorButton.onClick(mouseX, mouseY, this.menu.getPos(), 8, 3, this.menu.getRedstoneMode() != 3);
        this.comparatorSubButton.onClick(mouseX, mouseY, this.menu.getPos(), 8, 4, this.menu.getRedstoneMode() != 4);
    }

    public static boolean isShiftKeyDown() {
        return FurnaceGuiButton.isShiftKeyDown();
    }
}
