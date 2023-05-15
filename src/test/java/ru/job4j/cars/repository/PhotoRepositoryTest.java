package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Photo;

import static org.assertj.core.api.Assertions.*;

class PhotoRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @BeforeEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE FROM Photo").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    @Test
    void whenCreatePhotoThenGetIt() {
        var photo1 = photoRepository.create(new Photo(1, "name", "path")).get();
        assertThat(photoRepository.findById(photo1.getId()).get()).isEqualTo(photo1);
    }

    @Test
    public void whenAddNewPhotoThenUpdate() {
        Photo photo = photoRepository.create(new Photo(1, "name", "path")).get();
        photo.setName("newName");
        photoRepository.update(photo);
        assertThat(photoRepository.findById(photo.getId()).get().getName()).isEqualTo("newName");
    }

    @Test
    public void whenDeletePhotoThenCheckContains() {
        Photo photo1 = photoRepository.create(new Photo(1, "name", "path")).get();
        Photo photo2 = photoRepository.create(new Photo(2, "name", "path2")).get();
        photoRepository.delete(photo1.getId());
        assertThat(photoRepository.findAllOrderById()).hasSize(1)
                .contains(photo2)
                .doesNotContain(photo1);
    }
}