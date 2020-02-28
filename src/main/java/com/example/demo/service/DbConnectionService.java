package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DbConnectionService {

    public ApiConnectionImpl buildConnection(String host, String port, String user, String pass, String dbName) throws SQLException {
        return new ApiConnectionImpl(host, port, user, pass);
    }

}
