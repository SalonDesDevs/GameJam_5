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

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public final class InputManager {
    private final TIntIntMap frameKeysStateMap;
    private final TIntIntMap frameMouseButtonsStateMap;
    private final TIntList heldKeysList;
    private final TIntList heldMouseButtonsList;
    private int mouseX;
    private int mouseY;

    public InputManager() {
        this.frameKeysStateMap = new TIntIntHashMap();
        this.frameMouseButtonsStateMap = new TIntIntHashMap();
        this.heldKeysList = new TIntArrayList();
        this.heldMouseButtonsList = new TIntArrayList();
    }

    public void loadCallbacks(RenderWindow renderWindow) {
        final long windowHandle = renderWindow.getWindowHandle();

        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_STICKY_KEYS, 1);
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_STICKY_MOUSE_BUTTONS, 1);
        GLFW.glfwSetKeyCallback(windowHandle, this::keyCallback);
        GLFW.glfwSetMouseButtonCallback(windowHandle, this::mouseCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, this::cursorPosCallback);
    }

    public void beginFrame() {
        frameKeysStateMap.clear();
        frameMouseButtonsStateMap.clear();
    }

    public boolean isKeyPressed(int key) {
        int scancode = GLFW.glfwGetKeyScancode(key);

        if (frameKeysStateMap.containsKey(scancode)) {
            return frameKeysStateMap.get(scancode) == GLFW.GLFW_PRESS;
        }

        return false;
    }

    public boolean isKeyReleased(int key) {
        int scancode = GLFW.glfwGetKeyScancode(key);

        if (frameKeysStateMap.containsKey(scancode)) {
            return frameKeysStateMap.get(scancode) == GLFW.GLFW_RELEASE;
        }

        return false;
    }

    public boolean isKeyDown(int key) {
        return heldKeysList.contains(GLFW.glfwGetKeyScancode(key));
    }

    public boolean isButtonPressed(int button) {
        if (frameMouseButtonsStateMap.containsKey(button)) {
            return frameMouseButtonsStateMap.get(button) == GLFW.GLFW_PRESS;
        }

        return false;
    }

    public boolean isButtonReleased(int button) {
        if (frameMouseButtonsStateMap.containsKey(button)) {
            return frameMouseButtonsStateMap.get(button) == GLFW.GLFW_RELEASE;
        }

        return false;
    }

    public boolean isButtonDown(int button) {
        return heldMouseButtonsList.contains(button);
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        frameKeysStateMap.put(scancode, action);

        if (action == GLFW.GLFW_PRESS) {
            if (!heldKeysList.contains(scancode)) {
                heldKeysList.add(scancode);
            }
        } else if (action == GLFW.GLFW_RELEASE) {
            heldKeysList.remove(scancode);
        }
    }

    private void mouseCallback(long window, int button, int action, int mods) {
        frameMouseButtonsStateMap.put(button, action);

        if (action == GLFW.GLFW_PRESS) {
            if (!heldMouseButtonsList.contains(button)) {
                heldMouseButtonsList.add(button);
            }
        } else if (action == GLFW.GLFW_RELEASE) {
            heldMouseButtonsList.remove(button);
        }
    }

    private void cursorPosCallback(long window, double xpos, double ypos) {
        mouseX = (int) xpos;
        mouseY = (int) ypos;
    }
}
