package com.info.ecommerce.repository;

import com.info.ecommerce.model.CustomersWishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersWishItemRepository extends JpaRepository<CustomersWishItem, Long> {

    List<CustomersWishItem> findAllByCustomerId(Long customerId);

    Optional<CustomersWishItem> findByCustomerIdAndItemId(Long customerId, Long itemId);

}
