package com.webagregator.webagregator.app.repositories;

import com.webagregator.webagregator.domain.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    // Можно добавить дополнительные методы для работы с админами, если необходимо
}
