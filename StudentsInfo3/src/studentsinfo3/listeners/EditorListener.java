package studentsinfo3.listeners;

import studentsinfo3.model.Student;

public interface EditorListener {
    void isDataDirty(boolean isDirty);
    void updateStudent(Student student);
}
