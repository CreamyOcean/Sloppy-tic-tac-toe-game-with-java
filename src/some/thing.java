package some;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class thing {
    public static void main(String[] args) throws IOException {
        JBuild fr = new JBuild("TTT: Turn Player 1", 312, 335);
            fr.bBuild();
            fr.bg(Color.black);
            fr.ttt();
    }
}

class JBuild{
   static JFrame frame;
   static JPanel[] jb;

    public JBuild(String title, int x, int y) throws IOException {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setSize(x, y);
        frame.setLayout(null);
        JPanel jp = new JPanel();
        frame.add(jp);
        frame.setResizable(false);
        frame.setBounds(1250, 500, x, y);
        clearFile();




    }
    public void bg(Color c){
        Container cont = frame.getContentPane();
        cont.setBackground(c);

    }
    int[] yCoordinates;
    int[] xCoordinates;
    public void bBuild(){
        xCoordinates = new int[]{0, 100, 200, 0, 100, 200, 0, 100, 200};
        yCoordinates = new int[]{0, 0, 0, 100, 100, 100, 200, 200, 200};
        jb = new JPanel[9];
        for(int i = 0; i < jb.length; i++){
            jb[i] = new JPanel();
            jb[i].setBounds(xCoordinates[i], yCoordinates[i], 95, 95);
            jb[i].setBackground(Color.white);
            frame.add(jb[i]);
        }
    }
    static File f = new File("D:\\Users\\Whipo\\Desktop\\Coding\\Java\\Thing\\data.txt");
    public void paintComponent(Graphics g, boolean z) {
        if(z) {
            g.setColor(Color.blue);
            g.drawLine(30, 30, 60, 60);
            g.drawLine(60, 30, 30, 60);
            try {
                readAndWrite("X"+(theChoice+1)+" ");
            } catch (IOException | InterruptedException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        }else{
            g.setColor(Color.blue);
            g.drawOval(25, 25, 50, 50);
            try {
                readAndWrite("O"+(theChoice+1)+" ");
            } catch (IOException | InterruptedException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            System.out.println(theChoice);

        }
    }
    String sortedScore;
    public void readAndWrite(String x) throws IOException, InterruptedException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);


        PrintWriter out = new PrintWriter(new FileWriter(f, true));
        out.print(x);
        out.close();
        String score = br.readLine();
        String[] sc = score.split("\\s+");
        Arrays.sort(sc);
        sortedScore = Arrays.toString(sc).replaceAll("[\\[\\],]", "");
        System.out.println(sortedScore);
        victory();
    }
    public void victory() throws IOException, InterruptedException {
        //1 2 3 - 4 5 6 - 7 8 9 - 1 5 9 - 3 5 7 - 3 6 9 - 2 5 8 - 1 4 7
        System.out.println(sortedScore);


        Pattern patX
                = Pattern
                .compile("((?=.*X1)(?=.*X5)(?=.*X9))" +
                        "|((?=.*X1)(?=.*X2)(?=.*X3))" +
                        "|((?=.*X4)(?=.*X5)(?=.*X6))" +
                        "|((?=.*X7)(?=.*X8)(?=.*X9))" +
                        "|((?=.*X3)(?=.*X5)(?=.*X7))" +
                        "|((?=.*X3)(?=.*X6)(?=.*X9))" +
                        "|((?=.*X2)(?=.*X5)(?=.*X8))" +
                        "|((?=.*X1)(?=.*X4)(?=.*X7))");
        Pattern patO
                = Pattern
                .compile("((?=.*O1)(?=.*O5)(?=.*O9))" +
                        "|((?=.*O1)(?=.*O2)(?=.*O3))" +
                        "|((?=.*O4)(?=.*O5)(?=.*O6))" +
                        "|((?=.*O7)(?=.*O8)(?=.*O9))" +
                        "|((?=.*O3)(?=.*O5)(?=.*O7))" +
                        "|((?=.*O3)(?=.*O6)(?=.*O9))" +
                        "|((?=.*O2)(?=.*O5)(?=.*O8))" +
                        "|((?=.*O1)(?=.*O4)(?=.*O7))");

        Matcher matX = patX.matcher(sortedScore);
        Matcher matO = patO.matcher(sortedScore);
        if(matX.find()) {

            winner("Victory for X!");
        }else if(matO.find()){
            winner("Victory for O!");
        }

    }
    public void winner(String vic) throws IOException {
        frame.setTitle("TTT: Turn Player 2");
        JOptionPane.showMessageDialog(null, vic);
        JPopupMenu jpm = new JPopupMenu();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        JBuild neu = new JBuild("TTT: Turn Player 2", frame.getWidth(), frame.getHeight());
        neu.bBuild();
        neu.bg(Color.black);
        neu.ttt();
    }
    public void clearFile() throws FileNotFoundException {
        Formatter ff = new Formatter("D:\\Users\\Whipo\\Desktop\\Coding\\Java\\Thing\\data.txt");

        ff.format("");
    }
    MouseListener ml;
    static int theChoice;
    public void ttt(){
        ml =  new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                String[] coords = e.getSource().toString().replaceAll("[a-zA-Z.\\[\\]=5-9]", "").split(",");
                String coordAdd = coords[1] + ", " + coords[2];
                switch (coordAdd) {
                    case "0, 0" -> theChoice = 0;
                    case "100, 0" -> theChoice = 1;
                    case "200, 0" -> theChoice = 2;
                    case "0, 100" -> theChoice = 3;
                    case "100, 100" -> theChoice = 4;
                    case "200, 100" -> theChoice = 5;
                    case "0, 200" -> theChoice = 6;
                    case "100, 200" -> theChoice = 7;
                    case "200, 200" -> theChoice = 8;
                }
                if (frame.getTitle().equalsIgnoreCase("TTT: Turn Player 1")) {

                    paintComponent(jb[theChoice].getGraphics(), true);
                    frame.setTitle("TTT: Turn Player 2");
                    remove();


                } else if (frame.getTitle().equalsIgnoreCase("TTT: Turn Player 2")) {
                    paintComponent(jb[theChoice].getGraphics(), false);
                    frame.setTitle("TTT: Turn Player 1");
                    remove();
                }
            }




            @Override
            public void mouseReleased(MouseEvent e) {



            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        for (JPanel jPanel : jb) {

            jPanel.addMouseListener(ml);

        }
    }
    public void remove(){
        jb[theChoice].removeMouseListener(ml);
        System.out.println(theChoice);
    }
}

