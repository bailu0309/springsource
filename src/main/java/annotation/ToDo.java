package annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ToDo {
    public enum Priority {LOW, CENTER, HIGH}

    public enum Status {STARTED, NOT_START}

    String author() default "bailu";

    Priority PRIORITY() default Priority.CENTER;
}
