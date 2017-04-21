/*
Time Machine
Copyright (C) 2017 Alexis Lavaud

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.mario8705.timemachine;

import org.lwjgl.glfw.GLFW;

public final class GUI {
    public static final int GUI_BUTTON_DEFAULT_WIDTH = 200,
                            GUI_BUTTON_DEFAULT_HEIGHT = 40;

    public static boolean button(String text, int x, int y) {
        return button(text, x, y, GUI_BUTTON_DEFAULT_WIDTH, GUI_BUTTON_DEFAULT_HEIGHT);
    }

    public static boolean button(String text, int x, int y, int width, int height) {
        InputManager inputManager = Application.getInstance().getInputManager();
        int mouseX = inputManager.getMouseX(), mouseY = inputManager.getMouseY();

        boolean hovered = (mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY <= y + height);
        boolean clicked = inputManager.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1);
        boolean pressed = inputManager.isButtonReleased(GLFW.GLFW_MOUSE_BUTTON_1);

        if (hovered) {
            if (clicked) {
                renderButtonClicked(text, x, y, width, height);
            } else {
                renderButtonHovered(text, x, y, width, height);
            }
        } else {
            renderButtonNormal(text, x, y, width, height);
        }

        return (hovered && pressed);
    }

    private static void renderButtonNormal(String text, int x, int y, int width, int height) {
        RenderManager renderManager = Application.getInstance().getRenderManager();

        renderManager.setColor(0xff0000ff);
        renderManager.fillRect(x, y, width, height);
    }

    private static void renderButtonHovered(String text, int x, int y, int width, int height) {
        RenderManager renderManager = Application.getInstance().getRenderManager();

        renderManager.setColor(0x00ff00ff);
        renderManager.fillRect(x, y, width, height);
    }

    private static void renderButtonClicked(String text, int x, int y, int width, int height) {
        RenderManager renderManager = Application.getInstance().getRenderManager();

        renderManager.setColor(0x0000ffff);
        renderManager.fillRect(x, y, width, height);
    }
}
