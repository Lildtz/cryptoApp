package com.nh.cryptoapp.repository;

import com.nh.cryptoapp.dto.CoinTransationDTO;
import com.nh.cryptoapp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    private static String INSERT = "INSERT INTO COIN (NAME, PRICE, QUANTITY, DATETIME) VALUES (?,?,?,?)";

    private static String SELECT_ALL = "SELECT NAME, SUM(QUANTITY) AS QUANTITY FROM COIN GROUP BY NAME";

    private static String SELECT_BY_NAME = "SELECT * FROM COIN WHERE NAME = ?";
    private static String DELETE = "DELETE FROM COIN WHERE ID = ?";
    private static String UPDATE = "UPDATE COIN SET NAME = ?, PRICE = ?, QUANTITY = ? WHERE ID = ?";
    private final JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public Coin insert(Coin coin) {
        Object[] attr = new Object[] {
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT, attr);
        return coin;
    }
    public Coin update(Coin coin) {
        Object[] attr = new Object[] {
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getId()
        };
        jdbcTemplate.update(UPDATE, attr);
        return coin;
    }
    public List<CoinTransationDTO> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinTransationDTO>() {
            @Override
            public CoinTransationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CoinTransationDTO coin = new CoinTransationDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
        });
    }

    public List<Coin> getByName(String name) {
        Object[] attr = new Object[] { name };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();
                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDateTime(rs.getTimestamp("datetime"));

                return coin;
            }
        }, attr);
    }

    public int remove(int id) {
        return jdbcTemplate.update(DELETE, id);
    }
}
