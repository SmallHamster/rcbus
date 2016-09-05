package com.leoman.common.service.impl;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.common.service.GenericManager;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@SuppressWarnings("unchecked")
public class GenericManagerImpl<E, D extends IBaseJpaRepository<E>> implements GenericManager<E> {

    private D entityDAO;

    private static final Integer BITCH_SIZE = 50;

    @PersistenceContext
    public EntityManager em;

    public GenericManagerImpl() {
    }

    public EntityManager getEntityManager() {
        return em;
    }

    /***
     * 反射获取entiyDao
     *
     * @return
     */
    public D getEntityDAO() {
        if (entityDAO == null) {
            try {
                Field field = this.getClass().getDeclaredFields()[0];
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                entityDAO = (D) field.get(this);
                field.setAccessible(accessible);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ;
        }
        return this.entityDAO;
    }


    /****
     * 查询分页实现
     *
     * @param entity 实体类
     * @return
     */
    public Page<E> query(E entity, int start, int length) {
        Specification<E> spec = buildSpecification(entity);
        Page<E> page = getEntityDAO().findAll(spec, new PageRequest(start, length, Direction.DESC, "id"));
        return page;
    }

    /**
     * 按照指定字段查询指定条数的记录
     */

    public List<E> queryTop(String sort, boolean isAsc, int count) {
        Order o = new Order(Direction.DESC, sort);
        if (isAsc) {
            o = new Order(Direction.ASC, sort);
        }
        Page<E> page = getEntityDAO().findAll(new PageRequest(0, count, new Sort(o)));
        return page.getContent();
    }


    /**
     * 按照指定字段查询指定条数的记录
     */

    public List<E> queryTop(String sort, final String propertyName, final Object value, boolean isAsc, int count) {
        Order o = new Order(Direction.DESC, sort);
        if (isAsc) {
            o = new Order(Direction.ASC, sort);
        }
        Page<E> page = getEntityDAO().findAll(new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (value != null) {
                    list.add(cb.equal(root.get(propertyName), value));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, new PageRequest(0, count, new Sort(o)));
        return page.getContent();
    }

    /****
     * 分页规则构建
     * public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
     * CriteriaBuilder cb) {
     * List<Predicate> list = new ArrayList<Predicate>();
     * if(value!=null){
     * list.add(cb.equal(root.get(propertyName), value));
     * }
     * cb.not
     * if(um.getName()!=null && um.getName().trim().length()>0){
     * list.add(cb.like(root.get("name").as(String.class), "%"+um.getName()+"%"));
     * }
     * if(um.getUuid()>0){
     * list.add(cb.equal(root.get("uuid").as(Integer.class), um.getUuid()));
     * }
     * Predicate[] p = new Predicate[list.size()];
     * return cb.and(list.toArray(p));
     * }
     *
     * @param entity 实体类
     * @return
     */
    public Specification<E> buildSpecification(E entity) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
    }


    public void delete(E object) {
        getEntityDAO().delete(object);
    }


    public void deleteByPK(Serializable id) {
        getEntityDAO().delete(id);
    }


    public void deleteByPKs(Serializable[] ids) {
        for (Serializable s : ids) {
            getEntityDAO().delete(s);
        }

    }

    public void deletes(Collection<E> objects) {
        for (E obj : objects) {
            getEntityDAO().delete(obj);
        }
    }

    public boolean exists(Serializable id) {
        return getEntityDAO().exists(id);
    }


    public Class getEntityClass() {
        return null;
    }


    public Class getIdClass() {
        return null;
    }


    public String getIdName() {
        return null;
    }


    public E insert(E object) {
        return getEntityDAO().saveAndFlush(object);
    }


    public boolean isUnique(Object entity, String... uniquePropertyName) {
        return false;
    }


    public List<E> query(String hql, Object... values) {
        return null;
    }


    public List<E> query(Criterion... criterions) {
        return null;
    }


    public List<E> queryAll() {
        return getEntityDAO().findAll();
    }


    public List<E> queryAll(String orderBy, boolean isAsc) {
        Order o;
        if (isAsc) {
            o = new Order(Direction.ASC, orderBy);
        } else {
            o = new Order(Direction.DESC, orderBy);
        }
        Sort s = new Sort(o);
        return this.getEntityDAO().findAll(s);
    }


    public E queryByPK(Serializable id) {
        return getEntityDAO().findOne(id);
    }


    public List<E> queryByProperty(final String propertyName, final Object value) {
        return getEntityDAO().findAll(new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (value != null) {
                    String[] names = null;
                    if(propertyName.contains(".")) {
                        names = propertyName.split("\\.");
                        list.add(cb.equal(root.get(names[0]).get(names[1]), value));
                    }
                    else {
                        list.add(cb.equal(root.get(propertyName), value));
                    }
//                    list.add(cb.equal(root.get(names[0]).get(names[1]), value));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }


    public List<E> queryByProperty(final String propertyName, final Object value, String orderBy, boolean isAsc) {
        Order o;
        if (isAsc) {
            o = new Order(Direction.ASC, orderBy);
        } else {
            o = new Order(Direction.DESC, orderBy);
        }
        Sort s = new Sort(o);
        return getEntityDAO().findAll(new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (value != null) {
                    list.add(cb.equal(root.get(propertyName), value));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, s);
    }


    public E queryUniqueBy(final String propertyName, final Object value) {
        return getEntityDAO().findOne(new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (value != null) {
                    list.add(cb.equal(root.get(propertyName), value));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }


    public E save(E object) {
        return getEntityDAO().save(object);
    }

    @Override
    @Transactional
    public E saveList(List<E> list) {
        if (list == null || list.isEmpty()) return null;
//        int i = 0;
        for (E e : list) {
            this.save(e);
//            if (i % BITCH_SIZE == 0) {
//                getEntityManager().flush();
//            }
//            i++;
        }
        return null;
    }

    @Override
    public void updateList(List<E> list) {
        for (E e : list) {
            this.update(e);
        }
    }

    public E update(E object) {
        return getEntityDAO().save(object);
    }

    public List<E> query(final Predicate... predicate) {
        return getEntityDAO().findAll(new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                for (Predicate p : predicate) {
                    list.add(p);
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }


    /**
     * 根据条件查询某个实体的列表
     *
     * @param firstindex 开始行
     * @param maxresult  结束行
     * @param sql        sql语句
     * @return
     * @author zb
     */
    public Page<E> scroll(final int firstindex, final int maxresult, final String sql) {
//		List retrunList = new ArrayList();
//		EntityManager em = entityManagerFactory.createEntityManager();
//		Query query = em.createNativeQuery(sql);
//		query.setFirstResult(firstindex);
//		query.setMaxResults(maxresult);
//		retrunList = query.getResultList();
//		qr.setTotal(getCount(sql));
//		qr.setRows(retrunList);
//		em.close();
        return null;
    }

    @Override
    public Long getCount(com.leoman.common.service.Query query) {

        int count = getEntityManager().createQuery(query.newCriteriaQuery()).getResultList().size();
        return (long) count;
    }

    public Long getCount(String jpql) {
        return (Long) getEntityManager().createQuery(jpql).getResultList().get(0);
    }

    @Override
    public Integer getCountSql(String sql) {
        return getEntityManager().createNativeQuery(sql).getResultList().size();
    }

    public long count() {
        return getEntityDAO().count();
    }

    @Override
    public Page<E> queryPage(com.leoman.common.service.Query query) {
        int pageNo = query.getPagenum();
        int rowsPerPage = query.getPagesize();
        if (pageNo <= 0)
            pageNo = 1;
        if (rowsPerPage <= 0)
            rowsPerPage = 10;

        int count = getCount(query).intValue();

        // 当把最后一页数据删除以后,页码会停留在最后一个上必须减一
        int totalPageCount = count / rowsPerPage;
        if (pageNo > totalPageCount && (count % rowsPerPage == 0)) {
            pageNo = totalPageCount;
        }
        if (pageNo - totalPageCount > 2) {
            pageNo = totalPageCount + 1;
        }
        int firstResult = (pageNo - 1) * rowsPerPage;
        if (firstResult < 0) {
            firstResult = 0;
        }
        List<E> result = getEntityManager().createQuery(query.newCriteriaQuery()).setFirstResult(firstResult).setMaxResults(rowsPerPage).getResultList();
        return new PageImpl<E>(result, new PageRequest(pageNo, rowsPerPage), count);
    }

    @Override
    public List<E> queryAll(com.leoman.common.service.Query query) {
        List<E> result = getEntityManager().createQuery(query.newCriteriaQuery()).getResultList();
        return result;
    }

    @Override
    public List<E> queryByJpql(String jpql, Class<E> clazz) {
        return getEntityManager().createQuery(jpql, clazz).getResultList();
    }

    @Override
    public Integer updateByJpql(String jpql) {
        return getEntityManager().createQuery(jpql).executeUpdate();
    }

    @Override
    public List<E> queryBySql(String sql, Class<E> clazz) {
        return getEntityManager().createNativeQuery(sql, clazz).getResultList();
    }

    @Override
    public Integer updateBySql(String sql) {
        return getEntityManager().createNativeQuery(sql).executeUpdate();
    }

    @Override
    public Page queryPageByJpql(String jpql, int pageNo, int rowsPerPage) {
        if (pageNo <= 0)
            pageNo = 1;
        if (rowsPerPage <= 0)
            rowsPerPage = 7;

        String countJpql = "select count(*) from (" + jpql + ")";
        int count = getCount(countJpql).intValue();

        // 当把最后一页数据删除以后,页码会停留在最后一个上必须减一
        int totalPageCount = count / rowsPerPage;
        if (pageNo > totalPageCount && (count % rowsPerPage == 0)) {
            pageNo = totalPageCount;
        }
        if (pageNo - totalPageCount > 2) {
            pageNo = totalPageCount + 1;
        }
        int firstResult = (pageNo - 1) * rowsPerPage;
        if (firstResult < 0) {
            firstResult = 0;
        }
        List result = getEntityManager().createQuery(jpql).setFirstResult(firstResult).setMaxResults(rowsPerPage)
                .getResultList();
        return new PageImpl<E>(result, new PageRequest(pageNo - 1, rowsPerPage), totalPageCount == 0 ? 1 : totalPageCount);
    }
}
