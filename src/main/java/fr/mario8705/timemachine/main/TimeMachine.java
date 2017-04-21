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
package fr.mario8705.timemachine.main;

import fr.mario8705.timemachine.Application;
import fr.mario8705.timemachine.MainMenuStage;
import fr.mario8705.timemachine.RenderManager;
import org.lwjgl.glfw.GLFW;

public final class TimeMachine extends Application {
    private TimeMachine() {
        super("Time Machine");

        setNextStage(new MainMenuStage());
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(float tpf) {
        if (renderWindow.shouldClose()) {
            running = false;
        }

        if (inputManager.isKeyPressed(GLFW.GLFW_KEY_A)) {
            System.out.println("A just pressed");
        }

        if (inputManager.isKeyReleased(GLFW.GLFW_KEY_A)) {
            System.out.println("A just released");
        }

        if (inputManager.isKeyDown(GLFW.GLFW_KEY_Z)) {
            System.out.println("Z is held down");
        }
    }

    @Override
    protected void render(RenderManager renderManager) {

    }

    @Override
    protected void destroy() {

    }

    public static void main(String[] args) {
        TimeMachine timeMachine = new TimeMachine();
        timeMachine.start();
    }
}
