package com.base;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Scraper {

	private JFrame frame;
	private ScraperLogic logic;
	private JTextField websiteURLTextField;
	private JTextField XMLFileNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scraper window = new Scraper();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Scraper() {
		initialize();
		logic = new ScraperLogic();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 586, 363);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		websiteURLTextField = new JTextField();
		websiteURLTextField.setBounds(143, 12, 410, 27);
		mainPanel.add(websiteURLTextField);
		websiteURLTextField.setColumns(10);
		
		JButton scrapeBtn = new JButton("Scrape");
		scrapeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.scrapeToFile(null);
			}
		});
		scrapeBtn.setBounds(414, 312, 129, 24);
		mainPanel.add(scrapeBtn);
		
		JLabel lblNewLabel = new JLabel("Product List URL:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 10, 129, 27);
		mainPanel.add(lblNewLabel);
		
		XMLFileNameTextField = new JTextField();
		XMLFileNameTextField.setBounds(143, 54, 410, 27);
		mainPanel.add(XMLFileNameTextField);
		XMLFileNameTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("XML File Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(20, 54, 113, 27);
		mainPanel.add(lblNewLabel_1);
	}
}
