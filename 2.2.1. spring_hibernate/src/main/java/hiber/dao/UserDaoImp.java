package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   @SuppressWarnings("unchecked")
   public Optional<User> getUserByCar(String model, int series) {
      String hql = "from User user where user.car.model = :model and user.car.series = :series";
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
         query.setParameter("model", model).setParameter("series", series);
         return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
      } catch (NoResultException ex) {
         return Optional.empty();
      }
   }
}
