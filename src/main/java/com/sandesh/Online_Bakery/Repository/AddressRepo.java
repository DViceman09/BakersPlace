package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
