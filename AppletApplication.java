package Application;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Algorithm.Shor;
import Utils.Util;

/**
 * Java applet to run a simulation of Shor's Quantum Factoring Algorithm.
 * 
 * @author dmitryrogozhin
 * @date August 2022
 */
public class AppletApplication implements WindowListener
{
	private static final String COPYRIGHT = "\u00a9 2022 Dmitry Rogozhin";
	
	private static final int X = 20;
	private static final int W_LABEL = 150;
	private static final int W_TEXT = 140;
	private static final int H = 30;
	
	private static JFrame w = new JFrame("d'Factor");

	private static JLabel nLabel = new JLabel("Enter number to factor:");
	private static JTextField nText = new JTextField("");
	private static JLabel resLabel = new JLabel("Prime factors:");
	private static JTextArea resText = new JTextArea();
	private static JLabel timeLabel1 = new JLabel("Time elapsed (ms):");
	private static JLabel timeLabel2 = new JLabel("");

	private static JButton factor = new JButton("Factor!");
	private static JButton clear = new JButton("Clear");

	private static JLabel turbo = new JLabel("Turbo");
	private static ImageIcon turboIconBin = new ImageIcon("Images/flash-1bin.png");
	private static ImageIcon turboIcon = new ImageIcon("Images/flash-1.png");

	private static boolean turboMode = false;
	
	public static void main(String[] args)
	{
		setupFields();
		setupFrame();
		doMagic();
	}
			
	private static void setupFields()
	{
		nLabel.setBounds(X, 35, W_LABEL, H);
		w.add(nLabel);
		
		nText.setBounds(X, 65, W_TEXT, H);
		w.add(nText);

		resLabel.setBounds(X, 155, W_LABEL, H);
		w.add(resLabel);

		resText.setBounds(X, 185, W_TEXT, 2 * H);
		resText.setLineWrap(true);
		resText.setWrapStyleWord(true);
		w.add(resText);

		timeLabel1.setBounds(X, 250, W_LABEL, H);
		w.add(timeLabel1);

		timeLabel2.setBounds(X, 270, W_LABEL, H);
		w.add(timeLabel2);
				
		factor.setBounds(X, 110, 100, 35);
		factor.setForeground(Color.BLUE);
		w.add(factor);
		
		turbo.setBounds(125, 110, 35, 35);
		turbo.setIcon(turboIconBin);
		w.add(turbo);
		
		clear.setBounds(40, 295, 100, 35);
		clear.setForeground(Color.BLUE);
		w.add(clear);
		
		JLabel c = new JLabel(COPYRIGHT);
		c.setBounds(10, 340, 160, H);
		w.add(c);
	}
	
	private static void setupFrame()
	{
		AppletApplication app = new AppletApplication();
		w.addWindowListener(app);
		w.setBackground(Color.LIGHT_GRAY);
		w.setFont(new Font("System", Font.PLAIN, 13));
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(185, 400);
		w.setLayout(null);
		w.setVisible(true);
	}
	
	private static void doMagic()
	{
		factor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				long start = System.currentTimeMillis();
				ArrayList<Integer> primes = processNumber(nText);
				long end = System.currentTimeMillis();
				timeLabel2.setText(String.valueOf(end - start));
				
				String pStr = Util.getPrimeStr(primes);
				resText.setText(pStr);
			}
		});

		clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				nText.setText("");
				timeLabel2.setText("");
				resText.setText("");
			}
		});
		
		turbo.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(turbo.getIcon().equals(turboIconBin))
				{
					turbo.setIcon(turboIcon);
					turboMode = true;
				}
				else if(turbo.getIcon().equals(turboIcon))
				{
					turbo.setIcon(turboIconBin);
					turboMode = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}
	
	private static ArrayList<Integer> processNumber(JTextField nText)
	{
		ArrayList<Integer> primesList = new ArrayList<Integer>();
		
		try
		{
			String numStr = nText.getText();
			int num = Integer.parseInt(numStr);
			
			if(num >= Integer.MIN_VALUE && num < 2)
			{
				nText.setText("Give me n > 2");
			}
			else
			{
				if(turboMode)
				{
					primesList = Shor.turboFactor(num); 
				}
				else
				{
					primesList = Shor.factor(num);
				}				
			}			
		}
		catch (Exception e)
		{
			System.out.println(e.getStackTrace());
			nText.setText("Gimme the number!");
		}
		
		return primesList;
	}
		
	public AppletApplication() {}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) { System.exit(0); }

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
