## Matching Algorithm

- 학생 - 학과 매칭 알고리즘

## Usage

#### 학생 리스트 생성
```java
List<Student> studentList = new LinkedList<>();
for(int i = 0; i < /* Number of student */ ; i++) {
    Student student = new Student( /* student id */ );
    
    student.setGrade( /* grade */ );

    student.addPrefered( /* 1st Department */ );
    student.addPrefered( /* 2st Department */ );
    student.addPrefered( /* 3st Department */ );

    studentList.add(student);
}
```

#### 학과 리스트 생성
```java
List<Department> departmentList = new LinkedList<>();
for(int i = 0; i < /* Number of department */; i++) {
    Department department = new Department( /* deparment id */, /* capacity */ );

    departmentList.add(department);
}
```

#### MatchingManager 객체 생성
```java
MatchingManager matcher = new MatchingManager(studentList, departmentList);
```

#### 매칭 시작
```java
matcher.matching();
```

## Reference

### [안정 매칭 (Stable Matching)](https://gazelle-and-cs.tistory.com/111)
- Stable Matching Problem : Gale-Shapley Algorithm

### [미국 의사 레지던트 매칭 제대로 알기 - NRMP알고리즘](https://www.youtube.com/watch?v=xm6921w9vXw)
- National Resident Matching Program

