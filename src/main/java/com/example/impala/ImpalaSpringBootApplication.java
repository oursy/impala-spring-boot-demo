package com.example.impala;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
@Log4j2
public class ImpalaSpringBootApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImpalaSpringBootApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        int i = jdbcTemplate.update("insert into student values (cast(?as string),?,?)", "小明", 11, 22);

        List<Student> students = jdbcTemplate.query("select *from student", new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Student(resultSet.getString("name"), resultSet.getInt("age"), resultSet.getInt("contact"));
            }
        });

        students.forEach(
                student -> {
                    log.info("student info :{}", student.toString());
                }
        );

    }
}

@Data
@AllArgsConstructor
class Student {
    private String name;
    private Integer age;
    private Integer contact;
}
