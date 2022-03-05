package model.dto;

import java.io.Serializable;

import model.entities.Student;

public class StudentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer number;
	private String name;
	private String course;
	private Float grade1;
	private Float grade2;
	private Float grade3;
	private Float grade4;
	private Float average;
	private String situation;

	public StudentDTO() {
	}

	public StudentDTO(Student student) {
		this.number = student.getNumber();
		this.name = student.getName();
		this.course = student.getCourse();
		this.grade1 = student.getNota1();
		this.grade2 = student.getNota2();
		this.grade3 = student.getNota3();
		this.grade4 = student.getNota3();
		this.average = (grade1 + grade2 + grade3 + grade4) / 4;
		this.situation = (average >= 7) ? "APROVADO" : (average < 3) ? "REPROVADO" : "RECUPERAÇÃO";
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Float getGrade1() {
		return grade1;
	}

	public void setGrade1(Float grade1) {
		this.grade1 = grade1;
	}

	public Float getGrade2() {
		return grade2;
	}

	public void setGrade2(Float grade2) {
		this.grade2 = grade2;
	}

	public Float getGrade3() {
		return grade3;
	}

	public void setGrade3(Float grade3) {
		this.grade3 = grade3;
	}

	public Float getGrade4() {
		return grade4;
	}

	public void setGrade4(Float grade4) {
		this.grade4 = grade4;
	}

	public Float getAverage() {
		return this.average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public static Student convertToStudent(StudentDTO student){
		return new Student(student.getNumber(), student.getName(), student.getCourse(), student.getGrade1(),
		student.getGrade2(), student.getGrade3(), student.getGrade4());
	}
}

