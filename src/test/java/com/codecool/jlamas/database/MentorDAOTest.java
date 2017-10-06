package com.codecool.jlamas.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.codecool.jlamas.models.account.Mentor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class MentorDAOTest {

    ResultSet rs = mock(ResultSet.class);

    private Mentor mentor;

    @Test
    void testGetMentor() throws SQLException {

        Mockito.when(rs.getString("login")).thenReturn("loginMentor");
        Mockito.when(rs.getString("email")).thenReturn("mailMentor");
        Mockito.when(rs.getString("name")).thenReturn("nameMentor");
        Mockito.when(rs.getString("surname")).thenReturn("surnameMentor");
        Mockito.when(rs.getString("password")).thenReturn("passwordMentor");
        Mockito.when(rs.getString("group_tag")).thenReturn("group_tag");

        MentorDAO mentorDAO = new MentorDAO();
        mentor = mentorDAO.getMentorFromResultSet(rs);

        assertNotNull(mentor);
        assertEquals("loginMentor", mentor.getLogin().getValue());
        assertEquals("mailMentor", mentor.getEmail().getValue());
        assertEquals("nameMentor", mentor.getName());
        assertEquals("surnameMentor", mentor.getSurname());
        assertEquals("passwordMentor", mentor.getPassword().getValue());
        assertEquals("group_tag", mentor.getGroup().getName());
    }
}


