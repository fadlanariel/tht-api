package com.fadlan.tht.repository;

import com.fadlan.tht.dto.ServiceDto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepository {

    private final DataSource dataSource;

    public ServiceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ServiceDto> findAllServices() throws SQLException {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM services";
        List<ServiceDto> services = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                services.add(new ServiceDto(
                        rs.getString("service_code"),
                        rs.getString("service_name"),
                        rs.getString("service_icon"),
                        rs.getLong("service_tariff")));
            }
        }

        return services;
    }
}
