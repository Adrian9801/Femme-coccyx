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
	private JButton btnLoadText;
	private JButton btnAnalyze;
	private JButton btnAddImage;
	private BufferedImage buffer;
	private boolean Text;
	private boolean Image;
	private String Url;
	private JLabel Info;
	private JLabel LabelImagen;
	
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
		Text = false;
		Image = false;
		LabelImagen = new JLabel();
		Controller.getInstance();
		Info = new JLabel();
        addBtnText();
        addBtnAnalyze();
        addImage();
        Info.setBounds(600, 700, 150, 30);
        this.add(Info);
        this.add(LabelImagen);
        LabelImagen.setVisible(true);
	}
	
	public void loadText(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
        	File file = fileChooser.getSelectedFile();
        	Controller.getInstance().analyzeText(file.getAbsolutePath());
        	Text = true;
        }
    }
	
	public void addBtnAnalyze(){
		btnAnalyze = new JButton("Analizar imagen");
		btnAnalyze.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
            	if(Text && Image) {
            		analyze();
            		Info.setText("Analisis completado");
            	}
            	else
            		JOptionPane.showMessageDialog(null, "Agregue un texto e imagen antes de comenzar", "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        });
		btnAnalyze.setBounds(430, 700, 150, 30);
        this.add(btnAnalyze); 
    }
	
	public void addImage(){
		btnAddImage = new JButton("Insertar Imagen");
		btnAddImage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
            	addImageAnalyze();
            }
        });
		btnAddImage.setBounds(10, 140, 120, 35);
        this.add(btnAddImage); 
    }
	
	public void addImageAnalyze() {
		Url  = JOptionPane.showInputDialog("Escribe el link de la imagen");
		if(Url != null && Url.length() > 5) {
			if(Url.contains("https://www.dropbox.com/s"))
				Url = "https://www.dropbox.com/s/raw"+Url.substring(25, Url.length());
			BufferedImage buffer = null;
			Image Imagen = null;
			try {
				buffer = ImageIO.read(new URL(Url));
				Imagen = buffer;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LabelImagen.setIcon(new ImageIcon(Imagen));
	        LabelImagen.setBounds(MARGIN_LEFT+130,MARGIN_TOP,buffer.getWidth(),buffer.getHeight());
	        Image = true;
		}
	}
	
	public void analyze(){
		try {
			buffer = ImageIO.read(new URL(Url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller.getInstance().analyzeImage(Url);
		Controller.getInstance().generateRegions(buffer);
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
