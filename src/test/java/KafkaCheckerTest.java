import model.info.AdditionalParameter;
import model.info.Address;
import model.info.Phone;
import model.info.UserInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KafkaCheckerTest {

    // 1. Тест на полное совпадение
    @Test
    public void testExactMatch() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь полностью совпадает
        UserInfo targetUser = new UserInfo(1, "John Doe", addresses, phones);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 2. Тест на частичное совпадение по имени
    @Test
    public void testPartialMatchByName() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "Jane Smith", null, null);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с частично заполненным именем
        UserInfo targetUser = new UserInfo(null, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 3. Тест на частичное совпадение по адресу
    @Test
    public void testPartialMatchByAddress() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "Jane Smith", null, null);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с частично заполненным адресом
        Address partialAddress = new Address(null, "New York", null);
        UserInfo targetUser = new UserInfo(null, null, Arrays.asList(partialAddress), null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 4. Тест на частичное совпадение по телефонам
    @Test
    public void testPartialMatchByPhones() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "Jane Smith", null, null);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с частично заполненными телефонами
        Phone partialPhone = new Phone("home", "7999999999", null);
        UserInfo targetUser = new UserInfo(null, null, null, Arrays.asList(partialPhone));

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 5. Тест на отсутствие совпадений
    @Test
    public void testNoMatch() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с несуществующим именем
        UserInfo targetUser = new UserInfo(null, "Unknown Person", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 6. Тест на частичное совпадение с null-значениями
    @Test
    public void testPartialMatchWithNullValues() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address(null, "Los Angeles", "90001"); // Адрес с null-улицей
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователя
        UserInfo user1 = new UserInfo(1, null, addresses, phones); // Пользователь с null-именем
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь также имеет null-значения
        UserInfo targetUser = new UserInfo(1, null, null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 7. Тест на пустой список пользователей
    @Test
    public void testEmptyUserList() {
        // Пустой список пользователей
        List<UserInfo> userInfoList = new ArrayList<>();

        // Искомый пользователь
        UserInfo targetUser = new UserInfo(1, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 8. Тест на null-список пользователей
    @Test
    public void testNullUserList() {
        // Null-список пользователей
        List<UserInfo> userInfoList = null;

        // Искомый пользователь
        UserInfo targetUser = new UserInfo(1, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 9. Тест на частичное совпадение с вложенными коллекциями
    @Test
    public void testPartialMatchWithNestedCollections() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        AdditionalParameter param1 = new AdditionalParameter("color", "black");
        AdditionalParameter param2 = new AdditionalParameter("brand", "Apple");
        List<AdditionalParameter> params1 = Arrays.asList(param1, param2);

        Phone phone1 = new Phone("home", "7999999999", params1);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с частично заполненными параметрами телефона
        AdditionalParameter partialParam = new AdditionalParameter("color", "black");
        Phone partialPhone = new Phone("home", null, Arrays.asList(partialParam));
        UserInfo targetUser = new UserInfo(null, null, null, Arrays.asList(partialPhone));

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 10. Тест на частичное совпадение с разными ID
    @Test
    public void testPartialMatchWithDifferentId() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с другим ID
        UserInfo targetUser = new UserInfo(3, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 11. Тест на частичное совпадение с разными именами
    @Test
    public void testPartialMatchWithDifferentName() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "Jane Smith", null, null);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с другим именем
        UserInfo targetUser = new UserInfo(null, "Unknown Person", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 12. Тест на частичное совпадение с разными адресами
    @Test
    public void testPartialMatchWithDifferentAddresses() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с другими адресами
        Address differentAddress = new Address("789 Oak St", "Chicago", "60601");
        UserInfo targetUser = new UserInfo(null, null, Arrays.asList(differentAddress), null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 13. Тест на частичное совпадение с разными телефонами
    @Test
    public void testPartialMatchWithDifferentPhones() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с другими телефонами
        Phone differentPhone = new Phone("mobile", "7111111111", null);
        UserInfo targetUser = new UserInfo(null, null, null, Arrays.asList(differentPhone));

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что совпадений нет
        assertTrue(matches.isEmpty());

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 0);
    }

    // 14. Тест на частичное совпадение с пустыми коллекциями
    @Test
    public void testPartialMatchWithEmptyCollections() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с пустыми коллекциями
        UserInfo targetUser = new UserInfo(null, "John Doe", new ArrayList<>(), new ArrayList<>());

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 15. Тест на частичное совпадение с null-коллекциями
    @Test
    public void testPartialMatchWithNullCollections() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с null-коллекциями
        UserInfo targetUser = new UserInfo(null, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }

    // 16. Тест на несколько совпадений
    @Test
    public void testMultipleMatches() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        UserInfo user2 = new UserInfo(2, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1, user2);

        // Искомый пользователь с частично заполненными полями
        UserInfo targetUser = new UserInfo(null, "John Doe", null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено два совпадения
        assertEquals(2, matches.size());
        assertTrue(matches.contains(user1));
        assertTrue(matches.contains(user2));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 2);
    }

    // 17. Тест на игнорирование null-полей
    @Test
    public void testIgnoreNullFields() {
        // Создаем адреса
        Address address1 = new Address("123 Main St", "New York", "10001");
        Address address2 = new Address("456 Elm St", "Los Angeles", "90001");
        List<Address> addresses = Arrays.asList(address1, address2);

        // Создаем телефоны
        Phone phone1 = new Phone("home", "7999999999", null);
        Phone phone2 = new Phone("work", "7000000000", new ArrayList<>());
        List<Phone> phones = Arrays.asList(phone1, phone2);

        // Создаем пользователей
        UserInfo user1 = new UserInfo(1, "John Doe", addresses, phones);
        List<UserInfo> userInfoList = Arrays.asList(user1);

        // Искомый пользователь с null-полем
        UserInfo targetUser = new UserInfo(1, null, null, null);

        // Фильтруем список
        List<UserInfo> matches = KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList);

        // Проверяем, что найдено ровно одно совпадение
        assertEquals(1, matches.size());
        assertTrue(matches.contains(user1));

        KafkaChecker.findMessagesByPartitialMessage(targetUser, userInfoList, 1);
    }
}
