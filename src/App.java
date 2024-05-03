import java.util.*;

public class App {

    /*
     * ---------------------------------------------------
     * 
     *  <학생 - 학과 매칭 알고리즘>
     * 
     *  Stable Matching Problem : Gale-Shapley Algorithm
     * 
     * ---------------------------------------------------
     * 
     *  고민해야 할 것...
     * 
     *   1. 비 선호 학과도 매칭 시에 고려.
     * 
     *   2. 학과 정원이 고정이 아닌, 변동인 경우?
     *      -> 최소 70%, 최대 130%
     */

    public static final int STUDENT = 1000;

    public static final int DEPARTMENT = 25;
    
    public static final int DEPARTMENT_CAPACITY = 40;

    public static void main(String[] args) {
        List<Department> departments = new LinkedList<>();
        addDepartments(departments, DEPARTMENT);

        List<Student> students = new LinkedList<>();
        addRandomStudents(students, departments, STUDENT);

        MatchingManager matcher = new MatchingManager(students, departments);
        List<Student> matchedStudent = matcher.matching();

        printOrderByGrade(matchedStudent);
        
        System.out.println("매칭된 총 인원 : " + matchedStudent.size());
        System.out.println("선호도 점수 총합 : " + matcher.getTotalPreference());
    }

    public static void addRandomStudents(List<Student> students, List<Department> departments, int studentCount) {
        Random random = new Random();
        for(int i = 1; i <= studentCount; i++) {
            Student student = new Student(Integer.toString(i));
            double grade = random.nextInt(46) / 10.0; // 성적 : 0.0 ~ 4.5
            
            // 랜덤 성적 설정
            student.setGrade(Math.round(grade * 100) / 100.0);

            // 랜덤 학과, 랜덤 횟수 지원 (최대 Student.MAX_APPLY)
            int applyCnt = random.nextInt(Student.MAX_APPLY) + 1;
            for(int j = 0; j < applyCnt; j++) {
                int randomIndex = random.nextInt(departments.size());
                String preferDepartment = departments.get(randomIndex).getId();
                student.addPreferred(preferDepartment);
            }
            
            students.add(student);
        }
    }

    public static void addDepartments(List<Department> departments, int departmentCount) {
        for(int i = 1; i <= departmentCount; i++) {
            Department department = new Department(Integer.toString(i), DEPARTMENT_CAPACITY);
            departments.add(department);
        }
    }

    public static void printOrderByGrade(List<Student> matchedStudent) {
        Collections.sort(matchedStudent);
        for(Student student : matchedStudent) {
            System.out.println("학생" + student.getStudentID() + 
                                " (성적 : " + student.getGrade() + 
                                " / 학과 : " + student.getMatchedDepartment() + ")");
        }
    }
}
