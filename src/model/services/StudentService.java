package model.services;

import java.util.List;

import model.dao.StudentDao;
import model.dao.impl.StudentDaoJDBC;
import model.dto.StudentDTO;
import model.entities.Student;

public class StudentService {

    private StudentDaoJDBC studentDaoJDBC;

    public StudentService(StudentDao studentDao){
        this.studentDaoJDBC = (StudentDaoJDBC) studentDao;
    }

    public void insert(Student obj) {
        this.studentDaoJDBC.insert(obj);
    }

    public void update(Student obj) {
        this.studentDaoJDBC.update(obj);
    }

    public void deleteById(Integer id) {
        this.studentDaoJDBC.deleteById(id);
    }

    public Student findById(Integer id) {
        return this.studentDaoJDBC.findById(id);
    }

    public List<StudentDTO> findAll(){
        List<Student> list = studentDaoJDBC.findAll();
        return list.stream().map(StudentDTO::new).toList();
    }
}
