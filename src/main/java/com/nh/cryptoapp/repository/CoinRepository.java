package com.nh.cryptoapp.repository;

import com.nh.cryptoapp.dto.CoinTransationDTO;
import com.nh.cryptoapp.entity.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {


    private EntityManager entityManager;

    public CoinRepository(EntityManager entityManager) { this.entityManager = entityManager; }

    @Transactional
    public Coin insert(Coin coin) {
        entityManager.persist(coin);
        return coin;
    }
    @Transactional
    public Coin update(Coin coin) {
        entityManager.merge(coin);
        return coin;
    }
    public List<CoinTransationDTO> getAll() {
        String jpql = "select new com.nh.cryptoapp.dto.CoinTransationDTO(c.name, sum(c.quantity)) from Coin c group by c.name";
        TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql, CoinTransationDTO.class);
        return query.getResultList();
    }
//
//    public List<Coin> getByName(String name) {
//        Object[] attr = new Object[] { name };
//        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
//            @Override
//            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Coin coin = new Coin();
//                coin.setId(rs.getInt("id"));
//                coin.setName(rs.getString("name"));
//                coin.setPrice(rs.getBigDecimal("price"));
//                coin.setQuantity(rs.getBigDecimal("quantity"));
//                coin.setDateTime(rs.getTimestamp("datetime"));
//
//                return coin;
//            }
//        }, attr);
//    }
//
//    public int remove(int id) {
//        return jdbcTemplate.update(DELETE, id);
//    }
}
