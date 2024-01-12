package com.example.ContactListApp.repository;

import com.example.ContactListApp.Contact;

import java.util.List;

public interface ContactRepository {

    Contact findById(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

}
