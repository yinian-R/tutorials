package com.wymm.stream._1base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

/**
 * 收集器
 */
class _7CollectorTest {
    
    @Test
    void test() {
        List<Student> students = Arrays.asList(
                new Student("小明", 10, Student.Gender.MALE, Student.Grade.ONE),
                new Student("小绿", 12, Student.Gender.FEMALE, Student.Grade.TWO),
                new Student("小黄", 13, Student.Gender.FEMALE, Student.Grade.ONE),
                new Student("小紫", 12, Student.Gender.MALE, Student.Grade.THREE),
                new Student("小青", 21, Student.Gender.FEMALE, Student.Grade.FOUR),
                new Student("小灰", 22, Student.Gender.MALE, Student.Grade.FOUR),
                new Student("小黑", 12, Student.Gender.FEMALE, Student.Grade.ONE),
                new Student("小白", 15, Student.Gender.MALE, Student.Grade.FOUR),
                new Student("小红", 14, Student.Gender.FEMALE, Student.Grade.TWO)
        );
        
        // 得到所有学生的年龄列表
        Set<Integer> ages = students.stream().map(Student::getAge).collect(Collectors.toSet());
        System.out.println("所有学生的年龄：" + ages);
        
        // 统计汇总信息
        IntSummaryStatistics agesSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.println("年龄汇总信息：" + agesSummaryStatistics);
        
        // 分块
        Map<Boolean, List<Student>> genders = students.stream().collect(Collectors.partitioningBy(s -> s.getGender() == Student.Gender.MALE));
        MapUtils.verbosePrint(System.out, "男女生列表", genders);
        
        // 分组
        Map<Student.Grade, List<Student>> grades = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        MapUtils.verbosePrint(System.out, "学生班级列表", grades);
        
        // 得到所有班级学生的个数
        Map<Student.Grade, Long> gradesCount = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        MapUtils.verbosePrint(System.out, "班级学生个数列表", gradesCount);
        
    }
    
    @Data
    @AllArgsConstructor
    static class Student {
        private String name;
        private Integer age;
        private Gender gender;
        private Grade grade;
        
        
        enum Gender {
            MALE, FEMALE;
        }
        
        enum Grade {
            ONE, TWO, THREE, FOUR;
        }
    }
}
