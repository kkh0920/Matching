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

    public static void main(String[] args) {
        List<Student> students = new LinkedList<>();
        initStudents(students);

        List<Department> departments = new LinkedList<>();
        initDepartments(departments);

        MatchingManager matcher = new MatchingManager(students, departments);

        Map<String, Student> matchedStudent = matcher.matching(40);
        List<Student> orderByGrade = new LinkedList<>();

        printOrderByGrade(matchedStudent, orderByGrade);
        printOrderByID(matchedStudent);
        
        System.out.println("매칭된 총 인원 : " + matchedStudent.size());
        System.out.println("선호도 점수 총합 : " + matcher.getTotalPreference());
    }

    public static void initStudents(List<Student> students) {
        Random random = new Random();
        for(int i = 1; i <= 1000; i++) {
            Student student = new Student(Integer.toString(i));
            double grade = random.nextInt(46) / 10.0;
            
            // 성적 설정
            student.setGrade(Math.round(grade * 100) / 100.0);

            // 1 ~ Student.MAX_APPLY 횟수 만큼 랜덤으로 지원
            int applyCnt = random.nextInt(Student.MAX_APPLY) + 1;
            for(int j = 0; j < applyCnt; j++) {
                String prefer = Integer.toString(random.nextInt(DepartmentManager.DEPARTMENT_NUMBER) + 1);
                student.addPreferred(prefer);
            }
            
            students.add(student);
        }
    }

    public static void initDepartments(List<Department> departments) {
        for(int i = 1; i <= DepartmentManager.DEPARTMENT_NUMBER; i++) {
            Department department = new Department(Integer.toString(i));
            departments.add(department);
        }
    }

    public static void printOrderByID(Map<String, Student> matchedStudent) {
        for(int i = 1; i <= 1000; i++) {
            Student student = matchedStudent.get(Integer.toString(i));
            if(student == null)
                continue;
            System.out.println("학생" + student.getStudentID() + 
                                " (성적 : " + student.getGrade() + 
                                " / 학과 : " + student.getMatchedDepartment() + ")");
        }
    }

    public static void printOrderByGrade(Map<String, Student> matchedStudent, List<Student> order) {
        for(int i = 1; i <= 1000; i++) {
            Student student = matchedStudent.get(Integer.toString(i));
            order.add(student);
        }
        Collections.sort(order);
        for(Student student : order) {
            System.out.println("학생" + student.getStudentID() + 
                                " (성적 : " + student.getGrade() + 
                                " / 학과 : " + student.getMatchedDepartment() + ")");
        }
    }
}
