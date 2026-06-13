package ironfurnaces.gui.furnaces;

import ironfurnaces.IronFurnaces;
import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
import ironfurnaces.util.gui.FurnaceGuiButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.renderer.RenderPipelines;

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

    // ===== SPRITES =====
    public static final Identifier AUGMENT_BUTTON = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "augment_button");
    public static final Identifier CONFIG_ENGINE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config");
    public static final Identifier AUTO_INPUT = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "auto_input");
    public static final Identifier AUTO_OUTPUT = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "auto_output");
    public static final Identifier REDSTONE_IGNORE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "redstone_ignore");
    public static final Identifier REDSTONE_HIGH_LOW = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "redstone_high_low");
    public static final Identifier REDSTONE_COMPARATOR = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "redstone_comparator");
    public static final Identifier REDSTONE_SUBTRACT = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "redstone_subtract");

    // Sprites para fogo e progresso da seta
    public static final Identifier FIRE_BACKGROUND = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "fire");
    public static final Identifier ARROW_PROGRESS = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "arrow_progress");
    public static final Identifier WIDGET_BASE = Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "widget_base");

    // Sprites para as cores dos modos (0-4)
    private static final Identifier[] MODE_SPRITES = {
            Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config_disabled"),
            Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config_input"),
            Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config_output"),
            Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config_both"),
            Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "config_fuel")
    };

    // ===== BOTÕES =====
    private ConfigButton configButton;
    private ConfigButton closeAugmentButton;
    private ConfigButton autoInputButton;
    private ConfigButton autoOutputButton;
    private List<ConfigButton> directionalButtons = new ArrayList<>();
    private List<ConfigButton> redstoneButtons = new ArrayList<>();

    public BlockIronFurnaceScreenBase(T t, Inventory inv, Component name) {
        super(t, inv, name);
    }

    @Override
    protected void init() {
        super.init();

        // ===== DEFINIÇÃO DO WIDGET =====
        int widgetX = this.leftPos - 57;
        int widgetY = this.topPos + 0;

        // ===== BOTÃO DE CONFIGURAÇÕES =====
        int configButtonX = this.leftPos - 15;
        int configButtonY = this.topPos + 0;

        // ===== BOTÃO DE FECHAR AUGMENT =====
        int closeAugmentX = this.leftPos + this.imageWidth - 24;
        int closeAugmentY = this.topPos + 6;

        // ===== NOVAS POSIÇÕES DOS BOTÕES DENTRO DO WIDGET =====
        // Auto-input e Auto-output (14x14)
// Auto-input e Auto-output (14x14)
        int autoX = widgetX + 7;
        int autoY = widgetY +7;
        int autoOutputX = widgetX + 26;
        int autoOutputY = widgetY + 7;

// Botões de Redstone (14x14)
        int redstoneIgnoreX = widgetX + 7;
        int redstoneIgnoreY = widgetY + 65;

        int redstoneHighLowX = widgetX + 24;
        int redstoneHighLowY = widgetY + 65;

        int redstoneComparatorX = widgetX + 40;
        int redstoneComparatorY = widgetY + 65;

        int redstoneSubtractX = widgetX + 8;
        int redstoneSubtractY = widgetY + 81;

// Botões direcionais (10x10)
        int upX = widgetX + 23;
        int upY = widgetY + 26;

        int downX = widgetX + 23;
        int downY = widgetY + 50;

        int leftX = widgetX + 11;
        int leftY = widgetY + 38;

        int rightX = widgetX + 35;
        int rightY = widgetY + 38;

        int frontX = widgetX + 23;
        int frontY = widgetY + 38;

        int backX = widgetX + 35;
        int backY = widgetY + 50;

        // ===== 1. BOTÃO DE CONFIGURAÇÕES =====
        this.configButton = new ConfigButton(configButtonX, configButtonY, CONFIG_ENGINE, false, 14, 14, (btn) -> {
            boolean newState = !this.menu.getAugmentGUI();
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            10,
                            newState ? 1 : 0
                    )
            );
        });
        this.addRenderableWidget(this.configButton);

        // ===== 2. BOTÃO DE FECHAR AUGMENT =====
        this.closeAugmentButton = new ConfigButton(closeAugmentX, closeAugmentY, AUGMENT_BUTTON, false, 14, 14, (btn) -> {
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            10,
                            0
                    )
            );
        });
        this.addRenderableWidget(this.closeAugmentButton);

        // ===== 3. BOTÕES DE AUTO-INPUT/OUTPUT (14x14) =====
        this.autoInputButton = new ConfigButton(autoX, autoY, AUTO_INPUT, this.menu.getAutoInput(), 14, 14, (btn) -> {
            boolean newState = !this.menu.getAutoInput();
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            6,
                            newState ? 1 : 0
                    )
            );
            ((ConfigButton) btn).setOn(newState);
        });
        this.addRenderableWidget(this.autoInputButton);

        this.autoOutputButton = new ConfigButton(autoOutputX, autoOutputY, AUTO_OUTPUT, this.menu.getAutoOutput(), 14, 14, (btn) -> {
            boolean newState = !this.menu.getAutoOutput();
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            7,
                            newState ? 1 : 0
                    )
            );
            ((ConfigButton) btn).setOn(newState);
        });
        this.addRenderableWidget(this.autoOutputButton);

        // ===== 4. BOTÕES DE REDSTONE (14x14) =====
        int currentRedstoneMode = this.menu.getRedstoneMode();

        ConfigButton ignoreButton = new ConfigButton(redstoneIgnoreX, redstoneIgnoreY, REDSTONE_IGNORE, currentRedstoneMode == 0, 14, 14, (btn) -> {
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            8, 0
                    )
            );
            updateRedstoneButtons(0);
        });
        this.addRenderableWidget(ignoreButton);
        redstoneButtons.add(ignoreButton);

        ConfigButton highLowButton = new ConfigButton(redstoneHighLowX, redstoneHighLowY, REDSTONE_HIGH_LOW, currentRedstoneMode == 1, 14, 14, (btn) -> {
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            8, 1
                    )
            );
            updateRedstoneButtons(1);
        });
        this.addRenderableWidget(highLowButton);
        redstoneButtons.add(highLowButton);

        ConfigButton comparatorButton = new ConfigButton(redstoneComparatorX, redstoneComparatorY, REDSTONE_COMPARATOR, currentRedstoneMode == 2, 14, 14, (btn) -> {
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            8, 2
                    )
            );
            updateRedstoneButtons(2);
        });
        this.addRenderableWidget(comparatorButton);
        redstoneButtons.add(comparatorButton);

        ConfigButton subtractButton = new ConfigButton(redstoneSubtractX, redstoneSubtractY, REDSTONE_SUBTRACT, currentRedstoneMode == 3, 14, 14, (btn) -> {
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            8, 3
                    )
            );
            updateRedstoneButtons(3);
        });
        this.addRenderableWidget(subtractButton);
        redstoneButtons.add(subtractButton);

        // ===== 5. BOTÕES DIRECIONAIS (10x10) =====
        int upMode = this.menu.getSideMode(1);
        ConfigButton upButton = new ConfigButton(upX, upY, 1, upMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(1);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            1, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(upButton);
        directionalButtons.add(upButton);

        int leftMode = this.menu.getSideMode(4);
        ConfigButton leftButton = new ConfigButton(leftX, leftY, 4, leftMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(4);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            4, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(leftButton);
        directionalButtons.add(leftButton);

        int frontMode = this.menu.getSideMode(2);
        ConfigButton frontButton = new ConfigButton(frontX, frontY, 2, frontMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(2);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            2, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(frontButton);
        directionalButtons.add(frontButton);

        int rightMode = this.menu.getSideMode(5);
        ConfigButton rightButton = new ConfigButton(rightX, rightY, 5, rightMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(5);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            5, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(rightButton);
        directionalButtons.add(rightButton);

        int downMode = this.menu.getSideMode(0);
        ConfigButton downButton = new ConfigButton(downX, downY, 0, downMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(0);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            0, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(downButton);
        directionalButtons.add(downButton);

        int backMode = this.menu.getSideMode(3);
        ConfigButton backButton = new ConfigButton(backX, backY, 3, backMode, MODE_SPRITES, 10, 10, (btn) -> {
            int current = this.menu.getSideMode(3);
            int newMode = (current + 1) % 5;
            ironfurnaces.network.Messages.sendToServer(
                    new ironfurnaces.network.PacketFurnaceSettings(
                            this.menu.getPos().getX(),
                            this.menu.getPos().getY(),
                            this.menu.getPos().getZ(),
                            3, newMode
                    )
            );
            ((ConfigButton) btn).setMode(newMode);
        });
        this.addRenderableWidget(backButton);
        directionalButtons.add(backButton);

        boolean showConfig = this.menu.getAugmentGUI();
        setConfigButtonsVisible(showConfig);
    }

    private void updateRedstoneButtons(int activeMode) {
        for (int i = 0; i < this.redstoneButtons.size(); i++) {
            this.redstoneButtons.get(i).setOn(i == activeMode);
        }
    }

    private void setConfigButtonsVisible(boolean visible) {
        for (ConfigButton btn : this.directionalButtons) {
            btn.visible = visible;
        }
        for (ConfigButton btn : this.redstoneButtons) {
            btn.visible = visible;
        }
        this.autoInputButton.visible = visible;
        this.autoOutputButton.visible = visible;
        this.closeAugmentButton.visible = visible;
    }

    private int getBurnLeftScaled(int pixels) {
        return this.menu.getBurnLeftScaled(pixels);
    }

    private int getCookProgressScaled(int pixels) {
        return this.menu.getCookScaled(pixels);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        // 1. Fundo da GUI
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // 2. Fogo
        int fireHeight = getBurnLeftScaled(14);
        if (fireHeight > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, FIRE_BACKGROUND, this.leftPos + 56, this.topPos + 36 + (14 - fireHeight), 14, fireHeight);
        }

        // 3. Seta de progresso
        int arrowWidth = getCookProgressScaled(24);
        if (arrowWidth > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_PROGRESS, this.leftPos + 79, this.topPos + 35, arrowWidth, 17);
        }

        // 4. Widget base e botões de configuração
        if (this.menu.getAugmentGUI()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_AUGMENTS, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, WIDGET_BASE, this.leftPos - 57, this.topPos + 0, 59, 107);
            setConfigButtonsVisible(true);
        } else {
            setConfigButtonsVisible(false);
        }

        // 5. Slots e itens
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
    }

    public static boolean isShiftKeyDown() {
        return FurnaceGuiButton.isShiftKeyDown();
    }
}