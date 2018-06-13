package com.example.Nile_Order.repository;

import com.example.Nile_Order.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {
}
