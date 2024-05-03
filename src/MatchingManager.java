import java.util.*;

public class MatchingManager {

    private Map<String, Student> matching;
    private Queue<Student> nonMatching;
    
    private DepartmentManager departmentManager;

    private int totalPreference = -1;

    public MatchingManager(List<Student> students, List<Department> departments) {
        nonMatching = new LinkedList<>();
        nonMatching.addAll(students);

        matching = new HashMap<>();
        departmentManager = new DepartmentManager(departments);
    }
    
    /**
     * 학생 - 학과 매칭을 수행하고, 매칭된 결과를 반환한다.
     * @param capacity 학과 정원
     * @return <학번(키), 학생(값)>으로 이루어진 매칭 결과
     */
    public List<Student> matching(int capacity) {
        totalPreference = 0;
        Department.capacity = capacity;
        while(!nonMatching.isEmpty()) {
            Student student = nonMatching.poll();
            int preferNumber = student.fetchPrefer();
            if(preferNumber == student.getApplyCount()) { // n지망까지 모두 떨어졌을 때?
                if(!randomMatching(student, preferNumber)) { 
                    return finishMatching(); // 랜덤 매칭이 실패하면(==모든 학과의 정원이 마감되면), 매칭을 끝낸다.
                }
                continue;
            }
            preferMatching(student, preferNumber);
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
        if(prefer.apply(student, preferNumber)) {
            matching.put(student.getStudentID(), student);
        } else {
            Student swapped = prefer.swap(student, preferNumber);
            nonMatching.add(swapped);
            if(!swapped.getStudentID().equals(student.getStudentID())) { // swap
                matching.remove(swapped.getStudentID());
                matching.put(student.getStudentID(), student);
            }
        }
    }

    private boolean randomMatching(Student student, int preferNumber) {
        Department random = departmentManager.getAnything();
        if(random == null) {
            return false; 
        }
        random.apply(student, preferNumber);
        matching.put(student.getStudentID(), student);
        return true;
    }

    private List<Student> finishMatching() {
        totalPreference = departmentManager.finishMatching();
        
        List<Student> students = new LinkedList<>();
        for(int i = 1; i <= matching.size(); i++) {
            Student matched = matching.get(Integer.toString(i));
            students.add(matched);
        }
        
        return students;
    }
}
