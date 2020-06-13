// 볼링 계산 알고리즘

class scoreCalc {
	int[] rolls = new int[21]; // 최소 20번, 최대 21번의 roll에 따른 pindown 개수를 저장할 배열 선언
	String[] scoreFrame = new String[21];
	String[] total = new String[10];

	int score = 0;
	int cursor = 0;
	int scpointer = 0;
	int upperbound = 0;
	int lowerbound = 0;

	// player의 수준을 gui를 통해 받고, 입력함
	void insertLevel(int level) {

		switch (level) { // 0은 초보, 1은 중수, 2는 고수
		case 0:
			upperbound = 6; // 1~6 사이의 pindown 만 하도록 함
			lowerbound = 1;
			break;
		case 1:
			upperbound = 9; // 9~6 사이의 pindown 만 하도록 함
			lowerbound = 6;
			break;
		case 2:
			upperbound = 10; // 8~10 사이의 pindown 만 하도록 함
			lowerbound = 8;
			break;
		}
		for (int i = 0; i < 20; i += 2) {
			// 총 21번을 치는데, 각각 던질 때 넘어진 roll의 개수를 랜덤으로 받음
			// (int)(Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);

			// 한 프레임에서의 첫번째 roll에서는 수준별로 정해진 범위내의 난수만큼 pin을 쓰러트림
			rolls[i] = (int) (Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
			// 다음 roll의 pindown은 0~10 사이의 난수로 입력
			rolls[i + 1] = (int) (Math.random() * 11);
			// 단, 두번째 pindown의 수가 10-첫번째 pindown의 보다 클 때에는 둘의 합이 10이 되도록 맞추어줌
			if (rolls[i + 1] > 10 - rolls[i]) {
				rolls[i + 1] = 10 - rolls[i];
			}

		}
		// 마지막 프레임에서의 특별한 경우에 대해서는 if문을 통해 pindown 수를 입력함
		if (rolls[18] == 10) { // 마지막 프레임의 첫번째의 roll에서 10개의 핀을 쓰러트린다면,
			rolls[19] = (int) (Math.random() * 11);
		}
		if (rolls[18] + rolls[19] >= 10) { // 마지막 프레임에서 처음 두번의 roll에서 쓰러트린 핀의 개수가 10이상이라면, 즉 스페어 또는 스트라이크였다면 마지막프레임에서
											// 세번째 까지 roll의 기회가 주어지도록 함
			rolls[20] = (int) (Math.random() * 11);
		}

	}

	// roll 한 최소20~최대21번의 pindown 개수가 입력되어있는 배열을 입력받아 프레임별 점수를 계산해주는 메소드
	void scoreCalc(int rolls[]) {
		for (int frame = 0; frame < 10; frame++) { // 프레임은 총 열번
			if (rolls[cursor] == 10 && frame < 9) {// strike의 경우, 그리고 9번째 프레임까지만 해당되는 계산
				if (rolls[cursor + 2] != 10) { // 연속 스트라이크가 아니라면
					score += 10 + rolls[cursor + 2] + rolls[cursor + 3]; // 보너스 점수로 스트라이크 다음의 두번의 roll의 점수가 더해진다
				} else if (rolls[cursor + 3] == 10) { // 9번째 프레임에서 연속3번 스트라이크가 되면 10번째 프레임에서의 룰으로 인하여 예외사항이 발생한다. 이 때에는
														// score에 30을 더해라
					score += 30;
				} else {
					score += 20 + rolls[cursor + 4]; // 연속 2회 스트라이크라면 스트라이크를 친 두개의 프레임20점 + 다음 프레임 첫 roll 점수를 합하라

				}
				scoreFrame[scpointer] = "X"; // 표시가 되는 scoreFrame 배열에는 strike를 X로 기록하게 함
				scoreFrame[scpointer + 1] = "-"; // strike 다음은 - 표시
				scpointer += 2;
				cursor += 2;

			} else if (rolls[cursor] == 10) { // 10번째 프레임에서 스트라이크인 경우에
				score += 10 + rolls[cursor + 1] + rolls[cursor + 2]; // 프레임에서 다음 두번을 치고, 그 두번의 점수를 합한다

				scoreFrame[scpointer] = "X";
				scoreFrame[scpointer + 1] = "-";
				scpointer += 2;

				cursor += 2;

			} else if (rolls[cursor] + rolls[cursor + 1] == 10) {// spare의 경우 (프레임의 첫번째 pin down 개수와 두번째 pin down 개수를
																	// 더했을 때 10이 된다면)
				score += 10 + rolls[cursor + 2];
				scoreFrame[scpointer] = Integer.toString(rolls[cursor]); // 첫번째 친 개수는 그대로 숫자로 점수판에 뜨도록 함
				scoreFrame[scpointer + 1] = "/"; // 스페어 처리한 두번째 roll의 경우에는 '/'로 점수판에 뜨도록 함
				scpointer += 2;
				cursor += 2;

			} else { // open 의 경우
				score += rolls[cursor] + rolls[cursor + 1]; // 첫번재, 두번째 pin down 개수를 프레임 점수에 더함
				scoreFrame[scpointer] = Integer.toString(rolls[cursor]); // 점수판에는 그대로 pin down 개수 입력
				scoreFrame[scpointer + 1] = Integer.toString(rolls[cursor + 1]); // 점수판에는 그대로 pin down 개수 입력
				scpointer += 2;
				cursor += 2;
			}
			total[frame] = Integer.toString(score); // 프레임별 누적 점수를 배열에 저장 (for 문이 프레임별로 돌 때마다 입력함으로서, 프레임별 누적 점수가 총 10번
													// 입력되게 됨 )

		}
		if (rolls[20] != 0) { // 10번째 프레임의 특별한 룰에 의해 21번째 roll의 값이 0이 아닐 때, 즉 21번째까지 roll 을 했을 때에는
			scoreFrame[20] = Integer.toString(rolls[20]); // 점수판에도 21번째 값을 넣어준다
		}

		for (int i = 0; i < scoreFrame.length; i++) {
			try {
				if (scoreFrame[i].equals("0")) { // 치지 못하여 pindown 개수가 0으로 되어있으면
					scoreFrame[i] = "-"; // 점수판에는 -으로 기록되도록 함
				}
			} catch (NullPointerException e) {
				System.out.print("null"); // 21번째 roll 이 없었을 때에는 null exception 처리를 해주도록 함
			}
		}

	}

}
