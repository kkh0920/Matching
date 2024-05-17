import java.util.*;

public class Student implements Comparable<Student> {

    public static final int MAX_APPLY = 3;

    private String studentID; // 학번

    private List<String> preferred; // 선호 학과 ID 목록 : 학과 별로 선호도가 다르다. (0, 1, 2, ... 지망)
    private List<String> nonPreferred; // 비 선호 학과 ID 목록

    private String matchedDepartment;

    private double grade = 0; // 성적

    private int matchingCounter = 0;

    public Student(String id) {
        preferred = new LinkedList<>();
        nonPreferred = new LinkedList<>();
        studentID = id;
        grade = 0;
        matchingCounter = 0;
        matchedDepartment = null;
    }

    /**
     * 선호 학과를 등록한다. 
     * 학생의 선호도가 높은 학과부터 등록해야한다.
     * @param departmentId 선호 학과 ID
     */
    public void addPreferred(String departmentId) {
        if(preferred.size() >= MAX_APPLY) {
            return;
        }
        preferred.add(departmentId);
    }
    
    /**
     * 비 선호 학과를 등록한다.
     * @param departmentId 비 선호 학과 ID
     */
    public void addNonPreferred(String departmentId) {
        nonPreferred.add(departmentId);
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public int fetchPrefer() {
        return matchingCounter < getApplyCount() ? matchingCounter++ : matchingCounter;
    }

    public void resetMatching() {
        matchingCounter = 0;
        matchedDepartment = null;
    }    

    public String getPreferedDepartmentID(int prefer) {
        return preferred.get(prefer);
    }

    public String getStudentID() {
        return studentID;
    }

    public void setMatchedDepartment(String matchedDepartment) {
        this.matchedDepartment = matchedDepartment;
    }

    public String getMatchedDepartment() {
        return matchedDepartment;
    }

    public int getApplyCount() {
        return preferred.size();
    }

    public int getPreference() {
        for(int prefer = 0; prefer < getApplyCount(); prefer++) {
            if(preferred.get(prefer).equals(matchedDepartment)) { 
                return Student.MAX_APPLY - prefer;
            }
        }
        return 0;
    }

    @Override
    public int compareTo(Student o) { // 학점이 높은 순으로 비교
        if(o.grade > this.grade)
            return 1;
        else if(o.grade == this.grade)
            return 0;
        return -1;
    }   
}
