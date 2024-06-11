# Matching Algorithm

학생 - 학과 매칭 알고리즘

- [Usage](#usage)
- [Methods](#methods)
- [Reference](#reference)

## Usage

#### Step 1
**`Student List`** 생성
```java
List<Student> studentList = new LinkedList<>();

for(int i = 0; i < /* Number of student */ ; i++) {
    Student student = new Student( /* student id */ );
    
    student.setGrade( /* grade */ );

    student.addPrefered( /* 1st Department */ );
    student.addPrefered( /* 2nd Department */ );
    student.addPrefered( /* 3rd Department */ );

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

## Methods

**`MatchingManager`** methods:

- **matching(min: double, max: double)**: 학과 정원의 최소 비율, 최대 비율에 맞게 매칭 수행

- **matching()**: 학과 정원에 맞게 매칭 수행

- **getTotalPreference()**: 매칭 후, 매칭된 학생들의 선호도 점수 총합을 반환


**`Student`** methods:

- **addPreferred(departmentId: String)**: 학생이 선호하는 학과를 등록 (**1지망, 2지망, 3지망 순으로 등록**해야함)

- **setGrade(grade: double)**: 학생의 성적을 설정

- **getMatchedDepartment()**: 매칭된 학과를 반환 (매칭되지 않았으면 null)

- **getPreference()**: 학생 개인의 선호도를 반환
  
   (1지망 매칭: 3점 / 2지망 매칭: 2점 / 3지망 매칭: 1점 / 그 외의 매칭 혹은 매칭되지 않은 상태 : 0점)


**`Department`** methods:

- **getApplicants()**: 매칭된 학생 수를 반환

## Reference

#### [안정 매칭 (Stable Matching)](https://gazelle-and-cs.tistory.com/111)
- Stable Matching Problem : Gale-Shapley Algorithm

#### [미국 의사 레지던트 매칭 제대로 알기 - NRMP알고리즘](https://www.youtube.com/watch?v=xm6921w9vXw)
- National Resident Matching Program

