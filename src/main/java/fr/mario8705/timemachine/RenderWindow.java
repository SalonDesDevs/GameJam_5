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
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public final class RenderWindow {
    private long windowHandle;
    private int width;
    private int height;

    public RenderWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;

        if (!GLFW.glfwInit()) {
            throw new RuntimeException("glfwInit() failed");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        this.windowHandle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (windowHandle == MemoryUtil.NULL) {
            throw new RuntimeException("Could not create window");
        }

        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
    }

    public void resize(int width, int height) {
        GLFW.glfwSetWindowSize(windowHandle, width, height);

        this.width = width;
        this.height = height;
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void presentFrame() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public void destroy() {
        if (windowHandle != MemoryUtil.NULL) {
            GLFW.glfwDestroyWindow(windowHandle);
        }

        GLFW.glfwTerminate();
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
