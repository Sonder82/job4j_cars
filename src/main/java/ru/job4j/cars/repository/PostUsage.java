package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public class PostUsage {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            var user = new User();
            user.setLogin("admin_post");
            user.setPassword("admin");
            create(user, sf);

            var post = new Post();
            post.setDescription("description of the ad");
            post.setCreated(LocalDateTime.now());
            post.setUser(user);
            post.setPriceHistoryList(List.of(
                    new PriceHistory(0, 100, 200, LocalDateTime.now().minusWeeks(3)),
                    new PriceHistory(0, 300, 200, LocalDateTime.now().minusWeeks(5))
            ));
            create(post, sf);

            var stored = sf.openSession()
                    .createQuery("from Post where id = :fId", Post.class)
                    .setParameter("fId", post.getId())
                    .getSingleResult();
            stored.getPriceHistoryList().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> void create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(model);
        session.getTransaction().commit();
        session.close();
    }
}
