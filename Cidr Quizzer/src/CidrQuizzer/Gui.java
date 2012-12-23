package CidrQuizzer;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Gui
{
	
	// fields made here because they can change by listeners
	private IpAddr ip;
	private JTextField question;
	private JButton showAnsBtn;
	private JTextField endRangeAns;
	private JTextField startRangeAns;
	private JTextArea notePad;
	// Show button also doubles as next Question button, this toggle helps determine 
	// whether the button shows answers or displays the next question.
	private boolean showMode = true;
	
	/* Run the Program */
	public static void main(String[] args)
	{
		Gui g = new Gui();
		g.go();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	
	/* GUI Layout including Button Action sub classes 
	 * Util;izes the IpAddr Class which contains all the info on the questions
	 * such as question formatted as a string (ip.getQuestion()) and start and end of net ranges
	 * (ip.StartOfNetRange(); ip.EndOfNetRange())
	 */
	
	public void go()
	{
		ip = new IpAddr();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel background = new JPanel();
		frame.getContentPane().add(background, BorderLayout.CENTER);
		background.setLayout(new BorderLayout(0, 0));
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		
		JPanel quesPanel = new JPanel();
		background.add(quesPanel, BorderLayout.NORTH);
		quesPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel QuesLabel = new JLabel("Find the net range for the following...");
		quesPanel.add(QuesLabel, BorderLayout.NORTH);
		
		question = new JTextField();
		quesPanel.add(question, BorderLayout.CENTER);
		question.setColumns(15);
		question.setText(ip.getQuestion());
		question.setEditable(false);
		
		showAnsBtn = new JButton("Show Answer");
		quesPanel.add(showAnsBtn, BorderLayout.SOUTH);
		ShowAnsListener s = new ShowAnsListener();
		showAnsBtn.addActionListener(s);
		
		JSeparator separator = new JSeparator();
		background.add(separator, BorderLayout.CENTER);
		
		JPanel ansPanel = new JPanel();
		background.add(ansPanel, BorderLayout.SOUTH);
		ansPanel.setLayout(new BoxLayout(ansPanel, BoxLayout.Y_AXIS));
		
		JPanel startRangePanel = new JPanel();
		ansPanel.add(startRangePanel);
		
		JLabel startRange = new JLabel("Start of Range...");
		startRangePanel.add(startRange);
		
		startRangeAns = new JTextField();
		startRangeAns.setColumns(10);
		startRangeAns.setEditable(false);
		startRangePanel.add(startRangeAns);
		
		JPanel endRangePanel = new JPanel();
		ansPanel.add(endRangePanel);
		
		JLabel endRange = new JLabel("End of Range...");
		endRangePanel.add(endRange);
		
		endRangeAns = new JTextField();
		endRangeAns.setColumns(10);
		endRangeAns.setEditable(false);
		endRangePanel.add(endRangeAns);
		
		JLabel gap = new JLabel(" ");
		ansPanel.add(gap);
		
		JLabel notePadLabel = new JLabel("Jot your answers here:");
		notePadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		ansPanel.add(notePadLabel);
		
		notePad = new JTextArea();
		notePad.setRows(3);
		ansPanel.add(notePad);
		
    	frame.setBounds(50,50,300,300);
    	frame.pack();
    	frame.setVisible(true);
			
	}
	

	
	public class ShowAnsListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			if (showMode == true) {
				startRangeAns.setText(ip.StartOfNetRange());
				endRangeAns.setText(ip.EndOfNetRange());
				showAnsBtn.setText("Next Question");
				showMode = false;
			}
			else {
				ip = new IpAddr();
				startRangeAns.setText("");
				endRangeAns.setText("");
				question.setText(ip.getQuestion());
				notePad.setText("");
				showAnsBtn.setText("Show Answer");
				showMode = true;
			}
		}
	}
}
