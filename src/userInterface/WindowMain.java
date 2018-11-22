package userInterface;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import library.IConstants;
import logic.Controller;

@SuppressWarnings("serial")
public class WindowMain extends JFrame implements IConstants{
	private Controller controller;
	private JButton btnLoadText;
	private JButton btnAnalyze;
	private BufferedImage buffer;
	private boolean Text;
	private String Url;
	private JLabel Info;
	
	public WindowMain() {
		this.setTitle(TITLE_WINDOW);
		this.setLayout(null);
		this.setUndecorated(true);
	    this.setExtendedState(MAXIMIZED_BOTH);
	    this.getContentPane().setBackground(Color.WHITE);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    initComponent();
	    this.setVisible(true);
	}
	
	private void initComponent() {
		URL url = null;
		Text = false;
		controller = new Controller();
		Info = new JLabel();
		Image Imagen = null;
		BufferedImage buffer = null;
		try {
			url = new URL("https://pinguinoenlaroca.com/agencia/wp-content/uploads/2018/04/Playa-del-Carmen-601751-smalltabletRetina-1024x576.jpg");
			Url = url.toString();
			buffer = ImageIO.read(url);
			Imagen = buffer;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		JLabel labelImagen = new JLabel(new ImageIcon(Imagen));
        labelImagen.setBounds(MARGIN_LEFT+130,MARGIN_TOP,buffer.getWidth(),buffer.getHeight());
        this.add(labelImagen);
        labelImagen.setVisible(true);
        addBtnText();
        addBtnAnalyze();
        Info.setBounds(600, 700, 150, 30);
        this.add(Info);
	}
	
	public void loadText(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
        	File file = fileChooser.getSelectedFile();
        	controller.analyzeText(file.getAbsolutePath());
        	Text = true;
        }
    }
	
	public void addBtnAnalyze(){
		btnAnalyze = new JButton("Analizar imagen");
		btnAnalyze.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
            	if(Text) {
            		analyze();
            		Info.setText("Analisis completado");
            	}
            	else
            		JOptionPane.showMessageDialog(null, "Agregue un texto antes de comenzar", "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        });
		btnAnalyze.setBounds(430, 700, 150, 30);
        this.add(btnAnalyze); 
    }
	
	public void analyze(){
		try {
			buffer = ImageIO.read(new URL(Url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.analyzeImage(Url);
		controller.generateRegions(buffer);
    }
	
	public void addBtnText(){
		btnLoadText = new JButton("Buscar Texto");
		btnLoadText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
            	loadText();
            }
        });
		btnLoadText.setBounds(10, 100, 120, 30);
        this.add(btnLoadText); 
    }
}
