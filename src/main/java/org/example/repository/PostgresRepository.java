package org.example.repository;

import org.example.entity.PostgresBook;
import org.example.entity.PostgresSale;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresRepository {
    private Connection connection;

    private static PostgresRepository instance = null;

    public static PostgresRepository getInstance(){
        if (instance == null){
            try {
                instance = new PostgresRepository();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return instance;
    }

    private PostgresRepository() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/bookstore";
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        connection = dataSource.getConnection(
                "postgres",
                "45dq71"
        );
    }

    public List<PostgresSale> findSum(String paymentType){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT sale.id_sale, sale.date, SUM(position_in_sale.quantity * price.price_per_piece * (1-COALESCE(bonus_card.percentage_discount*0.01, 0)))\n" +
                    "FROM sale\n" +
                    "         JOIN position_in_sale ON position_in_sale.id_sale = sale.id_sale\n" +
                    "         JOIN product ON product.id_product = position_in_sale.id_product\n" +
                    "         JOIN price ON price.id_product = product.id_product\n" +
                    "         JOIN method_of_payment ON method_of_payment.id_method_of_payment = sale.id_method_of_payment\n" +
                    "         LEFT JOIN bonus_card ON bonus_card.id_bonus_card = sale.id_bonus_card\n" +
                    "WHERE method_of_payment.name = ? AND price.date = (SELECT MAX(date) FROM price WHERE id_product = product.id_product AND date <= sale.date)\n" +
                    "GROUP BY sale.id_sale, sale.date;");

            statement.setString(1, paymentType);

            ResultSet resultSet = statement.executeQuery();
            List<PostgresSale> postgresSales = new ArrayList<>();

            while (resultSet.next()){
                postgresSales.add(new PostgresSale(resultSet.getInt(1)
                        , resultSet.getDate(2).toString()
                        , resultSet.getDouble(3)));
            }

            return postgresSales;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PostgresBook> finBooks(String genre){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT product.name, book_product.author\n" +
                    "FROM product\n" +
                    "JOIN book_product ON book_product.id_product = product.id_product\n" +
                    "JOIN book_product_genre ON book_product.id_book_product = book_product_genre.id_book_product\n" +
                    "JOIN genre ON book_product_genre.id_genre = genre.id_genre\n" +
                    "WHERE genre.name = ?");
            statement.setString(1, genre);

            ResultSet resultSet = statement.executeQuery();
            List<PostgresBook> postgresBooks = new ArrayList<>();
            while (resultSet.next()){
                postgresBooks.add(new PostgresBook(resultSet.getString(1),resultSet.getString(2)));
            }
            return postgresBooks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
