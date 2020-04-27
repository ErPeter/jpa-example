package persons;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Main {

    Faker faker = new Faker(new Locale("hu"));

    private Person randomPerson(){

        java.util.Date date = new Date();
        java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Address address = new Address();
        address.setCountry(faker.address().country());
        address.setState(faker.address().state());
        address.setCity(faker.address().city());
        address.setStreetAddress(faker.address().streetAddress());
        address.setZip(faker.address().zipCode());
        Person person = new Person();

        person.setName(faker.name().fullName());
        person.setDob(localDate);
        person.setGender(faker.options().option(Person.Gender.FEMALE, Person.Gender.MALE));
        person.setAddress(address);
        person.setEmail(faker.internet().emailAddress());
        person.setProfession(faker.company().profession());

        return person;
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        Main main = new Main();

        for (int i = 0; i < 1000; i++) {

            em.getTransaction().begin();
            em.persist(main.randomPerson());
            em.getTransaction().commit();
        }


        em.close();
        emf.close();
    }
}