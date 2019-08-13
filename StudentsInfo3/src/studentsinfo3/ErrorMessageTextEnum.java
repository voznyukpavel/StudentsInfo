package studentsinfo3;

public enum ErrorMessageTextEnum {
    
    ADDRESS_CONTAIN_FORBIDDEN_SYMBOLS("Address contains forbidden symbols"), 
    CONTAIN_FORBIDDEN_SIMBOLS        (" contains forbidden symbols"), 
    EMPTY_FIELD_ERROR_MESSAGE        ("field can`t be empty"), 
    GROUP_CANNOT_BE_ADDED            ("Group cannot be added"), 
    GROUP_IS_ALLREADY_EXIST          ("Group is allready exist"),
    INVALID                          ("Invalid "),
    INVALID_ADDRESS                  ("Invalid Address"), 
    ONLY_LATERS_ERROR_MESSAGE        (" must contain only laters."),
    RESULT_MUST_BE_INTEGER           ("Result must be an integer number 0-5."), 
    STUDENT_CANNOT_BE_ADDED          ("Student cannot be added"),
    STUDENT_IS_ALLREADY_EXIST        ( "Student is allready axist"),
    WRONG_RESULT                     ("Wrong result");

    private String message;

    ErrorMessageTextEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
