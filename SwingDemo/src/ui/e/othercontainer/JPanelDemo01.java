package ui.e.othercontainer;

import javax.swing.JPanel ;
import javax.swing.JButton ;
import javax.swing.JLabel ;
import javax.swing.JFrame ;
import javax.swing.WindowConstants;

public class JPanelDemo01{
	
	public static void main(String args[]){
		
		JFrame frame = new JFrame("JPanel") ; // ʵ�����������
		JPanel pan = new JPanel() ;	// ׼������һ�����
		
		// �������е����ݶ����뵽��JPanel֮��
		pan.add(new JLabel("��ǩ-A")) ;
		pan.add(new JLabel("��ǩ-B")) ;
		pan.add(new JLabel("��ǩ-C")) ;
		pan.add(new JButton("��ť-X")) ;
		pan.add(new JButton("��ť-Y")) ;
		pan.add(new JButton("��ť-Z")) ;
		frame.add(pan) ;// �������뵽����֮��
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.pack() ;// ��������Զ�������С
		frame.setLocation(300,200) ;
		frame.setVisible(true) ;
		
	}
};