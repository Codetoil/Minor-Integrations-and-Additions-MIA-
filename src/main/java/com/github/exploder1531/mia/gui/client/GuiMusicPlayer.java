package com.github.exploder1531.mia.gui.client;

import com.github.exploder1531.mia.gui.container.ContainerMusicPlayer;
import com.github.exploder1531.mia.inventory.MusicPlayerInventory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMusicPlayer extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("mia:textures/gui/container/music_player.png");
    private final MusicPlayerInventory inventory;
    
    private GuiButtonImage pageLeftButton;
    private GuiButtonImage pageRightButton;
    
    public GuiMusicPlayer(InventoryPlayer playerInventory, MusicPlayerInventory inventory)
    {
        super(new ContainerMusicPlayer(playerInventory, inventory));
        this.inventory = inventory;
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;
        this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
        
        int maxSlots = 7;
        if (inventory.openPage * 7 <= inventory.getSizeInventory() && (inventory.openPage + 1) * 7 > inventory.getSizeInventory())
            maxSlots = (inventory.getSizeInventory() % 7) + 1;
        
        // If needed, draw additional inventory slots (the first one is always drawn)
        for (int slot = 1; slot < maxSlots; slot++)
            this.drawTexturedModalRect(posX + 25 + slot * 18, posY + 53, 25, 53, 18, 18);
    }
    
    @Override
    public void updateScreen()
    {
        boolean leftVisible = inventory.openPage > 0;
        boolean rightVisible = (inventory.openPage + 1) * 7 < inventory.getSizeInventory();
        
        pageLeftButton.enabled = leftVisible;
        pageLeftButton.visible = leftVisible;
        pageRightButton.enabled = rightVisible;
        pageRightButton.visible = rightVisible;
        
        super.updateScreen();
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            if (inventory.openPage > 0)
                inventory.openPage--;
        }
        else if (button.id == 1)
        {
            if ((inventory.openPage + 1) * 7 < inventory.getSizeInventory())
                inventory.openPage++;
        }
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        
        pageLeftButton = addButton(new GuiButtonImage(0, 9 + guiLeft, 51 + guiTop, 14, 22, 196, 0, 32, texture));
        pageRightButton = addButton(new GuiButtonImage(1, 153 + guiLeft, 51 + guiTop, 14, 22, 178, 0, 32, texture));
    }
}
