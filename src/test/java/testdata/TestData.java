package testdata;

public class TestData {  // константы тестовых данных
    // учётные данные
    public static final String USERNAME = "user1";
    public static final String PASSWORD = "password1";
    public static final String OTHER_PASSWORD = "password2";
    public static final String WRONG_PASSWORD = "wrongpassword";
    public static final String SHORT_USERNAME = "ab";
    public static final String SHORT_PASSWORD = "abc";
    public static final String NO_SUCH_USERNAME = "no_such_login_qa_42";

    // страница логина
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong login or password";
    public static final String NO_USERNAME_MESSAGE = "Login is required (minimum 3 characters)";
    public static final String NO_PASSWORD_MESSAGE = "Password is required (minimum 6 characters)";
    public static final String NO_CREDENTIALS_MESSAGE = "Login and password are required (minimum 3 and 6 characters)";
    public static final String SHORT_USERNAME_MESSAGE = "Login must be at least 3 characters";
    public static final String SHORT_PASSWORD_MESSAGE = "Password must be at least 6 characters";

    // страница регистрации
    public static final String USERNAME_TAKEN_MESSAGE = "Username already taken";
    public static final String PASSWORDS_MISMATCH_MESSAGE = "Passwords do not match";
}
