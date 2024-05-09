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

    public void setMaxCapacityRate(double rate) {
        for(Department department : departments) {
            department.setMaxCapacityRate(rate);
        }
    }

    public Department getAnything() { 
        if(isAllFull())
            return null;
        
        // 비 선호 학과에 대한 고려가 필요함
        
        Department department;
        do {
            department = departments.get(random.nextInt(departments.size()));
        } while(department.isFull());

        return department;
    }

    public List<Department> getUpperCapacity(double min) {
        List<Department> upper = new LinkedList<>();
        for(Department department : departments) {
            if(department.getApplicants() > department.getCapacity() * min) {
                upper.add(department);
            }
        }
        return upper;
    }
    
    public List<Department> getLowerCapacity(double min) {
        List<Department> lower = new LinkedList<>();
        for(Department department : departments) {
            if(department.getApplicants() < department.getCapacity() * min) {
                lower.add(department);
            }
        }
        return lower;
    }

    /**
     * 모든 학과의 정원이 마감되었는지에 대한 여부
     * @return 마감되었으면 true, 아니면 false
     */
    public boolean isAllFull() {
        for(Department department : departments) {
            if(!department.isFull()) {
                return false;
            }
        }
        return true;
    }

    public List<Student> finishMatching() {
        List<Student> matching = new LinkedList<>();
        for(Department department : departments) {
            matching.addAll(department.match());
        }
        print();
        return matching;
    }

    public void print() {
        for(Department department : departments) {
            System.out.println("학과" + department.getId() + 
                                " 인원 : " + department.getApplicants() + 
                                    " / " + department.getCapacity());
        }
    }
}
