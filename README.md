# Matching Algorithm

## Usage

#### Step 1
**`Student List`** 생성
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

#### Step 2
**`Department List`** 생성
```java
List<Department> departmentList = new LinkedList<>();
for(int i = 0; i < /* Number of department */; i++) {
    Department department = new Department( /* deparment id */, /* capacity */ );

    departmentList.add(department);
}
```

#### Step 3
**`MatchingManager`** 객체 생성
```java
MatchingManager matcher = new MatchingManager(studentList, departmentList);
```

#### Step 4
**`matching()`**
```java
matcher.matching();
// or
matcher.matching( /* Min capacity rate */, /* Max capacity rate */ );
```

## Reference

### [안정 매칭 (Stable Matching)](https://gazelle-and-cs.tistory.com/111)
- Stable Matching Problem : Gale-Shapley Algorithm

### [미국 의사 레지던트 매칭 제대로 알기 - NRMP알고리즘](https://www.youtube.com/watch?v=xm6921w9vXw)
- National Resident Matching Program

