package com.example.ContactListApp.repository;

import com.example.ContactListApp.Contact;
import com.example.ContactListApp.exception.ContactException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContactRepositoryImpl implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("calling ContactRepositoryImpl->findAll");

        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact findById(Long id) {
        log.debug("calling ContactRepositoryImpl->findById with id: {}", id);
        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                ));
        if (contact != null) {
            return contact;
        }
        log.warn("Contact with id: {} not found", id);
        throw new ContactException("Contact with id: " + id + " not found");
    }


    @Override
    public Contact save(Contact contact) {
        log.debug("calling ContactRepositoryImpl->save contact: {}", contact);

        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("calling ContactRepositoryImpl->update contact: {}", contact);
        findById(contact.getId());
        String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id =?";
        jdbcTemplate.update(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId());
        return contact;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("calling ContactRepositoryImpl->deleteById with id: {}", id);

        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

}
