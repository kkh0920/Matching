import java.util.*;

public class Department {

	public static int capacity = 40;

	private String id;

	private int applicants = 0;

	private List<Queue<Student>> applyQueues;

	public Department(String id) {
		this.id = id;
		applyQueues = new LinkedList<>();
		for(int i = 0; i < Student.MAX_APPLY + 1; i++) { // [n지망 지원 큐] + [n지망 모두 떨어진 학생 지원 큐]
			applyQueues.add(new PriorityQueue<>());
		}
	}

	/**
	 * 학생이 학과에 지원한다. 
	 * @param student 지원한 학생
	 * @param preferNumber n지망
	 * @return 정원이 모두 채워졌으면 false, 아니면 true 반환
	 */
	public boolean apply(Student student, int preferNumber) {
		if(applicants == capacity) {
			return false;
		}
		applyQueues.get(preferNumber).add(student);
		applicants++;
		return true;
	}

	/**
	 * 지원한 학생과 기존 학생을 비교하고 교체한다.
	 * @param student 지원한 학생
	 * @param preferNumber n지망
	 * @return 교체된 학생 반환 (교체 불가능하면 지원한 학생 그대로 반환)
	 */
	public Student swap(Student student, int preferNumber) {
		Queue<Student> applyQueue = applyQueues.get(preferNumber);
		// 1. 낮은 지망으로 지원한 학생과 교체
		for(int i = Student.MAX_APPLY; i > preferNumber; i--) {
			Queue<Student> queue = applyQueues.get(i);
			if(!queue.isEmpty()) {
				applyQueue.add(student);
				return queue.poll();
			}
		}

		// 2. 낮은 지망이 없다면, 같은 지망으로 지원한 학생 중 성적 비교 후 교체
		if(applyQueue.isEmpty()) {
			return student;
		}
	
		Student swap = applyQueue.peek();
		if(swap.getGrade() < student.getGrade()) { // 성적이 같은 경우는 어떻게 해야할까?
			applyQueue.add(student);
			return applyQueue.poll();
		}
		// 3. 성적도 낮으면, 교체되지 않고 반환
		return student;
	}

	/**
	 * 해당 학과에 지원한 학생을 "확정"한다. 그리고 선호도 점수를 계산하여 반환한다.
	 * @return 선호도 점수
	 */
	public int match() {
		int score = 0;
		for(Queue<Student> applyQueue : applyQueues) {
			while(!applyQueue.isEmpty()) {
				// 1. 매칭
				Student student = applyQueue.poll();
				student.setMatchedDepartment(id);
				// 2. 선호도 계산
				for(int prefer = 0; prefer < student.getApplyCount(); prefer++) {
					String preferID = student.getPreferedDepartmentID(prefer);
					if(preferID.equals(id)) {
						score += Student.MAX_APPLY - prefer;
						break;
					}
				}
			}
		}
		return score;
	}

	public boolean isFull() {
		return applicants >= capacity;
	}

	public static int getCapacity() {
		return capacity;
	}

	public String getId() {
		return id;
	}

	public int getApplicants() {
		return applicants;
	}
}
