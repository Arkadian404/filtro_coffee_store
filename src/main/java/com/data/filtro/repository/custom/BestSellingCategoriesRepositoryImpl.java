package com.data.filtro.repository.custom;

import com.data.filtro.model.Category;
import com.data.filtro.model.Invoice;
import com.data.filtro.model.InvoiceDetail;
import com.data.filtro.model.Product;
import com.data.filtro.model.custom.BestSellingCategories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestSellingCategoriesRepositoryImpl implements BestSellingCategoriesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BestSellingCategories> findBestSellingCategories(Integer month, Integer year) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BestSellingCategories> cq = cb.createQuery(BestSellingCategories.class);

        Root<InvoiceDetail> id = cq.from(InvoiceDetail.class);
        Join<InvoiceDetail, Invoice> i = id.join("invoice");
        Join<InvoiceDetail, Product> p = id.join("product");
        Join<InvoiceDetail, Category> c = p.join("category");


        cq.select(cb.construct(
                        BestSellingCategories.class,
                        p.get("category").get("id").alias("id"),
                        c.get("categoryName"),
                        cb.sum(id.get("quantity")).alias("quantity"),
                        cb.sum(cb.prod(id.get("quantity"), id.get("price"))).alias("total")
                )).where(cb.and(
                        cb.equal(cb.function("month", Integer.class, i.get("purchasedDate")), month),
                        cb.equal(cb.function("year", Integer.class, i.get("purchasedDate")), year)
                )).groupBy(c.get("id"))
                .orderBy(cb.desc(cb.sum(id.get("quantity"))));


        TypedQuery<BestSellingCategories> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
