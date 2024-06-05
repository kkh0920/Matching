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

    public static final int[] APPLICANTS = { 47, 42, 44, 45, 33, 21, 49, 17, 27, 35, 
                                            30, 28, 31, 36, 12, 20, 18, 29, 89, 100,
                                            30, 73, 39, 42, 63 }; // 각 학과 별 지원 현황   
         

    public static void main(String[] args) {
        List<Department> departments = new LinkedList<>();
        List<Student> students = new LinkedList<>();
        // for(int i = 0; i < 1000; i++) {
        //     departments.clear();
        //     students.clear();

        //     addDepartments(departments, DEPARTMENT);
        //     addStudents(students, departments, STUDENT);

        //     MatchingManager matcher = new MatchingManager(students, departments);
        //     List<Student> matchedStudent = matcher.matching(0.7, 1.3); // matching

        //     System.out.println(matcher.getTotalPreference());
        // }

        addDepartments(departments, DEPARTMENT);
        addStudents(students, departments, STUDENT);

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

    public static void addStudents(List<Student> students, List<Department> departments, int studentCount) {
        int remain = 0;      

        int[] applicants = new int[DEPARTMENT];
        for(int i = 0; i < DEPARTMENT; i++) {
            applicants[i] = APPLICANTS[i] * Student.MAX_APPLY; // 3회 지원
            if(applicants[i] > 0) {
                remain++;
            }
        }

        Random random = new Random();
        for(int i = 1; i <= studentCount; i++) {
            Student student = new Student(Integer.toString(i));

            // 랜덤 성적 설정 (0.0 ~ 4.5)
            double grade = random.nextInt(46) / 10.0; 
            student.setGrade(Math.round(grade * 100) / 100.0);
            
            // 정해진 비율 학과 지원
            int apply = remain > 3 ? 3 : remain;
            for(int j = 0; j < apply; j++) {
                int randomIndex;
                String preferDepartment;
                do {
                    randomIndex = random.nextInt(departments.size());
                    preferDepartment = departments.get(randomIndex).getId();
                } while(applicants[randomIndex] <= 0 ||
                            !student.addPreferred(preferDepartment)); // 학과 중복 등록 방지

                if((--applicants[randomIndex]) == 0) {
                    remain--;
                }
            }

            // 랜덤 학과 지원
            // for(int j = 0; j < Student.MAX_APPLY; j++) {
            //     int randomIndex;
            //     String preferDepartment;
            //     do {
            //         randomIndex = random.nextInt(departments.size());
            //         preferDepartment = departments.get(randomIndex).getId();
            //     } while(!student.addPreferred(preferDepartment));
            // }
            
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
