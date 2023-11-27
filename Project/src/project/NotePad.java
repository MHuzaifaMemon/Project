package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;



public class NotePad extends JFrame implements ActionListener {
	////////////////////////////////////////////

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }

    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);

    public void highlight(JTextComponent textcomp, String pattern) {

        try {
            Highlighter hilite = textcomp.getHighlighter();
            Document doc = textcomp.getDocument();

            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }
        } catch (Exception e) {

        }
    }

	/////////////////////////////////////////
    private Stack stack = new Stack();
    private JTextArea area;
    private JScrollPane scpane;
    String text = "";
       NotePad() {
        setTitle("NotePad");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon NotePadicon = new ImageIcon("C:\\Users\\UII\\Desktop\\Data Structures (Lab)\\FinalProject\\src\\project\\notepad.png");
        Image icon = NotePadicon.getImage();
        setIconImage(icon);

        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);

        JMenu file = new JMenu("File");
        file.setFont(new Font("AERIAL", Font.PLAIN, 14));

        JMenuItem newdoc = new JMenuItem("NEW");
        newdoc.addActionListener(this);
        newdoc.setActionCommand("NEW");

        JMenuItem open = new JMenuItem("OPEN");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("SAVE");
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem print = new JMenuItem("PRINT");
        print.addActionListener(this);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exit = new JMenuItem("EXIT");
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(exit);
        file.add(print);

        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("AERIAL", Font.PLAIN, 14));
        JMenuItem copy = new JMenuItem("COPY");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem paste = new JMenuItem("PASTE");
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem cut = new JMenuItem("CUT");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem selectall = new JMenuItem("SELECT ALL");
        selectall.addActionListener(this);
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem find = new JMenuItem("FIND");
        find.addActionListener(this);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        JMenuItem findandreplace = new JMenuItem("FIND AND REPLACE");
        findandreplace.addActionListener(this);
        findandreplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem undText = new JMenuItem("UNDO");
        undText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undText.addActionListener(this);

        JMenuItem rdoText = new JMenuItem("REDO");
        rdoText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        rdoText.addActionListener(this);

        JMenu about = new JMenu("Help");

        JMenuItem notepad = new JMenuItem("About");
        notepad.addActionListener(this);

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        scpane = new JScrollPane(area);
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menubar);
        menubar.add(file);
        menubar.add(edit);
        menubar.add(about);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(exit);
        file.add(print);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);
        edit.add(find);
        edit.add(findandreplace);
        edit.add(undText);
        edit.add(rdoText);

        about.add(notepad);

        add(scpane, BorderLayout.CENTER);
        setVisible(true);
    }

   	public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("NEW")) {
            area.setText("");
        } else if (source.getText().equals("OPEN")) {
            JFileChooser choose = new JFileChooser();
            choose.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("only .txt Files", "txt");

            choose.addChoosableFileFilter(restrict);
            int action = choose.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = choose.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader, null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (source.getActionCommand().equals("SAVE")) {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("SAVE");
            int action = saveas.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File filename = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(filename));
                area.write(outFile);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (source.getActionCommand().equals("PRINT")) {
            try {
                area.print();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (source.getActionCommand().equals("EXIT")) {
            System.exit(0);
        } else if (source.getActionCommand().equals("COPY")) {
            text = area.getSelectedText();
        } else if (source.getActionCommand().equals("PASTE")) {
            area.insert(text, area.getCaretPosition());
        } else if (source.getActionCommand().equals("CUT")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } else if (source.getActionCommand().equals("SELECT ALL")) {
            area.selectAll();
        } else if (source.getActionCommand().equals("FIND")) {
            JFrame findFrame = new JFrame("FIND");
            findFrame.setSize(400, 150);
            findFrame.setLocationRelativeTo(null);
            findFrame.setLayout(null);
            findFrame.setResizable(false);

            JTextField textArea = new JTextField(20);
            JButton btn = new JButton("Find");

            JLabel findLabel = new JLabel("Find ");
            findLabel.setBounds(25, 30, 40, 40);
            findFrame.add(findLabel);
            textArea.setBounds(60, 35, 150, 30);

            JLabel Label1 = new JLabel("");
            Label1.setBounds(60, 55, 150, 30);
            findFrame.add(Label1);
            btn.setBounds(250, 40, 70, 30);
            btn.setBackground(Color.white);

            findFrame.add(textArea);
            findFrame.add(btn);
            findFrame.setVisible(true);
            findFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(findFrame,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        area.setText(area.getText());
                    }
                }
            });
            //main logic for label text enter for find word
////////////////////////////////////////////
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String textIn = area.getText();
                if (textIn.contains(textArea.getText())) {

                    if (textIn.charAt(textIn.length() - 1) != ' ') {

                        Label1.setText(" ");
                        String line = textIn + " ";
                        area.setText(line);
                    }

                    if (!textIn.equals("")) {

                        String[] words = textIn.split("\\s+");
//                        int count = 0;
//                        for (int i = 0; i < words.length; i++) {
//                            if (words[i].equals(textArea.getText())) {
//                                count++;
//                            }
//                        }
                       // Label1.setText("Word Exist : " + Integer.toString(count) + " times");
                        highlight(area, textArea.getText());
                        String areaText = area.getText();
                        stack.push(areaText);
                    }
                    return;
                } else {

                    Label1.setText("Match Word Not Found");
                }
            }
        });
        // findFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
        ///////////////////////////////

        else if (source.getActionCommand().equals("FIND AND REPLACE")) {
            JFrame replaceFrame = new JFrame("FIND AND REPLACE");
            replaceFrame.setSize(400, 150);
            replaceFrame.setLocationRelativeTo(null);
            replaceFrame.setLayout(null);
            replaceFrame.setResizable(false);

            JLabel findLabel = new JLabel("Find ");
            JTextField findText = new JTextField(20);

            findLabel.setBounds(40, 20, 50, 25);
            findText.setBounds(70, 20, 150, 25);
            replaceFrame.add(findText);
            replaceFrame.add(findLabel);

            JLabel replaceLabel = new JLabel("Replace ");
            JTextField replaceText = new JTextField(20);

            replaceLabel.setBounds(20, 50, 50, 25);
            replaceText.setBounds(70, 50, 150, 25);
            replaceFrame.add(replaceText);
            replaceFrame.add(replaceLabel);

            JButton btn = new JButton("Replace");

            btn.setBounds(240, 20, 90, 55);
            btn.setBackground(Color.white);
            JLabel label2 = new JLabel();
            label2.setBounds(70, 70, 150, 25);

            replaceFrame.add(label2);
            //main logic
            ////////////////////////////
        btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            String text = area.getText();
            String fText = findText.getText();
            String rText = replaceText.getText();

            if (text.contains(fText)) {
                label2.setText("");

                String replaceString = text.replace(fText, rText);
                area.setText(replaceString);
                stack.push(replaceString);
                return;
            } else {

                label2.setText("Match Not Found");

            }
        }
    });
////////////////////////////////
            replaceFrame.add(btn);
            replaceFrame.setVisible(true);
        }
        else if (source.getActionCommand().equals("UNDO")) {
        	/////////////////////////
            String textt = stack.pop();
             //if (!textt.isEmpty()) {
            area.setText(textt);
             //}
        	
        	}
        	//////////////////////
        else if (source.getActionCommand().equals("REDO")) {
        	/////////////////////////
             String textt = stack.redoPop();
            // if (!textt.isEmpty()) {
             area.setText(textt);
             //}
        	/////////////////////////

        } else if (source.getActionCommand().equals("About")) {
            new About().setVisible(true);
        }
    }

    public static void main(String args[]) {
        new NotePad();
    }
}
