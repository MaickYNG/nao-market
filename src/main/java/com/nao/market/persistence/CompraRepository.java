package com.nao.market.persistence;

import com.nao.market.domain.Purchase;
import com.nao.market.domain.PurchaseItem;
import com.nao.market.domain.repository.PurchaseRepository;
import com.nao.market.persistence.crud.CompraCrudRepository;
import com.nao.market.persistence.entity.Compra;
import com.nao.market.persistence.entity.ComprasProducto;
import com.nao.market.persistence.entity.ComprasProductoPK;
import com.nao.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }



}
