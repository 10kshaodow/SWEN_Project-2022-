package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Address;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.CreditCard;
import com.estore.api.estoreapi.model.OrderItem;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserFileDAOTest {

  @BeforeAll
  static public void setup() throws Exception {
    IUserDAO dao = new UserFileDAO("data/testUsers.json", new ObjectMapper());
    for (User u : dao.getAllUsers()) {
      dao.deleteUser(u.getUsername());
    }
  }

  private IUserDAO createDao() throws Exception {
    return new UserFileDAO("data/testUsers.json", new ObjectMapper());
  }

  private CreditCard createTestCard(int id) {
    return new CreditCard("testCard" + id, "testCard" + id, "testCard" + id, "testCard" + id);
  }

  private Address createAddress(int id) {
    return new Address("testAddy" + id, "testAddy" + id, "testAddy" + id, "testAddy" + id, "testAddy" + id);
  }

  private Cart createCart(ArrayList<OrderItem> orders) {
    return new Cart(orders);
  }

  private User createUser(String name, CreditCard card, UserType type, Address addy, Cart cart) {
    return new User(name, name, card, type, addy, new ArrayList<>(), cart, new ArrayList<>(),
        new ArrayList<>());
  }

  @Test
  public void testCreateUser() throws Exception {
    IUserDAO dao = createDao();
    CreditCard card = createTestCard(0);
    Address addy = createAddress(0);
    Cart cart = createCart(new ArrayList<>());
    User expected = createUser("test", card, UserType.Admin, addy, cart);

    User actual = dao.createUser(expected);

    assertEquals(expected, actual);

    try {
      dao.createUser(expected);

      assert (false);
    } catch (Exception e) {
      assert (true);
    }
  }

  @Test
  public void testUpdateUser() throws Exception {
    IUserDAO dao = createDao();
    CreditCard card = createTestCard(0);
    CreditCard card2 = createTestCard(1);
    Address addy = createAddress(0);
    Cart cart = createCart(new ArrayList<>());
    User expected = createUser("gang", card, UserType.Admin, addy, cart);

    User actual = dao.createUser(expected);

    expected.setCreditCard(card2);

    actual = dao.updateUser(expected);

    assertEquals(expected, actual);

    User failure = createUser("butch", card, UserType.Admin, addy, cart);

    assertNull(dao.updateUser(failure));

  }

  @Test
  public void testDeleteUserFails() throws Exception {
    IUserDAO dao = createDao();
    CreditCard card = createTestCard(0);
    Address addy = createAddress(0);
    Cart cart = createCart(new ArrayList<>());
    User expected = createUser("admin", card, UserType.Admin, addy, cart);

    try {

      dao.deleteUser(expected.getUsername());

      assert (false);

    } catch (Exception e) {
      assert (true);
    }

    assertEquals(false, dao.deleteUser("different"));

  }

  @Test
  public void testGetUser() throws Exception {
    IUserDAO dao = createDao();
    CreditCard card = createTestCard(0);
    Address addy = createAddress(0);
    Cart cart = createCart(new ArrayList<>());
    User expected = createUser("butch2", card, UserType.Admin, addy, cart);

    User actual = dao.createUser(expected);
    actual = dao.getUser(expected.getUsername());

    assertEquals(expected, actual);

    assertNull(dao.getUser("Curios George"));

  }

  @Test
  public void testAddNotification() throws Exception {
    IUserDAO dao = createDao();
    CreditCard card = createTestCard(0);
    Address addy = createAddress(0);
    Cart cart = createCart(new ArrayList<>());
    User expected = createUser("butch3", card, UserType.Admin, addy, cart);
    ArrayList<Integer> listExpected = new ArrayList<>();
    listExpected.add(0);

    User actual = dao.createUser(expected);
    actual = dao.addNotificationToUser(expected.getUsername(), 0);

    assertEquals(listExpected.toString(), actual.getNotifications().toString());

    assertNull(dao.addNotificationToUser("goose", 0));
  }

  // UserFileDAO userFileDao;
  // UserFileDAO mockUserFileDao;
  // User existingUser;
  // User testUser;

  // @BeforeEach
  // void setup() throws IOException {
  // this.userFileDao = new UserFileDAO("data/testUsers.json", new
  // ObjectMapper());
  // this.mockUserFileDao = Mockito.mock(UserFileDAO.class);
  // CreditCard creditCard = new CreditCard("testnum", "testeyr", "testem",
  // "testcvv");
  // Address address = new Address("teststreet", "testcity", "teststate",
  // "testcountry", "testzip");
  // Cart cart = new Cart(new ArrayList<OrderItem>());
  // existingUser = new User("admin", "Admin", creditCard, null, address, new
  // ArrayList<String>(), cart,
  // new ArrayList<Integer>(), new ArrayList<Integer>());
  // testUser = new User("new_user", "Admin", creditCard, null, address, new
  // ArrayList<String>(), cart,
  // new ArrayList<Integer>(), new ArrayList<Integer>());
  // }

  // @Test
  // void testCreateDeleteUser() {
  // try {
  // userFileDao.createUser(existingUser);
  // assert (false);
  // } catch (UserExistsException e) {
  // assert (true);
  // } catch (IOException e) {
  // assert (false);
  // }

  // try {
  // User user = userFileDao.createUser(testUser);
  // assertEquals(testUser, user);
  // } catch (UserExistsException e) {
  // assert (false);
  // } catch (IOException e) {
  // assert (false);
  // }

  // try {
  // assertFalse(userFileDao.deleteUser("DoesntExist"));
  // } catch (IOException e) {
  // assert (false);
  // } catch (DeleteAdminException e) {
  // assert (false);
  // }

  // try {
  // assert (userFileDao.deleteUser(testUser.username));
  // } catch (IOException e) {
  // assert (false);
  // } catch (DeleteAdminException e) {
  // assert (false);
  // }

  // try {
  // userFileDao.deleteUser("admin");
  // } catch (IOException e) {
  // assert (false);
  // } catch (DeleteAdminException e) {
  // assert (true);
  // }

  // }

  // @Test
  // void testGetAllUsers() {
  // try {
  // assert (userFileDao.getAllUsers() != null);
  // } catch (IOException e) {
  // assert (false);
  // }
  // }

  // @Test
  // void testGetUser() throws IOException {
  // assertEquals(existingUser, userFileDao.getUser(existingUser.username));
  // assert (userFileDao.getUser("not_exist") == null);
  // }

  // @Test
  // void testUpdateUser() {
  // try {
  // assertEquals(userFileDao.updateUser(existingUser), existingUser);
  // } catch (IOException e) {
  // assert (false);
  // }

  // try {
  // assert (userFileDao.updateUser(testUser) == null);
  // } catch (IOException e) {
  // assert (false);
  // }
  // }

  // @Test
  // void testUserExists() throws IOException {
  // assert (userFileDao.userExists(existingUser.username));
  // assertFalse(userFileDao.userExists("doesnt_exist"));
  // }

}
