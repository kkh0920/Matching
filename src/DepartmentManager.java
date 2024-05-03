import java.util.*;

public class DepartmentManager {

	private List<Department> departments;

	private Random random;

	public DepartmentManager(List<Department> departments) {
		this.departments = new LinkedList<>();
		this.departments.addAll(departments);
		random = new Random();
	}
	
	/**
	 * ID 값에 맞는 학과를 검색해서 반환한다.
	 * @param id 원하는 학과 ID
	 * @return 검색 결과
	 */
	public Department findByID(String id) {
		for(Department department : departments) {
			if(department.getId().equals(id)) {
				return department;
			}
		}
		return null;
	}

	public Department getAnything() { 
		if(isFull())
			return null;
		
		// 비 선호 학과에 대한 고려가 필요함
		
		Department department;
		do {
			department = departments.get(random.nextInt(departments.size()));
		} while(department.isFull());

		return department;
	}

	/**
	 * 모든 학과의 정원이 마감되었는지에 대한 여부
	 * @return 마감되었으면 true, 아니면 false
	 */
	public boolean isFull() {
		for(Department department : departments) {
			if(!department.isFull()) {
				return false;
			}
		}
		return true;
	}

	public int finishMatching() {
		int score = 0;
		for(Department department : departments) {
			score += department.match();
			// System.out.println("학과" + department.getId() + " 인원 : " + department.getApplicants());
		}
		return score;
	}
}
