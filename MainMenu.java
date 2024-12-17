package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Vac> vacs = new ArrayList<>();

    public static void main(String[] args) {
        try {
            int option;
            do {
                showMenu();
                option = getOptionFromUser();
                handleOption(option);
            } while (option != 0);
        } catch (Exception e) {
            System.err.println("Ошибка во время инициализации: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Менеджер пылесосов ---");
        System.out.println("1. Просмотреть пылесосы");
        System.out.println("2. Добавить новый пылесос");
        System.out.println("3. Редактировать пылесос");
        System.out.println("4. Удалить пылесос");
        System.out.println("5. Сохранить пылесосы в TXT");
        System.out.println("6. Загрузить пылесосы из TXT");
        System.out.println("7. Сохранить пылесосы в JSON");
        System.out.println("8. Загрузить пылесосы из JSON");
        System.out.println("9. Сохранить зашифрованные пылесосы в JSON");
        System.out.println("10. Загрузить зашифрованные пылесосы из JSON");//нот
        System.out.println("11. ZIP файл JSON с пылесосами");
        System.out.println("12. Отсортировать пылесосы по максимальной мощности");
        System.out.println("13. Сохранить пылесосы в XML");
        System.out.println("14. Загрузить пылесосы из XML");
        System.out.println("15. JAR файл JSON с пылесосами");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }
    private static int getOptionFromUser() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите другой номер.");
            }
        }
    }

    private static void handleOption(int option) {
        switch (option) {
            case 1:
                viewVacs();
                break;
            case 2:
                addNewVac();
                break;
            case 3:
                editVac();
                break;
            case 4:
                deleteVac();
                break;
            case 5:
                saveVacsToTxt();
                break;
            case 6:
                loadVacsFromTxt();
                break;
            case 7:
                saveVacsToJson();
                break;
            case 8:
                loadVacsFromJson();
                break;
            case 9:
                saveEncryptedVacsToJson();
                break;
            case 10:
                loadEncryptedVacsFromJson();
                break;
            case 11:
                zipVacsJsonFile();
                break;
            case 12:
                sortVacsByMaxPower();
                break;
            case 13:
                saveVacsToXml();
                break;
            case 14:
                loadVacsFromXml();
                break;
            case 15:
                jarVacsJsonFile();
                break;
            case 0:
                System.out.println("Выход из программы.");
                break;
            default:
                System.out.println("Неверный вариант. Пожалуйста, попробуйте еще раз.");
        }
    }

    private static void viewVacs() {
    if (vacs.isEmpty()) {
        System.out.println("Нет доступных пылесосов.");
    } else {
        vacs.iterator().forEachRemaining(System.out::println);
    }
}

private static void addNewVac() {
    System.out.println("Введите данные пылесоса:");
    System.out.print("Код: ");
    String code = scanner.nextLine();
    System.out.print("Бренд: ");
    String brand = scanner.nextLine();
    System.out.print("Максимальная мощность: ");
    int maxPower = Integer.parseInt(scanner.nextLine());
    System.out.print("Дата релиза: ");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date releaseDate;
    try {
        releaseDate = format.parse(scanner.nextLine());
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }
    System.out.print("Цена: ");
    double price = Double.parseDouble(scanner.nextLine());

    Vac vac = new Builders.RobotBuilder()
            .setId(code)
            .setModel(brand)
            .setPrice(price)
            .setMaxPower(maxPower)
            .setReleaseDate(releaseDate)
            .build();;
    vacs.add(vac);
    System.out.println("Добавлен пылесос в список.");
}

private static void editVac() {
    System.out.print("Введите индекс пылесоса для редактирования (0 - " + (vacs.size() - 1) + "): ");
    int index = Integer.parseInt(scanner.nextLine());

    if (index >= 0 && index < vacs.size()) {
        Vac vac = vacs.get(index);
        System.out.println("Редактирование пылесоса: " + vac);
        System.out.print("Введите новую максимальную мощность (текущая: " + vac.getMaxPower() + "): ");
        vac.setMaxPower(Integer.parseInt(scanner.nextLine()));
        System.out.print("Введите новую цену (текущая: " + vac.getPrice() + "): ");
        vac.setPrice(Double.parseDouble(scanner.nextLine()));
        System.out.println("Пылесос обновлен.");
    } else {
        System.out.println("Неверный индекс.");
    }
}

private static void deleteVac() {
    System.out.print("Введите индекс пылесоса для удаления (0 - " + (vacs.size() - 1) + "): ");
    int index = Integer.parseInt(scanner.nextLine());

    if (index >= 0 && index < vacs.size()) {
        vacs.remove(index);
        System.out.println("Пылесос удален.");
    } else {
        System.out.println("Неверный индекс.");
    }
}

private static void saveVacsToTxt() {
    FileHandler.saveVacsToTxt(vacs, "vacs.txt");
}

private static void loadVacsFromTxt() {
    FileHandler.loadVacsFromTxt(vacs, "vacs.txt");
    System.out.println("Пылесосы загружены из файла TXT.");
}

private static void saveVacsToJson() {
    FileHandler.saveVacsToJson(vacs, "vacs.json");
}

private static void loadVacsFromJson() {
    vacs = FileHandler.loadVacsFromJson("vacs.json");
    System.out.println("Пылесосы загружены из файла JSON.");
}

private static void saveVacsToXml() {
    FileHandler.saveVacsToXml(vacs, "vacs.xml");
}

private static void loadVacsFromXml() {
    vacs = FileHandler.loadVacsFromXml("vacs.xml");
    System.out.println("Пылесосы загружены из файла XML.");
}

private static void saveEncryptedVacsToJson() {
    FileHandler.saveEncryptedJson(vacs, "encrypted_vacs.json");
}

private static void loadEncryptedVacsFromJson() {
    vacs = FileHandler.loadEncryptedJson("encrypted_vacs.json");
    System.out.println("Зашифрованные пылесосы, загруженные из файла JSON.");
}

private static void zipVacsJsonFile() {
    FileHandler.zipFile("vacs.json", "vacs.zip");
}

private static void jarVacsJsonFile() {
    FileHandler.zipFile("vacs.json", "vacs.jar");
}

private static void sortVacsByMaxPower() {
    vacs.sort((v1, v2) -> Double.compare(v2.getMaxPower(), v1.getMaxPower()));
    System.out.println("Пылесосы сортируются по максимальной мощности.");
}
}