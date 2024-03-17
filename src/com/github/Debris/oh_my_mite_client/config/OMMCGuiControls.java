package com.github.Debris.oh_my_mite_client.config;

import net.minecraft.*;
import org.lwjgl.input.Keyboard;

public class OMMCGuiControls extends GuiScreen {
    private final GuiScreen parentScreen;
    protected String screenTitle = "OMMC按键绑定";
    private int buttonId = -1;
    private int page_index;

    public OMMCGuiControls(GuiScreen par1GuiScreen) {
        this.parentScreen = par1GuiScreen;
    }

    private int getLeftBorder() {
        return this.width / 2 - 155;
    }

    private int getKeybindButtonPosX(int index) {
        index %= 10;
        return this.getLeftBorder() + index % 2 * 160;
    }

    private int getKeybindButtonPosY(int index) {
        index %= 10;
        return this.height / 6 + 24 * (index / 2) + 6;
    }

    @Override
    public void initGui() {
        for (int var2 = 0; var2 < FeatureToggle.values().length; ++var2) {
            this.buttonList.add(new GuiSmallButton(var2, this.getKeybindButtonPosX(var2), this.getKeybindButtonPosY(var2), 70, 20, FeatureToggle.values()[var2].getKeybind().keyDescription));
        }
        this.setKeybindButtonVisibilities();
        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 168 - 24, I18n.getString("gui.nextPage")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.getString("gui.done")));
    }

    private void setKeybindButtonVisibilities() {
        for (int i = 0; i < FeatureToggle.values().length; ++i) {
            ((GuiButton) this.buttonList.get(i)).drawButton = this.isKeybindButtonVisible(i);
        }
    }

    private boolean isKeybindButtonVisible(int index) {
        return index >= this.page_index * 10 && index < (this.page_index + 1) * 10;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        for (int var2 = 0; var2 < FeatureToggle.values().length; ++var2) {
            ((GuiButton) this.buttonList.get(var2)).displayString = FeatureToggle.values()[var2].getKeybind().keyDescription;
        }

        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (par1GuiButton.id == 201) {
            if (++this.page_index > (FeatureToggle.values().length - 1) / 10) {
                this.page_index = 0;
            }

            this.setKeybindButtonVisibilities();
        } else {
            this.buttonId = par1GuiButton.id;
            par1GuiButton.displayString = "> " + getOptionDisplayString(par1GuiButton.id) + " <";
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        if (this.buttonId >= 0) {
            setKeyBinding(this.buttonId, -100 + par3);
            ((GuiButton) this.buttonList.get(this.buttonId)).displayString = getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.mouseClicked(par1, par2, par3);
        }
    }

    protected void keyTyped(char par1, int par2) {
        if (this.buttonId >= 0) {
            setKeyBinding(this.buttonId, par2);
            ((GuiButton) this.buttonList.get(this.buttonId)).displayString = getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.keyTyped(par1, par2);
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        int var5 = 0;

        while (var5 < FeatureToggle.values().length) {
            if (!this.isKeybindButtonVisible(var5)) {
                ++var5;
            } else {
                boolean var6 = false;

                for (int var7 = 0; var7 < FeatureToggle.values().length; ++var7) {
                    if (var7 != var5 && FeatureToggle.values()[var5].getKeybind().keyCode == FeatureToggle.values()[var7].getKeybind().keyCode) {
                        var6 = true;
                        break;
                    }
                }

                for (int var7 = 0; var7 < this.mc.gameSettings.keyBindings.length; ++var7) {
                    if (FeatureToggle.values()[var5].getKeybind().keyCode == this.mc.gameSettings.keyBindings[var7].keyCode) {
                        var6 = true;
                        break;
                    }
                }

                if (this.buttonId == var5) {
                    ((GuiButton) this.buttonList.get(var5)).displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
                } else if (var6) {
                    ((GuiButton) this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + getOptionDisplayString(var5);
                } else {
                    ((GuiButton) this.buttonList.get(var5)).displayString = getOptionDisplayString(var5);
                }

                this.drawString(this.fontRenderer, getKeyBindingDescription(var5), this.getKeybindButtonPosX(var5) + 70 + 6, this.getKeybindButtonPosY(var5) + 7, -1);
                ++var5;
            }
        }

        super.drawScreen(par1, par2, par3);
    }

    public void setKeyBinding(int par1, int par2) {
        FeatureToggle.values()[par1].getKeybind().keyCode = par2;
        FeatureToggle.saveOptions();
    }

    public String getOptionDisplayString(int par1) {
        int var2 = FeatureToggle.values()[par1].getKeybind().keyCode;
        return getKeyDisplayString(var2);
    }

    public static String getKeyDisplayString(int par0) {
        return Keyboard.getKeyName(par0);
    }

    public String getKeyBindingDescription(int par1) {
        return I18n.getString(FeatureToggle.values()[par1].getKeybind().keyDescription);
    }
}

