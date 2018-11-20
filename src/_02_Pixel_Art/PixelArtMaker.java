package _02_Pixel_Art;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.PixelInterleavedSampleModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelArtMaker implements MouseListener, ActionListener{
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	JButton button;
	JPanel panel;
	ColorSelectionPanel csp;
	
	public void start() {
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		openOldGP();
		csp = new ColorSelectionPanel();
		button = new JButton();
		panel = new JPanel();
		button.setText("save");
		button.addActionListener(this);
		window.remove(gip);
		window.add(panel);
		panel.add(button);
		window.add(gp);
		window.add(csp);
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
	}
	

	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("hi");
		try {
			FileOutputStream fos = new FileOutputStream("src/_02_Pixel_Art/test.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
	
			oos.writeObject(gp.pixels);
			
			oos.close();
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	private void openOldGP() {
		
		
		try {
			
			FileInputStream fis = new FileInputStream("src/_02_Pixel_Art/test.obj");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Pixel[][] pixels = (Pixel[][]) ois.readObject();
			
			for(int i = 0; i < gp.getPixel().length; i++) {
				for(int j = 0; j < gp.getPixel()[i].length; j++) {
					
					gp.pixels[i][j].color = pixels[i][j].color;
					
				}
			}
			
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
