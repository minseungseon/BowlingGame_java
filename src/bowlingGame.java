
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class bowlingGame extends JFrame {
	// gui화면 가로 세로 크기 설정을 위한 변수 선언
	int f_width = 800;
	int f_height = 400;

	// level에 따른 변수 선언
	static int index1, index2;

	// 최종 결과를 비교할 때 쓰일 변수 선언
	static int scoreComp1, scoreComp2;
	static int flag1 = 1, flag2 = 1;
	static String playerName1, playerName2;

	// 선수 이름을 입력받는 textfiedl 선언
	JTextField tf1 = new JTextField(10);
	JTextField tf2 = new JTextField(10);

	// 선수 이름과 수준을 보여주는 라벨 선언
	JLabel player1 = new JLabel();
	JLabel player2 = new JLabel();
	JLabel player1level = new JLabel();
	JLabel player2level = new JLabel();

	// 콤보박스에 들어갈 데이터 array 와 두 선수에 대한 콤보박스 두개 선언
	String[] levels = { "초보", "중수", "고수" };
	JComboBox<String> levelCombo1 = new JComboBox<String>(levels);
	JComboBox<String> levelCombo2 = new JComboBox<String>(levels);

	// 게임 시작 버튼
	JButton start1 = new JButton("Player1 START");
	JButton start2 = new JButton("Player2 START");

	// 20혹은 21번의 roll의 pindown 숫자를 담을 배열 선언
	JLabel score1[] = new JLabel[21];
	// 한 프레임의 점수를 담을 배열 선언
	JLabel totalEach[] = new JLabel[10];
	// 두 플레이어의 최종 점수를 담을 라벨 선언
	JLabel total1 = new JLabel();
	JLabel total2 = new JLabel();
	// 두플레이어의 점수 비교 후 ~가 이겼습니다'를 보여줄 라벨 선언
	JLabel scoreComp = new JLabel();

	// 패널 생성
	private MyPanel panel = new MyPanel();

	public bowlingGame() {
		setComponents(); // 패널에 들어갈 요소들 add
		setFrame(); // 패널의 크기, 배경사진 등 설정
	}

	// 각 버튼과 라벨이 하게되는 함수들에 대한 메소드 선언

	// 1. 마지막 점수를 비교하는 메소드
	public void compareScore() {
		if (flag1 == 0 && flag2 == 0) { // 두 player 모두 게임을 종료했을 때 점수 비교 시작
			scoreComp = new JLabel();
			scoreComp.setBounds(480, 200, 600, 100);
			scoreComp.setFont(new Font("Arial", Font.BOLD, 30));
			if (scoreComp1 > scoreComp2) {
				scoreComp.setText(playerName1 + "님이 이겼습니다!!");

			} else if (scoreComp1 < scoreComp2) {
				scoreComp.setText(playerName2 + "님이 이겼습니다!!");

			} else {
				scoreComp.setText("비겼다!!");

			}
		}
		panel.add(scoreComp);
	}

	// 2. 첫번째 플레이어의 roll마다의 pindown 개수, frame 별 점수, 최종 점수를 label에 넣어주는 메소드
	public void putScore1(String arr[], String arr2[], int score) {
		scoreComp1 = score;
		for (int i = 0; i < 21; i++) {// roll마다의 pindown 개수를 배열로 Label 에 넣어줌 , 패널에 추가
			score1[i] = new JLabel(arr[i]);
			score1[i].setBounds(180 + i * 26, 43, 130, 100);
			score1[i].setFont(new Font("Arial", Font.BOLD, 20));
			panel.add(score1[i]);
			panel.revalidate();
			panel.repaint();
		}

		for (int j = 0; j < 10; j++) {// frame 별 점수를 배열로 label 에 넣어줌,패널에 추가
			totalEach[j] = new JLabel(arr2[j]);
			totalEach[j].setBounds(183 + j * 52, 70, 130, 100);
			totalEach[j].setFont(new Font("Arial", Font.BOLD, 20));
			panel.add(totalEach[j]);
			panel.revalidate();
			panel.repaint();
		}

		total1 = new JLabel(Integer.toString(score));// 최종 점수를 label에 넣어줌, 패널에 추가
		total1.setBounds(730, 90, 70, 30);
		total1.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(total1);

	}

	// 3. 첫번째 플레이어의 roll마다의 pindown 개수, frame 별 점수, 최종 점수를 label에 넣어주는 메소드
	public void putScore2(String arr[], String arr2[], int score) {
		scoreComp2 = score;
		for (int i = 0; i < 21; i++) { // roll마다의 pindown 개수를 배열로 Label 에 넣어줌 , 패널에 추가
			score1[i] = new JLabel(arr[i]);
			score1[i].setBounds(180 + i * 26, 113, 130, 100);
			score1[i].setFont(new Font("Arial", Font.BOLD, 20));
			panel.add(score1[i]);
			panel.revalidate();
			panel.repaint();
		}
		for (int j = 0; j < 10; j++) { // frame 별 점수를 배열로 label 에 넣어줌,패널에 추가
			totalEach[j] = new JLabel(arr2[j]);
			totalEach[j].setBounds(183 + j * 52, 140, 130, 100);
			totalEach[j].setFont(new Font("Arial", Font.BOLD, 20));
			panel.add(totalEach[j]);
			panel.revalidate();
			panel.repaint();
		}

		total2 = new JLabel(Integer.toString(score)); // 최종 점수를 label에 넣어줌, 패널에 추가
		total2.setBounds(730, 160, 70, 30);
		total2.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(total2);

	}

	// 패널에 들어가야하는 컴포넌트들을 추가해주고 위치 설정해주는 메소드
	public void setComponents() {
		add(panel);
		panel.setLayout(null);
		tf1.setBounds(10, 80, 80, 50);
		tf2.setBounds(10, 150, 80, 50);
		player1.setBounds(0, 180, 130, 100);
		player1level.setBounds(200, 180, 200, 100);
		player2.setBounds(0, 220, 200, 100);
		player2level.setBounds(200, 220, 200, 100);

		levelCombo1.setBounds(130, 220, 75, 25);
		levelCombo2.setBounds(130, 260, 75, 25);

		start1.setBounds(265, 215, 150, 35);
		start2.setBounds(265, 255, 150, 35);

		player(); // Listener들을 정리해줌
		panel.add(tf1);
		panel.add(tf2);

		panel.add(player1);

		panel.add(player2);

	}

	// 패널의 사이즈 설정
	public void setFrame() {
		setTitle("Bowling Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		setSize(f_width, f_height);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int f_x = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_y = (int) (screen.getHeight() / 2 - f_width / 2);

		setLocation(f_x, f_y);
		setResizable(false);
		setVisible(true);
	}

	// 컴포넌트들의 리스너 설정
	public void player() {
		// 1. tf1, tf2: 선수 이름을 입력했을 때
		tf1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				String input = t.getText();
				playerName1 = input;
				player1.setText("First player: " + input); // 입력된 선수 이름을 디스플레이 하도록 함
				tf1.setEnabled(false); // 한번 입력이 되었으면 더이상 입력할 수 없도록 disable
				panel.add(levelCombo1);
			}
		});
		tf2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				String input = t.getText();
				playerName2 = input;
				player2.setText("Second player: " + input);
				tf2.setEnabled(false);
				panel.add(levelCombo2);

			}
		});

		// 2. 선수별 수준 선택
		levelCombo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String r = cb.getSelectedItem().toString(); // 선택한 수준을 디스플레이 하도록 함
				index1 = cb.getSelectedIndex(); // Index1에 선택된 수준을 0,1,2로 저장함
				player1level.setText("수준은:" + r);// 수준 디스플레이

				panel.add(player1level);
				panel.add(start1);
				panel.revalidate();
				panel.repaint();

			}
		});
		levelCombo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String r = cb.getSelectedItem().toString();// 선택한 수준을 디스플레이 하도록 함
				index2 = cb.getSelectedIndex();// Index2에 선택된 수준을 0,1,2로 저장함
				player2level.setText("수준은:" + r);// 수준 디스플레이

				panel.add(player2level);
				panel.add(start2);
				panel.revalidate();
				panel.repaint();

			}
		});

		// 3. 선수 별 게임 시작 버튼
		start1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scoreCalc c = new scoreCalc(); // 볼링 점수 계산 클래스를 호출
				c.insertLevel(index1); // 플레이어의 수준 데이터를 입력
				c.scoreCalc(c.rolls); // 수준에 따라 만들어진 pindown 배열에 따라 점수 계산
				putScore1(c.scoreFrame, c.total, c.score); // 계산이 되어진 pindown 개수, 프레임별 누적점수, 최종 점수를 입력하여 gui에 출력되도록 함

				JButton source = (JButton) e.getSource(); // 게임 시작 버튼을 한번 누르면 비활성화 되도록 함
				source.setEnabled(false);
				flag1 = 0; // 게임을 한번 시작했고, 종료했음을 알리는 flag
				compareScore(); // 점수 비교 메소드 호출

			}
		});
		start2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scoreCalc c = new scoreCalc();
				c.insertLevel(index2);
				c.scoreCalc(c.rolls);
				putScore2(c.scoreFrame, c.total, c.score);

				c.insertLevel(index2);

				JButton source = (JButton) e.getSource();
				source.setEnabled(false);
				flag2 = 0;
				compareScore();

			}
		});
	}

	public static void main(String[] args) {
		new bowlingGame();
	}
}

//패널의 배경 이미지와 패널 자체의 크기 설정 
class MyPanel extends JPanel {
	private ImageIcon icon = new ImageIcon("score.png");
	private Image img = icon.getImage(); // 이미지 객체

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 30, 800, 180, this);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("BowlingGame<<이름을 먼저 입력해주세요>>", 130, 25);
		g.setFont(new Font("Arial", Font.BOLD, 30));
	}
}
