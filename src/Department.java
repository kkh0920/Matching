import java.util.*;

public class Department {

    private String id;

    private int applicants = 0;

    private int capacity;

    private double maxCapacityRate = 1.0f;

    private List<Queue<Student>> applyQueues;

    public Department(String id, int capacity) {
        this.id = id;
        this.applicants = 0;
        this.capacity = capacity;
        this.maxCapacityRate = 1.0f;
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
        if(applicants >= capacity * maxCapacityRate) {
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
            Student swappedStudent = popStudent(i);
            if(swappedStudent != null) {
                applyQueue.add(student);
                applicants++;
                return swappedStudent;
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
     * 해당 학과에 지원한 학생을 "확정"하고 리스트로 만든다.
     * @return 매칭된 학생 리스트
     */
    public List<Student> match() {
        List<Student> matching = new LinkedList<>();
        for(Queue<Student> applyQueue : applyQueues) {
            while(!applyQueue.isEmpty()) {
                Student student = applyQueue.poll();
                student.setMatchedDepartment(id);
                matching.add(student);
            }
        }
        return matching;
    }

    /**
     * [prefer] 순위로 지원한 학생 중, 가장 낮은 학점을 가진 학생을 반환한다.
     * @param prefer 지원 순위
     * @return 반환된 학생
     */
    public Student popStudent(int prefer) {
        Queue<Student> preferQueue = applyQueues.get(prefer);
        if(preferQueue.isEmpty())
            return null;
        applicants--;
        return preferQueue.poll();
    }

    public boolean isFull() {
        return applicants >= capacity * maxCapacityRate;
    }

    public void setMaxCapacityRate(double maxCapacityRate) {
        this.maxCapacityRate = maxCapacityRate;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    public int getApplicants() {
        return applicants;
    }
}
