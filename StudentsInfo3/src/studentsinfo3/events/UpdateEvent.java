package studentsinfo3.events;


import java.util.EventObject;
import studentsinfo3.model.Student;

@SuppressWarnings("serial")
public class UpdateEvent extends EventObject{
    
    private Student student;
 
    public UpdateEvent(Object source,Student student) {
        super(source);
        this.student=student;

    }

    public Student getStudent() {
        return student;
    }

}

