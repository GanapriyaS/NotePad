package editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.UIManager;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 *
 * @author GanapriyaS
 */
public class Editor implements ActionListener,WindowListener
{
//    actionlistener to make open file opens to work
    JMenuItem neww,open,saveas,save,cut,copy,paste,replace,print,font,font_color,background_color,zoomin,zoomout;
    JTextArea textarea;
    JTextField txtFind,txtReplace;
    JFrame jf,font_frame;
    JDialog dialog;
    JLabel match;
    JComboBox font_family,font_size,font_style;
    JButton ok;
    File file;
    String fontfamily="Cantarell";
    String fontsize="15";
    String fontstyle="Plain";
    int style=0;
    int matches=0;
    JButton btnReplace,btnFind,btnReplaceAll;
    Editor()
    {       
            try
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            jf=new JFrame("*Untitled* - Notepad");
            jf.setSize(700,600);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setLocationRelativeTo(null);
            
            JMenuBar jmenubar=new JMenuBar();
            
            JMenu file=new JMenu("File");
            neww=new JMenuItem("New");
            neww.addActionListener(this);
            file.add(neww);
            
            open=new JMenuItem("Open");
            open.addActionListener(this);
            file.add(open);
            
            saveas=new JMenuItem("Save As");
            saveas.addActionListener(this);
            file.add(saveas);
            
            save=new JMenuItem("Save");
            save.addActionListener(this);
            file.add(save);
            
            jmenubar.add(file);
            
            JMenu edit=new JMenu("Edit");

            cut=new JMenuItem("Cut");
            cut.addActionListener(this);
            edit.add(cut);
            
            copy=new JMenuItem("Copy");
            copy.addActionListener(this);
            edit.add(copy);
            
            paste=new JMenuItem("Paste");
            paste.addActionListener(this);
            edit.add(paste);
            
            replace=new JMenuItem("Find and Replace");
            replace.addActionListener(this);
            edit.add(replace);
            
            print=new JMenuItem("Print");
            print.addActionListener(this);
            edit.add(print);
            
            jmenubar.add(edit);
            
            JMenu format=new JMenu("Format");

            font=new JMenuItem("Font");
            font.addActionListener(this);
            format.add(font);
            
            font_color=new JMenuItem("Font Color");
            font_color.addActionListener(this);
            format.add(font_color);
            
            background_color=new JMenuItem("Background Color");
            background_color.addActionListener(this);
            format.add(background_color);
            
            jmenubar.add(format);
            
            JMenu view=new JMenu("View");

            zoomin=new JMenuItem("Zoom in");
            zoomin.addActionListener(this);
            view.add(zoomin);
            
            zoomout=new JMenuItem("Zoom out");
            zoomout.addActionListener(this);
            view.add(zoomout);
                        
            jmenubar.add(view);
            
            jf.setJMenuBar(jmenubar);
            
            textarea=new JTextArea();
//            scrollbar when reduce the size of textarea
            JScrollPane scrollpane= new JScrollPane(textarea);
            scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jf.add(scrollpane);
            
//            jf.addWindowListener(new WindowListener);
            
            jf.addWindowListener(this);

            jf.setVisible(true);

    }
    public static void main(String[] args) {
        // TODO code application logic here
        new Editor();
    }
    void openFile()
        {
            JFileChooser fileChooser=new JFileChooser();
            int result=fileChooser.showOpenDialog(jf);
            if(result==0)
            {   
                textarea.setText("");
    //                parent in parenthesis parameter
                file=fileChooser.getSelectedFile();
    //            getselectedfile returns a file
                jf.setTitle(file.getName());
                try(FileInputStream fis=new FileInputStream(file))
                {
                    int i;
                    while((i=fis.read())!=-1)
                    {
                        textarea.append(String.valueOf((char)i));
    //                  append accept only string as input so used string method
                    }
                }
                catch(IOException ee)
                {
                    ee.printStackTrace();
                }
            }
        }
    int saveasFile()
    {
        JFileChooser fileChooser=new JFileChooser();
            int result=fileChooser.showSaveDialog(jf);
            if(result==0)
            {
                String text=textarea.getText();
                file=fileChooser.getSelectedFile();
                jf.setTitle(file.getName());
                try(FileOutputStream fos=new FileOutputStream(file))
                {
                    byte[] b=text.getBytes();
                    fos.write(b);
                }
                catch(IOException ee)
                {
                    ee.printStackTrace();
                }
            }
            return result;
    }
    void newFile()
    {
        String text=textarea.getText();
        if(!text.equals(""))
            {   
                int i=JOptionPane.showConfirmDialog(jf,"Do you want to save this file ?");
                if(i==0)
                {
                    int result=saveasFile();
                    if(result==0)
                    {
                    textarea.setText("");
                    jf.setTitle("*Untitled* - Notepad");
                    }
                }
                else if(i==1)
                {
                    textarea.setText("");
                }
                
            }
    }
    void saveFile()
    {
        String title=jf.getTitle();
        if(title.equals("*Untitled* - Notepad"))
        {
            saveasFile();
        }
        else 
        {
//            file.getAbsolutePath()
            String text=textarea.getText();
            try(FileOutputStream fos=new FileOutputStream(file))
            {
                byte[] b=text.getBytes();
                fos.write(b);
            }
            catch(IOException ee)
            {
                ee.printStackTrace();
            }
        }
    }
    void openFontFrame()
    {
        font_frame=new JFrame("Choose Font");
        font_frame.setSize(500,500);
        font_frame.setLocationRelativeTo(jf);
        font_frame.setLayout(null);
//        stlayout should be null or else one over the above result in collapse as setbounds not work
        
        String fonts[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_family=new JComboBox(fonts);
        font_family.setBounds(70,100,100,30);
        font_frame.add(font_family);
        
        String[] sizes={"10","12","14","18","24","28","34","42","56","72","96"};
        font_size=new JComboBox(sizes);
        font_size.setBounds(200,100,100,30);
        font_frame.add(font_size);
        
        String[] styles={"Plain","Bold","Italic"};
        font_style=new JComboBox(styles);
        font_style.setBounds(320,100,100,30);
        font_frame.add(font_style);
        
        ok=new JButton("OK");
        ok.setBounds(200,200,100,50);
        ok.addActionListener(this);
        font_frame.add(ok);
        
        font_frame.setVisible(true);
    }
    
    void setFontTextarea()
    {
        fontfamily=(String)font_family.getSelectedItem();
        fontsize=(String)font_size.getSelectedItem();
        fontstyle=(String)font_style.getSelectedItem();
                    
            if(fontstyle.equals("Plain"))
                style=0;
            else if(fontstyle.equals("Bold"))
                style=1;
            else if(fontstyle.equals("Italic"))
                style=2;
            
//            style or fontsize should be int for constructor of fontt
            Font fontt=new Font(fontfamily,style,Integer.parseInt(fontsize));
            textarea.setFont(fontt);
            
            font_frame.setVisible(false);
    }
    void printJFrame(){
            boolean complete;
        try {
            complete = textarea.print();
            if(complete){
                JOptionPane.showMessageDialog(null,"Done printing","Information",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,"Error occured","Printer",JOptionPane.ERROR_MESSAGE);
            }
        } catch (PrinterException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
    {
        public MyHighlightPainter(Color color)
        {
            super(color);
        }
    }
    Highlighter.HighlightPainter MyHighlightPainter= new MyHighlightPainter(Color.yellow);
    public void removeHighlight(JTextComponent textComp)
    {
        Highlighter h=textComp.getHighlighter();
        Highlighter.Highlight[] h1=h.getHighlights();
        for(int i=0; i< h1.length;i++)
        {
            if(h1[i].getPainter() instanceof MyHighlightPainter)
            {
                h.removeHighlight(h1[i]);
            }
     }
    }
    public void highlight(JTextComponent textComp, String from)
    {   
        removeHighlight(textComp);
        try{
                Highlighter h=textComp.getHighlighter();
//                Document doc=textComp.getDocument();
//                String text=doc.getText(0,doc.getLength());
                String text=textComp.getText();
                int pos=0;
                while((pos=text.toUpperCase().indexOf(from.toUpperCase(),pos))>=0)
                {
                    h.addHighlight(pos, pos+ from.length(), MyHighlightPainter);
                    pos+=from.length();
                }
            }
        catch(Exception e1)
            {}
    }
    void btnfind()
    {   
        match();
        String from = txtFind.getText();
        if(textarea.getText().toUpperCase().indexOf(from.toUpperCase())<0  ||  from.length() <= 0 || textarea.getText().length()<=0)
        {
            removeHighlight(textarea);
            JOptionPane.showMessageDialog(dialog,"No matched words");
        }
        else
        highlight(textarea,from);
      
}   
    void match()
    {  
        String from = txtFind.getText();
        int pos=0;
        int matches=0;
        while((pos=textarea.getText().toUpperCase().indexOf(from.toUpperCase(),pos))>=0 && textarea.getText().length()>0 && from.length()>0)
        {
            matches+=1;
            pos=pos+from.length();
        }
        match.setText(matches+" matches");
    }
    void btnreplace()
    {   
        String from = txtFind.getText();
        int start = textarea.getText().toUpperCase().indexOf(from.toUpperCase());
        if (start >= 0 && from.length() > 0 && textarea.getText().length()>0)
        {   
          textarea.replaceRange(txtReplace.getText(), start, start + from.length());
        }
        else
            JOptionPane.showMessageDialog(dialog,"No matched words");
        match();
    }
    void btnreplaceall()
    {   
        match();
        String from = txtFind.getText();
        while(textarea.getText().toUpperCase().indexOf(from.toUpperCase()) >=0 && textarea.getText().length()>0 && from.length()>0)
        {
            btnreplace();
        }
        if(textarea.getText().toUpperCase().indexOf(from.toUpperCase())<0 || textarea.getText().length()<=0 ||from.length()<=0)
        {
            JOptionPane.showMessageDialog(dialog,"No matched words");
        }
    }
    void replace(){
        dialog = new JDialog(jf);
        dialog.setLayout(new GridLayout(3,4));
        dialog.setTitle("Find and Replace");
        txtFind = new JTextField();
        txtReplace = new JTextField();
        btnFind = new JButton("Find");
        btnFind.addActionListener(this);
        btnReplace = new JButton("Replace");
        btnReplace.addActionListener(this);
        btnReplaceAll = new JButton("Replace All");
        btnReplaceAll.addActionListener(this);
        match=new JLabel(matches+" matches");
        
        dialog.add(new JLabel("Find: "));
        dialog.add(txtFind);
        dialog.add(new JLabel(""));
        dialog.add(btnFind);
        dialog.add(new JLabel("Replace with: "));
        dialog.add(txtReplace);
        dialog.add(new JLabel(""));
        dialog.add(btnReplace);
        dialog.add(new JLabel(""));
        dialog.add(match);
        dialog.add(new JLabel(""));
        dialog.add(btnReplaceAll);
       
        
        dialog.pack();
        dialog.setLocationRelativeTo(jf);
        dialog.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==neww){
//            textarea.setText() will replace whole not append
           newFile();
        }
        if(e.getSource()==open){
//            file explorer
            openFile();
        }
       
         if(e.getSource()==saveas)
        {
            saveasFile();
        } 
         
        if(e.getSource()==save)
        {
            saveFile();
        }
        if(e.getSource()==cut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==print)
        {
            printJFrame();
        }
        if(e.getSource()==font)
        {
            openFontFrame();
        }
        if(e.getSource()==ok)
        {
            setFontTextarea();
        }
        if(e.getSource()==font_color)
        {
            Color c=JColorChooser.showDialog(jf,"Choose Font Color",Color.black);
            textarea.setForeground(c);
//          textarea.setSelectedTextColor(c);
        }
        if(e.getSource()==background_color)
        {
            Color c=JColorChooser.showDialog(jf,"Choose Background Color",Color.white);
            textarea.setBackground(c);
            
        }
        if(e.getSource()==zoomin)
        {   
            int fs1=Integer.parseInt(fontsize);
            fontsize=String.valueOf(fs1+2);
            Font fontt=new Font(fontfamily,style,fs1+2);
            textarea.setFont(fontt);
            
        }
        if(e.getSource()==zoomout)
        {
//            Font a=textarea.getFont();
//            sys.out(a);
            int fs2=Integer.parseInt(fontsize);
            fontsize=String.valueOf(fs2-2);
            Font fontt=new Font(fontfamily,style,fs2-2);
            textarea.setFont(fontt);
        }
        if(e.getSource()==replace)
        {
            replace();
        }
        if(e.getSource()==btnFind)
        {
            btnfind();
        }
        if(e.getSource()==btnReplace)
        {
            btnreplace();
        }
        if(e.getSource()==btnReplaceAll)
        {
            btnreplaceall();
        }
            
    }       

    @Override
    public void windowOpened(WindowEvent we) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent we) {
        if(!(textarea.getText().equals("")))
        {
            int i=JOptionPane.showConfirmDialog(jf,"Do you want to save this file?");
            if(i==0)
                {
                    saveasFile();
                }
        }
    }
    @Override
    public void windowClosed(WindowEvent we) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent we) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent we) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
