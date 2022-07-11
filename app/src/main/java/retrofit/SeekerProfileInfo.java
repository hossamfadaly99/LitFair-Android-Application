package retrofit;

public class SeekerProfileInfo {
    public SeekerProfileInfo() {
    }

    private int id;
    private String date_of_birth = null;
    private String fname;
    private String lname;
    private String nationality = null;
    private String country = null;
    private String city = null;
    private String gender = null;
    private String phone_number = null;
    private String title = null;
    private String email;

    public SeekerProfileInfo(String country, String city) {
        this.country = country;
        this.city = city;
    }

// Getter Methods

    public int getId() {
        return id;
    }
    public String getDate_of_birth() {
        return date_of_birth;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getNationality() {
        return nationality;
    }
    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }
    public String getGender() {
        return gender;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getTitle() {
        return title;
    }
    public String getEmail() {
        return email;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }
    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}