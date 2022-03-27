import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.JPanel;

public class DrawingProgram extends JFrame {

    private Point mousePnt = new Point();
    private Point p1, p2;
    private int x,y,x1,y1;
    private int rec_height_x, rec_length_y;
    private JSlider penSize = new JSlider(JSlider.HORIZONTAL,1,30,4);

    private ImageIcon icons[] = {
            new ImageIcon(getClass().getResource("icon/square.png")),
            new ImageIcon(getClass().getResource("icon/rectangle.png")),
            new ImageIcon(getClass().getResource("icon/circle.png")),
            new ImageIcon(getClass().getResource("icon/oval.png")),
    }; //58*58

    private ImageIcon iconToolbar[] = {
            new ImageIcon(getClass().getResource("icon/color-palatte.png")),
            new ImageIcon(getClass().getResource("icon/clear.png")),
            new ImageIcon(getClass().getResource("icon/pen.png")),
    };

    public static int pen = 4, choice;
    public static Color penColor = new Color(0,0,0);
    private JButton penSelect = new JButton(iconToolbar[2]);

    private JButton shape1 = new JButton(icons[0]);
    private JButton shape2 = new JButton(icons[1]);
    private JButton shape3 = new JButton(icons[2]);
    private JButton shape4 = new JButton(icons[3]);

    private JButton colorSelect = new JButton(iconToolbar[0]);
    private JButton eraser = new JButton(iconToolbar[1]);
    private JLabel welcome = new JLabel("Welcome to our Drawing Program!");

    public static void main(String[] a) {

        DrawingProgram dp = new DrawingProgram();

        dp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        dp.setSize(990,600);
        dp.setVisible(true);
        dp.setLayout(null);
        JOptionPane.showMessageDialog(dp,
                "Welcome to E-PRO Drawing Board \n \n " +
                        "Below are the mouse operations that can be operated on the components : \n \n" +
                        "1. Pen : Click \n" +
                        "2. Pen Size : Click + Drag \n" +
                        "3. Pen Color : Cick \n" +
                        "4. Eraser : Click \n" +
                        "1. Square : Click + Drag \n " +
                        "2. Oval : Click + Drag \n" +
                        "3. Circle : 2 points clicks \n" +
                        "4. Rectangle : 2 points clicks",
                "Welcome !",
                JOptionPane.INFORMATION_MESSAGE);
    }

    DrawingProgram() {

        DrawingProgramLogic dl = new DrawingProgramLogic();

        JPanel welcomePanel = new JPanel();
        JPanel jp = new JPanel();
        JPanel shape = new JPanel();
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        this.setBackground(new Color(255,255,255));
        toolbar.setBackground(Color.WHITE); jp.setBackground(Color.WHITE); shape.setBackground(Color.WHITE);

        this.add(jp, BorderLayout.CENTER);
        this.add(welcomePanel,BorderLayout.NORTH);
        this.add(toolbar,BorderLayout.SOUTH);

        toolbar.add(penSelect);
        toolbar.add(penSize);
        toolbar.add(colorSelect);
        toolbar.add(eraser);

        colorSelect.setVisible(true);
        colorSelect.setPreferredSize(new Dimension(120,25));
        eraser.setPreferredSize(new Dimension(120,25));
        toolbar.setBorder(new EmptyBorder(-10, 5, -10, -5));

        welcomePanel.add(welcome);

        penSelect.setOpaque(false); penSelect.setContentAreaFilled(false); penSelect.setBorderPainted(false);
        colorSelect.setOpaque(false); colorSelect.setContentAreaFilled(false); colorSelect.setBorderPainted(false);
        eraser.setOpaque(false); eraser.setContentAreaFilled(false); eraser.setBorderPainted(false);
        shape1.setOpaque(false); shape1.setContentAreaFilled(false); shape1.setBorderPainted(false);
        shape2.setOpaque(false); shape2.setContentAreaFilled(false); shape2.setBorderPainted(false);
        shape3.setOpaque(false); shape3.setContentAreaFilled(false); shape3.setBorderPainted(false);
        shape4.setOpaque(false); shape4.setContentAreaFilled(false); shape4.setBorderPainted(false);

        shape.add(shape1); shape.add(shape4); shape.add(shape2); shape.add(shape3);
        toolbar.add(shape);

        penSelect.addActionListener(dl);
        colorSelect.addActionListener(dl);
        eraser.addActionListener(dl);
        shape1.addActionListener(dl); shape2.addActionListener(dl); shape3.addActionListener(dl); shape4.addActionListener(dl);

        jp.addMouseMotionListener(dl); jp.addMouseListener(dl);

        penSize.addChangeListener(dl);
    }

    private class DrawingProgramLogic extends MouseAdapter implements ActionListener, ChangeListener {

        public void actionPerformed(ActionEvent e) {
            // pen color chooser
            if(e.getSource() == colorSelect) {
                penColor = JColorChooser.showDialog(null,"Change pen colour",penColor);
            }
            // pen
            else if(e.getSource() == penSelect) {
                choice = 0;
            }
            // square
            else if(e.getSource() == shape1) {
                choice = 1;
            }
            // rectangle
            else if (e.getSource() == shape2) {
                choice = 2;
            }
            // circle
            else if(e.getSource() == shape3)  {
                choice = 3;
            }
            // oval
            else if(e.getSource() == shape4) {
                choice = 4;
            }
            // eraser
            else if(e.getSource() ==  eraser) {
                // penColor = new Color(255,255,255);
                repaint();
            }
        }

        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if(!source.getValueIsAdjusting()){
                pen = (int)source.getValue();
            }
        }

        public void mousePressed(MouseEvent me) {
            x = me.getX();
            y = me.getY();
        }

        public void mouseDragged(MouseEvent me) {

            Graphics g = getGraphics();

            mousePnt = me.getPoint();

            x1 = me.getX();
            y1 = me.getY();

            if(choice == 0) {
                g.setColor(penColor);
                g.fillOval(mousePnt.x + 6,mousePnt.y + 55, pen, pen);
            }
        }

        public void mouseClicked(MouseEvent me) {

            Graphics g = getGraphics();

            if (p1 == null || p2 != null) {
                p1 = me.getPoint(); //x
                p2 = null;
            }
            else {
                p2 = me.getPoint(); //y
            }

            if(p1 != null && p2 != null) {

                rec_height_x = p2.x - p1.x;
                rec_length_y = p2.y - p1.y;

                //rectangle
                if (choice == 2) {

                    if(p1.x != p2.x || p1.y != p2.y) { //IF SAME X OR SAME Y
                        g.setColor(Color.PINK);
                        g.fillRect(p1.x+5, p1.y+55, rec_height_x, rec_length_y);
                    }
                    else {
                        System.out.println("ERROR");
                    }
                }

                //circle
                else if (choice == 3) {

                    rec_height_x = p2.x - p1.x;
                    rec_length_y = p2.y - p1.y;

                    int width = ((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y));
                    double cir_width = Math.sqrt(width);
                    int cir_width_new = (int) cir_width;
                    int r = cir_width_new / 2;

                    int x = ((p1.x + p2.x) / 2) + 5;
                    int y = ((p1.y + p2.y) / 2) + 55;

                    g.setColor(Color.RED);
                    g.fillOval(x-r, y-r,cir_width_new, cir_width_new);
                }
            }
        }

        public void mouseReleased(MouseEvent me) {

            Graphics g = getGraphics();

            mousePnt = me.getPoint();

            x1 = me.getX();
            y1 = me.getY();

            //square
            if (choice == 1) {
                g.setColor(Color.GREEN);
                g.fillRect(x + 5,y + 45, Math.abs(x-x1), Math.abs(x-x1));
            }

            //oval
            if (choice == 4) {
                g.setColor(Color.black);
                g.fillOval(x + 8 ,y + 90, Math.abs(x-x1), Math.abs(y-y1));
            }
        }

        public void mouseEntered(MouseEvent me){}

        public void mouseExited(MouseEvent me){}

        public void mouseMoved(MouseEvent me){}
    }
}