package com.data.filtro.repository.custom;

import com.data.filtro.model.Flavor;
import com.data.filtro.model.Invoice;
import com.data.filtro.model.InvoiceDetail;
import com.data.filtro.model.Product;
import com.data.filtro.model.custom.BestSellingFlavors;
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
public class BestSellingFlavorsRepositoryImpl implements BestSellingFlavorsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BestSellingFlavors> findBestSellingFlavors(Integer month, Integer year) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BestSellingFlavors> cq = cb.createQuery(BestSellingFlavors.class);

        Root<Invoice> i = cq.from(Invoice.class);
        Join<Invoice, InvoiceDetail> id = i.join("invoiceDetails");
        Join<Invoice, Product> p = id.join("product");
        Join<Invoice, Flavor> f = p.join("flavor");

        cq.select(cb.construct(
                        BestSellingFlavors.class,
                        p.get("flavor").get("id").alias("id"),
                        f.get("flavorName").alias("flavorName"),
                        cb.sum(id.get("quantity")).alias("quantity"),
                        cb.sum(cb.prod(id.get("quantity"), id.get("price"))).alias("total")
                )).where(cb.and(
                        cb.equal(cb.function("month", Integer.class, i.get("purchasedDate")), month),
                        cb.equal(cb.function("year", Integer.class, i.get("purchasedDate")), year)
                )).groupBy(f.get("id"))
                .orderBy(cb.desc(cb.sum(id.get("quantity"))));

        TypedQuery<BestSellingFlavors> query = entityManager.createQuery(cq);
        return query.getResultList();


    }
}
