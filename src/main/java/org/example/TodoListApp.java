package org.example;

import dao.TaskDAOImpl;
import dao.TaskDao;
import db.DatabaseConnection;
import model.Task;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TodoListApp {


    public static void main(String[] args) {
        TaskDao taskDAO = new TaskDAOImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Добавить задачу");
            System.out.println("2. Показать все задачи");
            System.out.println("3. Обновить задачу");
            System.out.println("4. Удалить задачу");
            System.out.println("5. Выйти");
            System.out.print("Выберите опцию: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // потребление новой строки

            try {
                switch (option) {
                    case 1:
                        // Добавление задачи
                        System.out.print("Введите название задачи: ");
                        String title = scanner.nextLine();
                        System.out.print("Введите описание задачи: ");
                        String description = scanner.nextLine();
                        Task newTask = new Task(0, title, description, false);
                        taskDAO.addTask(newTask);
                        System.out.println("Задача добавлена!");
                        break;

                    case 2:
                        // Показ всех задач
                        List<Task> tasks = taskDAO.getAllTasks();
                        if (tasks.isEmpty()) {
                            System.out.println("Список задач пуст.");
                        } else {
                            for (Task task : tasks) {
                                System.out.println(task);
                            }
                        }
                        break;

                    case 3:
                        // Обновление задачи
                        System.out.print("Введите ID задачи для обновления: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // потребление новой строки
                        Task taskToUpdate = taskDAO.getTask(updateId);
                        if (taskToUpdate == null) {
                            System.out.println("Задача с таким ID не найдена.");
                        } else {
                            System.out.print("Введите новое название задачи (оставьте пустым для сохранения текущего): ");
                            String newTitle = scanner.nextLine();
                            if (!newTitle.isEmpty()) {
                                taskToUpdate.setTitle(newTitle);
                            }

                            System.out.print("Введите новое описание задачи (оставьте пустым для сохранения текущего): ");
                            String newDescription = scanner.nextLine();
                            if (!newDescription.isEmpty()) {
                                taskToUpdate.setDescription(newDescription);
                            }

                            System.out.print("Задача выполнена? (true/false): ");
                            boolean isCompleted = scanner.nextBoolean();
                            taskToUpdate.setCompleted(isCompleted);

                            taskDAO.updateTask(taskToUpdate);
                            System.out.println("Задача обновлена!");
                        }
                        break;

                    case 4:
                        // Удаление задачи
                        System.out.print("Введите ID задачи для удаления: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // потребление новой строки
                        taskDAO.deleteTask(deleteId);
                        System.out.println("Задача удалена!");
                        break;

                    case 5:
                        // Выход из программы
                        System.out.println("Выход из программы...");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите опцию от 1 до 5.");
                        break;
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при выполнении операции: " + e.getMessage());
            }
        }
    }
}