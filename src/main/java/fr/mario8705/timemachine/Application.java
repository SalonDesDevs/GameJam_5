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

public abstract class Application {
    protected volatile boolean running;
    protected RenderWindow renderWindow;
    protected RenderManager renderManager;
    protected InputManager inputManager;
    protected Stage currentStage;
    private Stage nextStage;

    public Application(String appTitle) {
        this.renderWindow = new RenderWindow(800, 600, appTitle);
        this.renderManager = new RenderManager(renderWindow);
        this.inputManager = new InputManager();

        inputManager.loadCallbacks(renderWindow);
    }

    public void start() {
        if (running) return;

        running = true;

        try {
            init();

            float lastFrameTime = Time.getTime();

            while (running) {
                float currentTime = Time.getTime();
                float elapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                inputManager.beginFrame();
                renderManager.beginFrame();

                if (nextStage != null) {
                    if (currentStage != null) {
                        currentStage.destroy();
                    }

                    currentStage = nextStage;
                    currentStage.init();
                }

                renderWindow.pollEvents();

                if (currentStage != null) {
                    currentStage.update(elapsedTime);
                }

                update(elapsedTime);

                if (currentStage != null) {
                    currentStage.render(renderManager);
                }

                render(renderManager);

                renderWindow.presentFrame();
            }
        } finally {
            appDestroy();
        }
    }

    public void setNextStage(Stage nextStage) {
        this.nextStage = nextStage;

        nextStage.application = this;
    }

    private void appDestroy() {
        if (currentStage != null) {
            currentStage.destroy();
        }

        destroy();

        if (renderWindow != null) {
            renderWindow.destroy();
        }
    }

    public void stop() {
        running = false;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    protected abstract void init();
    protected abstract void update(float tpf);
    protected abstract void render(RenderManager renderManager);
    protected abstract void destroy();
}
