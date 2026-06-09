package com.riccardopoppi.catalogo_cap.services;

import com.riccardopoppi.catalogo_cap.domain.Cappello;
import com.riccardopoppi.catalogo_cap.repositories.CappelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CappelloRepository cappelloRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cappelloRepository.count() == 0) {
            
            Cappello c1 = new Cappello();
            c1.setCodice("CAP001");
            c1.setNome("Snapback Classic");
            c1.setMarca("New Era");
            c1.setTaglia("M");
            c1.setAnno(2023);
            c1.setPrezzo(29.99);
            c1.setImmagine("snapback.jpg");
            cappelloRepository.save(c1);

            Cappello c2 = new Cappello();
            c2.setCodice("CAP002");
            c2.setNome("Beanie Winter");
            c2.setMarca("Carhartt");
            c2.setTaglia("Unica");
            c2.setAnno(2022);
            c2.setPrezzo(19.99);
            c2.setImmagine("beanie.jpg");
            cappelloRepository.save(c2);

            Cappello c3 = new Cappello();
            c3.setCodice("CAP003");
            c3.setNome("Fedora Elegance");
            c3.setMarca("Borsalino");
            c3.setTaglia("L");
            c3.setAnno(2021);
            c3.setPrezzo(150.00);
            c3.setImmagine("fedora.jpg");
            cappelloRepository.save(c3);
        }
    }
}