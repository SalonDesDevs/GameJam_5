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

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public final class RenderManager {
    public static final int FVF_POSITION        = 0x01,
                            FVF_COLOR           = 0x02;
    private final RenderWindow renderWindow;
    private int currentColor;

    public RenderManager(RenderWindow renderWindow) {
        this.renderWindow = renderWindow;
        this.currentColor = 0xffffffff;
    }

    public void beginFrame() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glViewport(0, 0, renderWindow.getWidth(), renderWindow.getHeight());

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, renderWindow.getWidth(), renderWindow.getHeight(), 0.0, 1.0, -1.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    public void fillRect(float x, float y, float w, float h) {
        submitVertexArray(new Vertex[] {
                new Vertex(x, y, currentColor),
                new Vertex(x + w, y, currentColor),
                new Vertex(x + w, y + h, currentColor),
                new Vertex(x, y + h, currentColor)
        }, FVF_POSITION | FVF_COLOR, PrimitiveType.Quads);
    }

    public void submitVertexArray(Vertex[] vertices, int vertexFormat, PrimitiveType primitiveType) {
        switch (primitiveType) {
            case Quads:
                GL11.glBegin(GL11.GL_QUADS);
                break;

            case Triangles:
                GL11.glBegin(GL11.GL_TRIANGLES);
                break;
        }

        for (Vertex vertex : vertices) {
            if ((vertexFormat & FVF_COLOR) != 0) {
                final int color = vertex.color;

                GL11.glColor4ub(
                        (byte) ((color >> 24) & 0xff),
                        (byte) ((color >> 16) & 0xff),
                        (byte) ((color >> 8) & 0xff),
                        (byte) ((color >> 0) & 0xff)
                );
            }

            if ((vertexFormat & FVF_POSITION) != 0) {
                GL11.glVertex2f(vertex.x, vertex.y);
            }
        }

        GL11.glEnd();
    }

    public void setColor(int color) {
        this.currentColor = color;
    }

    public enum PrimitiveType {
        Quads,
        Triangles;
    }

    public static final class Vertex {
        public float x, y;
        public int color;

        public Vertex(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Vertex(float x, float y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}
