package com.webagregator.webagregator.app.services;

import com.webagregator.webagregator.app.repositories.ProjectRepository;
import com.webagregator.webagregator.app.repositories.StudentRepository;
import com.webagregator.webagregator.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {
    /**
     * Репозиторий студента
     */
    private final StudentRepository studentRepository;

    /**
     * Конструктор сервиса.
     *
     * @param studentRepository Репозиторий студентов.
     */
    @Autowired
    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    /**
     * Получить информацию о студенте по его ID.
     *
     * @param id ID студента.
     * @return Объект студента или null, если студент не найден.
     */
    public Student getStudentById(Long id) {
        log.info("Getting student by ID: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * Создать нового студента.
     *
     * @param student Объект студента, который будет создан.
     */
    public void createStudent(Student student) {
        log.info("Creating a new student: {}", student);
        studentRepository.save(student);
    }

    /**
     * Обновить информацию о студенте.
     *
     * @param student Объект студента, который будет обновлен.
     */
    public void updateStudent(Student student) {
        log.info("Updating student: {}", student);
        studentRepository.save(student);
    }

    /**
     * Удалить студента по его ID.
     *
     * @param id ID студента, который будет удален.
     */
    public void deleteStudent(Long id) {
        log.info("Deleting student by ID: {}", id);
        studentRepository.deleteById(id);
    }

    /**
     * Получить количество доступных голосов у студента.
     *
     * @param studentId ID студента.
     * @return Количество доступных голосов или 0, если студент не найден.
     */
    public int getAvailableVoteCount(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return 0; // Если студент не найден, вернуть 0 доступных голосов
        }

        return student.getVoteCount();
    }
}
