package com.codecool.jlamas.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.codecool.jlamas.models.account.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StudentDAOTest {

    ResultSet rs = mock(ResultSet.class);

    private Student student;

    @Test
    void getStudentFromResultSet() throws SQLException{

        Mockito.when(rs.getString("name")).thenReturn("StudentName");
        Mockito.when(rs.getString("surname")).thenReturn("StudentSurname");
        Mockito.when(rs.getString("login")).thenReturn("StudentLogin");
        Mockito.when(rs.getString("password")).thenReturn("StudentPassword");
        Mockito.when(rs.getString("email")).thenReturn("StudentMail");
        Mockito.when(rs.getString("group_tag")).thenReturn("StudentGroupTag");
        Mockito.when(rs.getString("team_tag")).thenReturn("StudentTeamTag");
        Mockito.when(rs.getString("team_tag")).thenReturn("StudentTeamTag");
        Mockito.when(rs.getInt("balance")).thenReturn(10);


        StudentDAO studentDAO = new StudentDAO();
        student = studentDAO.getStudentFromResultSet(rs);

        assertNotNull(student);

        assertEquals("StudentName", student.getName());
        assertEquals("StudentSurname", student.getSurname());
        assertEquals("StudentLogin", student.getLogin().getValue());
        assertEquals("StudentPassword", student.getPassword().getValue());
        assertEquals("StudentMail", student.getEmail().getValue());
        assertEquals("StudentGroupTag", student.getGroup().getName());
        assertTrue(student.getTeamId() instanceof Integer);
    }
}