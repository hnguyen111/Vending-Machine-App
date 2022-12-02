package assignment_2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assignment_2.model.Model;
import assignment_2.model.User;
import assignment_2.model.ProductHandler;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private Model model;

    @BeforeEach
    public void init() throws IOException{
        try {
            this.model = new Model();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Check that initial size of users is only 3
    @Test
    void checkInitialSize(){
        int expected = 4;
        int actual = this.model.getAllUsers().size();
        assertEquals(expected, actual);
    }

    // Check that the first user added is "owner"
    @Test void checkOwnerAdded(){
        String expected = "owner";
        String actual = this.model.getAllUsers().get(0).getUserType();
        assertEquals(expected, actual);
    }

    // Check that the second user added is "cashier"
    @Test void checkCashierAdded(){
        String expected = "cashier";
        String actual = this.model.getAllUsers().get(1).getUserType();
        assertEquals(expected, actual);
    }

    // Check that the third user added is "seller"
    @Test void checkSellerAdded(){
        String expected = "seller";
        String actual = this.model.getAllUsers().get(2).getUserType();
        assertEquals(expected, actual);
    }

    // Check the function correctly adds a new user
    @Test void checkCorrectUserAdded() {
        String username = "user";
        String password = "user123";
        User expected = this.model.addUser(username, password);
        User actual = this.model.getAllUsers().get(4);
        assertEquals(expected, actual);

        String expectedUsername = "user";
        String actualUsername = this.model.getAllUsers().get(4).getUserName();
        assertEquals(expectedUsername, actualUsername);

        String expectedPassword = "user123";
        String actualPassword = this.model.getAllUsers().get(4).getPassword();
        assertEquals(expectedPassword, actualPassword);

    }

    // Check that function correctly sets new user as null
    @Test void checkNewUserNull(){
        String expected = null;
        this.model.setUser(null);
        assertNull(this.model.getCurrentUser());
    }

    // Check that function correctly sets new user as not null
    @Test void checkNewUserNotNull(){
        String expected = "newUser";
        this.model.setUser(new User("newUser", "newUser123", "customer"));
        String actual = this.model.getCurrentUser().getUserName();
        assertEquals(expected, actual);
    }

    // Check that currency handler is not null
    @Test void currencyHandlerNotNull(){
        ProductHandler actual = this.model.getProductHandler();
        assertNotNull(actual);
    }
  
  /* Check that product handler has been initialized */
    @Test
    public void productHandlerInit(){
        assertNotNull(this.model.getProductHandler());
    }

    /* Check that transaction handler has been initialized */
    @Test
    public void transactionHandlerInit(){
        assertNotNull(this.model.getTransactionHandler());
    }

    /* Check that init() retrieves categories */
    @Test
    public void retrieveCat(){
        assertNotEquals(0, this.model.getProductHandler().
                        getAllProductCategories().size());
    }

    /* Check that init() retrieves products */
    @Test
    public void retrieveProd(){
        assertNotEquals(0, this.model.getProductHandler().getAllProducts().size());
    }

    /* Check that getCashHandler() returns the cash handler */
    @Test
    public void getCashHandlerTest(){
        assertNotNull(this.model.getCashHandler());
    }  

    /* Check that timetracker is initialized */
    @Test
    public void initTimetracker(){
        assertNotNull(this.model.getTimeTracker());
    }

    /* Check that anonymous user is initialized */
    @Test
    public void initAnonUser(){
        String expected = "anonymous";
        String actual = this.model.getAnonymousUser().getUserName();
        assertEquals(expected, actual);
    }

    /* Check that a user can be removed from the system */
    @Test
    public void removedUserDisappeared(){
        int size = this.model.getAllUsers().size();
        this.model.removeUser("nouser");
        assertEquals(size, this.model.getAllUsers().size());

        boolean isExisted = false;
        for (User user : this.model.getAllUsers()){
            if (user.getUserName().equals("seller")){
                isExisted = true;
                break;
            }
        }
        assertTrue(isExisted);

        this.model.removeUser("seller");
        isExisted = false;
        for (User user : this.model.getAllUsers()){
            if (user.getUserName().equals("seller")){
                isExisted = true;
                break;
            }
        }	
        assertFalse(isExisted);
    }

    /* Check that the current user can be set to be an anonymous user */
    @Test
    public void setAnonymousUserTest(){
        this.model.setAnonymousUser();
        assertTrue(this.model.getAnonymousUser().getUserName().equals("anonymous"));
    }

    /* Check the returned report generator is not null */
    @Test
    public void getReportGeneratorTest(){
        assertNotNull(this.model.getReportGenerator());
    }
}
