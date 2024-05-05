import java.util.*;

public class MatchingManager {

    private Queue<Student> nonMatching;
    private List<Student> impossibleMatching;
    
    private List<Student> matched;

    private DepartmentManager departmentManager;

    private int totalPreference = -1;

    public MatchingManager(List<Student> students, List<Department> departments) {
        impossibleMatching = new LinkedList<>();
        matched = new LinkedList<>();
        nonMatching = new LinkedList<>();
        nonMatching.addAll(students);
        departmentManager = new DepartmentManager(departments);
    }
    
    /**
     * 학생 - 학과 매칭을 수행하고, 매칭된 결과를 반환한다.
     * @param capacity 학과 정원
     * @return 학과에 매칭된 학생 리스트
     */
    public List<Student> matching() {
        if(totalPreference > -1) {
            return matched;
        }
        totalPreference = 0;
        while(!nonMatching.isEmpty()) {
            Student student = nonMatching.poll();
            int preferNumber = student.fetchPrefer();
            if(preferNumber < student.getApplyCount()) { 
                preferMatching(student, preferNumber);
            } else { // n지망까지 모두 떨어졌을 때, 랜덤 매칭
                randomMatching(student); 
            }
        } 
        return finishMatching();
    }

    /**
     * 선호도 점수 총합을 반환한다. matching()을 먼저 수행해야한다.
     * @return 선호도 점수 총합 (matching() 미 수행 시 -1을 반환)
     */
    public int getTotalPreference() {
        return totalPreference;
    }

    private void preferMatching(Student student, int preferNumber) {
        Department prefer = departmentManager.findByID(student.getPreferedDepartmentID(preferNumber));
        if(!prefer.apply(student, preferNumber)) {
            Student swapped = prefer.swap(student, preferNumber);
            nonMatching.add(swapped);
        }
    }

    private void randomMatching(Student student) {
        Department random = departmentManager.getAnything();
        if(random != null) { 
            random.apply(student, Student.MAX_APPLY);
        } else { // 모든 학과의 정원이 마감인 경우 (== 매칭이 불가능한 학생)
            impossibleMatching.add(student); 
        }
    }

    private List<Student> finishMatching() {
        matched.addAll(departmentManager.finishMatching());
        calculateTotalPreference();
        return matched;
    }

    private void calculateTotalPreference() {
        for(Student match : matched) {   
            for(int prefer = 0; prefer < match.getApplyCount(); prefer++) {
                String preferID = match.getPreferedDepartmentID(prefer);
                if(preferID.equals(match.getMatchedDepartment())) {
                    totalPreference += Student.MAX_APPLY - prefer;
                    break;
                }
            }
        }
    }
}
