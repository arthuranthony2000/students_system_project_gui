package model.services;

import model.dao.DaoFactory;

public class ServiceFactory {
    public static StudentService createStudentService() {
		return new StudentService(DaoFactory.createStudentDao());
	}   
}
