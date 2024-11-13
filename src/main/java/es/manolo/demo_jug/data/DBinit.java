package es.manolo.demo_jug.data;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DBinit implements ApplicationRunner {
    private final ContactRepository contactRepository;

    public DBinit(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void run(ApplicationArguments args) {
        // create array of 20 names
        String[] names = {"Manolo", "Pepe", "Juan", "Luis", "Pedro", "Paco", "Antonio", "Jose", "Carlos", "Javier", "Raul", "Miguel", "Angel", "David", "Alberto", "Fernando", "Ricardo", "Sergio", "Jorge", "Ruben"};
        // create array of 20 surnames
        String[] surnames = {"Garcia", "Rodriguez", "Lopez", "Martinez", "Sanchez", "Perez", "Gonzalez", "Gomez", "Fernandez", "Moreno", "Jimenez", "Ruiz", "Hernandez", "Diaz", "Torres", "Alvarez", "Navarro", "Romero", "Vazquez", "Serrano"};
        // insert 100 contacts in repository
        for (int i = 0; i < 100; i++) {
            //Generate random email
            String email = names[i % 20] + "." + surnames[i % 20] + "@gmail.com";
            // Generate random birthday in the past
            LocalDate birthday = LocalDate.now().minusYears((long) (Math.random() * 50 + 18)).minusMonths((long) (Math.random() * 12)).minusDays((long) (Math.random() * 30));

            Contact contact = new Contact();
            contact.setName(names[i % 20]);
            contact.setSurname(surnames[i % 20]);
            contact.setPhone("555-555-555");
            contact.setEmail(email);
            contact.setBirthday(birthday);
            contact.setAddress("Calle " + names[i % 20] + " " + surnames[i % 20] + " " + i);
            contactRepository.save(contact);
        }
    }

}
