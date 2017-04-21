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

    public Application(String appTitle) {
        this.renderWindow = new RenderWindow(800, 600, appTitle);
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

                renderWindow.pollEvents();

                update(elapsedTime);
                render();

                renderWindow.presentFrame();
            }
        } finally {
            appDestroy();
        }
    }

    private void appDestroy() {
        if (renderWindow != null) {
            renderWindow.destroy();
        }

        destroy();
    }

    public void stop() {
        running = false;
    }

    protected abstract void init();
    protected abstract void update(float tpf);
    protected abstract void render();
    protected abstract void destroy();
}
