SELECT user.login, user.email, user.name, user.surname, login.login, login.password, mentor.class_tag
FROM user
     INNER JOIN login
             ON login.login = user.login
     INNER JOIN mentor
             ON mentor.login = user.login
WHERE user.type = 'Mentor';
