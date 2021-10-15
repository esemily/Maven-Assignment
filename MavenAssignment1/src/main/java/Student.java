public class Student {

    String first_name;
    String gender;
    String email;
    String gpa;

    Student (String f, String g, String e, String p) {
        this.first_name = f;
        this.gender = g;
        this.email = e;
        this.gpa = p;
    }

    void PrintStudent(){
        System.out.println(first_name);
        System.out.println(gender);
        System.out.println(email);
        System.out.println(gpa);
    }

}
