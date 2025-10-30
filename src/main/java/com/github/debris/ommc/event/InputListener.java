package com.github.debris.ommc.event;

import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;

public class InputListener implements IMouseInputHandler {
    private static final InputListener Instance = new InputListener();

    public static InputListener getInstance() {
        return Instance;
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int eventButton, boolean eventButtonState) {
        if (eventButton == 0) {
            if (eventButtonState) {
                // TODO
            }
        }
        return false;
    }
}
