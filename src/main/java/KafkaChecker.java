import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class KafkaChecker {

    /**
     * Возвращает список объектов, которые частично совпадают с искомым объектом.
     * Из минусов - отсутствует возможность явной проверки на нулл.
     *
     * @param target     Искомый объект (частично заполненный).
     * @param objectList Список объектов для фильтрации.
     * @param <T>        Тип объектов.
     * @param count      Количество совпадений.
     * @return Список объектов, которые совпадают по частично заполненным полям искомого объекта.
     */
    public static <T> List<T> findMessagesByPartitialMessage(T target, List<T> objectList, Integer count) {
        List<T> results = new ArrayList<>();
        Awaitility.await("Ожидание совпадений...")
                .pollInterval(200, MILLISECONDS)
                .timeout(1, SECONDS)
                .untilAsserted(() -> {
                    List<T> matches = findMessagesByPartitialMessage(target, objectList);
                    System.out.println("Найдено совпадений: " + matches.size());
                    Assertions.assertEquals(count, matches.size(), "Количество ожидаемых сообщение в топике не совпадает");
                    results.addAll(matches);
                });
        return results;
    }

    /**
     * Возвращает список объектов, которые частично совпадают с искомым объектом.
     * Из минусов - отсутствует возможность явной проверки на нулл.
     *
     * @param target     Искомый объект (частично заполненный).
     * @param objectList Список объектов для фильтрации.
     * @param <T>        Тип объектов.
     * @return Список объектов, которые совпадают по частично заполненным полям искомого объекта.
     */
    public static <T> List<T> findMessagesByPartitialMessage(T target, List<T> objectList) {
        if (target == null || objectList == null || objectList.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> filteredList = new ArrayList<>();

        for (T obj : objectList) {
            if (areObjectsPartiallyEqual(target, obj)) {
                filteredList.add(obj);
            }
        }

        return filteredList;
    }

    /**
     * Сравнивает два объекта по частично заполненным полям.
     *
     * @param partialObj Объект с частично заполненными полями.
     * @param fullObj    Объект для сравнения.
     * @return true, если все непустые поля partialObj совпадают с соответствующими полями fullObj; иначе false.
     */
    private static boolean areObjectsPartiallyEqual(Object partialObj, Object fullObj) {
        if (partialObj == null || fullObj == null) {
            return false;
        }

        Class<?> clazz = partialObj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object partialValue = field.get(partialObj);
                Object fullValue = field.get(fullObj);

                // Если поле в partialObj равно null, его игнорируем
                if (partialValue == null) {
                    continue;
                }

                // Если поле является коллекцией или массивом, сравниваем элементы
                if (partialValue instanceof List && fullValue instanceof List) {
                    if (!areListsPartiallyEqual((List<?>) partialValue, (List<?>) fullValue)) {
                        return false;
                    }
                }

                // Если поле является сложным объектом, рекурсивно проверяем его
                else if (isComplexObject(partialValue)) {
                    if (!areObjectsPartiallyEqual(partialValue, fullValue)) {
                        return false;
                    }
                }

                // Для простых типов используем equals()
                else if (!partialValue.equals(fullValue)) {
                    return false;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * Проверяет частичное равенство двух списков.
     *
     * @param partialList Частично заполненный список.
     * @param fullList    Полный список для сравнения.
     * @return true, если все непустые элементы partialList совпадают с соответствующими элементами fullList; иначе false.
     */
    private static boolean areListsPartiallyEqual(List<?> partialList, List<?> fullList) {
        if (partialList == null && fullList == null) {
            return true;
        }
        if (partialList == null || fullList == null || partialList.size() > fullList.size()) {
            return false;
        }

        // Теперь ищем каждый элемент partialList где угодно в fullList
        for (Object partialItem : partialList) {
            boolean matchFound = false;
            for (Object fullItem : fullList) {
                if (fullItem == null && partialItem == null) {
                    matchFound = true;
                    break;
                }
                if (fullItem == null || partialItem == null) continue;

                if (isComplexObject(partialItem)) {
                    if (areObjectsPartiallyEqual(partialItem, fullItem)) {
                        matchFound = true;
                        break;
                    }
                } else if (partialItem.equals(fullItem)) {
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) return false;
        }

        return true;
    }

    /**
     * Проверяет, является ли объект сложным (не примитивным типом или строкой).
     *
     * @param obj Объект для проверки.
     * @return true, если объект является сложным; иначе false.
     */
    private static boolean isComplexObject(Object obj) {
        return !(obj instanceof String || obj instanceof Number || obj instanceof Boolean || obj instanceof Character);
    }
}