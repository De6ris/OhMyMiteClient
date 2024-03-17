package com.github.Debris.oh_my_mite_client.config;

import net.minecraft.*;

public class OMMCGuiControls extends GuiScreen {
    private final GuiScreen parentScreen;
    protected String screenTitle = "OMMC按键绑定";
    private final OMMCOptions ommcOptions;
    private int buttonId = -1;
    private int page_index;

    public OMMCGuiControls(GuiScreen par1GuiScreen, OMMCOptions par2GameSettings) {
        this.parentScreen = par1GuiScreen;
        this.ommcOptions = par2GameSettings;
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
        for (int var2 = 0; var2 < this.ommcOptions.allKeyBindings.length; ++var2) {
            this.buttonList.add(new GuiSmallButton(var2, this.getKeybindButtonPosX(var2), this.getKeybindButtonPosY(var2), 70, 20, this.ommcOptions.allKeyBindings[var2].keyDescription));
        }
        this.setKeybindButtonVisibilities();
        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 168 - 24, I18n.getString("gui.nextPage")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.getString("gui.done")));
    }

    private void setKeybindButtonVisibilities() {
        for (int i = 0; i < this.ommcOptions.allKeyBindings.length; ++i) {
            ((GuiButton) this.buttonList.get(i)).drawButton = this.isKeybindButtonVisible(i);
        }
    }

    private boolean isKeybindButtonVisible(int index) {
        return index >= this.page_index * 10 && index < (this.page_index + 1) * 10;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        for (int var2 = 0; var2 < this.ommcOptions.allKeyBindings.length; ++var2) {
            ((GuiButton) this.buttonList.get(var2)).displayString = this.ommcOptions.allKeyBindings[var2].keyDescription;
        }

        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (par1GuiButton.id == 201) {
            if (++this.page_index > (this.ommcOptions.allKeyBindings.length - 1) / 10) {
                this.page_index = 0;
            }

            this.setKeybindButtonVisibilities();
        } else {
            this.buttonId = par1GuiButton.id;
            par1GuiButton.displayString = "> " + this.ommcOptions.getOptionDisplayString(par1GuiButton.id) + " <";
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        if (this.buttonId >= 0) {
            this.ommcOptions.setKeyBinding(this.buttonId, -100 + par3);
            ((GuiButton) this.buttonList.get(this.buttonId)).displayString = this.ommcOptions.getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.mouseClicked(par1, par2, par3);
        }
    }

    protected void keyTyped(char par1, int par2) {
        if (this.buttonId >= 0) {
            this.ommcOptions.setKeyBinding(this.buttonId, par2);
            ((GuiButton) this.buttonList.get(this.buttonId)).displayString = this.ommcOptions.getOptionDisplayString(this.buttonId);
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

        while (var5 < this.ommcOptions.allKeyBindings.length) {
            if (!this.isKeybindButtonVisible(var5)) {
                ++var5;
            } else {
                boolean var6 = false;

                for (int var7 = 0; var7 < this.ommcOptions.allKeyBindings.length; ++var7) {
                    if (var7 != var5 && this.ommcOptions.allKeyBindings[var5].keyCode == this.ommcOptions.allKeyBindings[var7].keyCode) {
                        var6 = true;
                        break;
                    }
                }

                for (int var7 = 0; var7 < this.mc.gameSettings.keyBindings.length; ++var7) {
                    if (this.ommcOptions.allKeyBindings[var5].keyCode == this.mc.gameSettings.keyBindings[var7].keyCode) {
                        var6 = true;
                        break;
                    }
                }

                if (this.buttonId == var5) {
                    ((GuiButton) this.buttonList.get(var5)).displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
                } else if (var6) {
                    ((GuiButton) this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + this.ommcOptions.getOptionDisplayString(var5);
                } else {
                    ((GuiButton) this.buttonList.get(var5)).displayString = this.ommcOptions.getOptionDisplayString(var5);
                }

                this.drawString(this.fontRenderer, this.ommcOptions.getKeyBindingDescription(var5), this.getKeybindButtonPosX(var5) + 70 + 6, this.getKeybindButtonPosY(var5) + 7, -1);
                ++var5;
            }
        }

        super.drawScreen(par1, par2, par3);
    }
}
