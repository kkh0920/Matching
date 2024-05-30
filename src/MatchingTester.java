import java.util.*;

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

public class MatchingTester {

    public static final int STUDENT = 1000;

    public static final int DEPARTMENT = 25;
    
    public static final int DEPARTMENT_CAPACITY = 40;

    public static final int[] APPLICANTS = { 45, 28, 35, 46, 39, 20, 63, 20, 37, 20, 
                                            12, 31, 26, 36, 14, 25, 23, 37, 145, 103,
                                            33, 48, 33, 31, 50 }; // 각 학과 별 지원 현황                     

    public static void main(String[] args) {
        List<Department> departments = new LinkedList<>();
        List<Student> students = new LinkedList<>();
        
        addDepartments(departments, DEPARTMENT);
        addRandomStudents(students, departments, STUDENT);

        MatchingManager matcher = new MatchingManager(students, departments);
        List<Student> matchedStudent = matcher.matching(0.7, 1.3); // matching

        print(matcher, matchedStudent, departments);
    }

    public static void print(MatchingManager matcher, List<Student> matchedStudent, List<Department> departments) {
        System.out.print("\n\n-------------------  Matching Result  -------------------\n\n");
        int i = 0;
        for(Department department : departments) {
            System.out.printf("%10s", "학과");
            System.out.printf("%2s", department.getId());
            System.out.printf("%10s", "(" + department.getApplicants() + " / " + department.getCapacity() + ")");
            i++;
            if(i % 2 == 0) {
                System.out.println();
            }
        }
        System.out.print("\n\n            매칭된 총 인원");
        System.out.printf("%16s", " : " + matchedStudent.size() + " / " + STUDENT);
        System.out.print("\n            선호도 점수 총합" );
        System.out.printf("%13s", " : " + matcher.getTotalPreference() + " / " + Student.MAX_APPLY * STUDENT);
        System.out.println("\n\n---------------------------------------------------------\n\n");

    }

    public static void addRandomStudents(List<Student> students, List<Department> departments, int studentCount) {
        int remain = DEPARTMENT;      
        for(int i = 0; i < DEPARTMENT; i++) {
            APPLICANTS[i] *= Student.MAX_APPLY; // 3회 지원
        }
        Random random = new Random();
        for(int i = 1; i <= studentCount; i++) {
            Student student = new Student(Integer.toString(i));

            // 랜덤 성적 설정 (0.0 ~ 4.5)
            double grade = random.nextInt(46) / 10.0; 
            student.setGrade(Math.round(grade * 100) / 100.0);
            
            // 랜덤 학과 지원
            int apply = remain > 3 ? 3 : remain;
            for(int j = 0; j < apply; j++) {
                int randomIndex;
                String preferDepartment;
                do {
                    randomIndex = random.nextInt(departments.size());
                    preferDepartment = departments.get(randomIndex).getId();
                } while(APPLICANTS[randomIndex] <= 0 ||
                            !student.addPreferred(preferDepartment)); // 학과 중복 등록 방지

                if((--APPLICANTS[randomIndex]) == 0) {
                    remain--;
                }
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
}
