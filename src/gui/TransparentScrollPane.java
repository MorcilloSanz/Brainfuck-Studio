package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class TransparentScrollPane extends JScrollPane {

    TransparentScrollPane(JComponent editor) {
        super(editor);
        init();
        /*
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        */
    }

    private void init() {

        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();

            @Override protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = null;
                JScrollBar sb = (JScrollBar)c;
                if(!sb.isEnabled() || r.width>r.height)
                    return;
                else if(isDragging)
                    color = new Color(201, 153, 255, 255);
                else if(isThumbRollover())
                    color = new Color(201, 153, 255, 255);
                else
                    color = new Color(160, 120, 204, 255);

                g2.setPaint(color);
                int thumbWidth = 1;
                int x = (r.width - thumbWidth) / 2;
                g2.fillRect(r.x + x ,r.y,thumbWidth,r.height);
                g2.dispose();
            }
            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });

        getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();
            @Override protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = null;
                JScrollBar sb = (JScrollBar)c;

                int orientation = scrollbar.getOrientation();
                int x = r.x;
                int y = r.y;
                int thumbSize = 1;

                int width = orientation == JScrollBar.VERTICAL ? thumbSize : r.width;
                width = Math.max(width, thumbSize);

                int height = orientation == JScrollBar.VERTICAL ? r.height : thumbSize;
                height = Math.max(height, thumbSize);

                int margin = 7;

                Graphics2D graphics2D = (Graphics2D) g.create();
                graphics2D.setColor(new Color(201, 153, 255, 255));
                graphics2D.fillRect(x, y + margin, width, thumbSize);
                graphics2D.dispose();
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });
    }
}
