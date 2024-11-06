package es.manolo.demo_jug.data;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DbInit implements ApplicationRunner {
    private final ContactRepository contactRepository;

    public DbInit(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Generate 20 random names
        String[] names = {"Manolo", "Pepe", "Juan", "Pedro", "Luis", "Carlos", "Antonio", "Jose", "Javier", "Paco", "Raul", "Miguel", "Alberto", "David", "Jorge", "Fernando", "Sergio", "Ricardo", "Ruben", "Adrian"};
        // Now generate 20 random lastNames
        String[] lastNames = {"Garcia", "Rodriguez", "Lopez", "Martinez", "Sanchez", "Perez", "Gonzalez", "Gomez", "Fernandez", "Moreno", "Jimenez", "Ruiz", "Hernandez", "Diaz", "Torres", "Alvarez", "Navarro", "Romero", "Vazquez", "Serrano"};

        for (int i = 0; i < 20; i++) {
            LocalDate birthDay = LocalDate.now().minusDays((long) (Math.random() * 365 * 50));
            String phone = "+34 " + (int) (Math.random() * 1000000000);
            Contact contact = new Contact(names[i], "email" + i + "@gmail.com", phone, "Calle " + i, lastNames[i], birthDay);
            contactRepository.save(contact);
        }
    }
}
