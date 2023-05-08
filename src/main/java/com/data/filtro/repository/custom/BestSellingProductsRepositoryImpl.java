package com.data.filtro.repository.custom;

import com.data.filtro.model.Invoice;
import com.data.filtro.model.InvoiceDetail;
import com.data.filtro.model.Product;
import com.data.filtro.model.custom.BestSellingProducts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestSellingProductsRepositoryImpl implements BestSellingProductsRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<BestSellingProducts> findBestSellingProducts(Integer month, Integer year) {
        //        String queryString = "select p.id, p.productName, sum(id.quantity) as quantity, sum(id.quantity*id.price) as total from Invoice i join InvoiceDetail id on i.id = id.id join Product p on id.product.id = p.id where month (i.purchasedDate) =:month and year (i.purchasedDate) =:year group by p.id order by quantity desc ";
        //        TypedQuery<BestSellingProducts> query = (TypedQuery<BestSellingProducts>) entityManager.createNativeQuery(queryString, BestSellingProducts.class);
        //        query.setParameter("month", month);
        //        query.setParameter("year", year);
        //        return query.getResultList();
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<BestSellingProducts> cq = cb.createQuery(BestSellingProducts.class);
//
//        Root<Invoice> i = cq.from(Invoice.class);
//        Join<Invoice, InvoiceDetail> id = i.join("InvoiceDetail");
//        Join<InvoiceDetail, Product> p = id.join("Product");
//
//        cq.select(cb.construct(
//                        BestSellingProducts.class,
//                        p.get("id"),
//                        p.get("productName"),
//                        cb.sum(id.get("quantity")).alias("quantity"),
//                        cb.sum(cb.prod(id.get("quantity"), id.get("price"))).alias("total")
//                )).where(cb.and(
//                        cb.equal(cb.function("month", Integer.class, i.get("purchasedDate")), month),
//                        cb.equal(cb.function("year", Integer.class, i.get("purchasedDate")), year)
//                )).groupBy(p.get("id"), p.get("productName"))
//                .orderBy(cb.desc(cb.sum(id.get("quantity"))));
//
//        TypedQuery<BestSellingProducts> query = entityManager.createQuery(cq);
//        return query.getResultList();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BestSellingProducts> cq = cb.createQuery(BestSellingProducts.class);

        Root<InvoiceDetail> id = cq.from(InvoiceDetail.class);
        Join<InvoiceDetail, Product> p = id.join("product");
        Join<InvoiceDetail, Invoice> i = id.join("invoice");

        cq.select(cb.construct(
                        BestSellingProducts.class,
                        p.get("id"),
                        p.get("productName"),
                        cb.sum(id.get("quantity")).alias("quantity"),
                        cb.sum(cb.prod(id.get("quantity"), id.get("price"))).alias("total")))
                .where(cb.and(
                        cb.equal(cb.function("month", Integer.class, i.get("purchasedDate")), month),
                        cb.equal(cb.function("year", Integer.class, i.get("purchasedDate")), year)))
                .groupBy(p.get("id"), p.get("productName"))
                .orderBy(cb.desc(cb.sum(id.get("quantity"))));

        TypedQuery<BestSellingProducts> query = entityManager.createQuery(cq);
        return query.getResultList();

    }
}

