package com.BitCoinService;

import com.BitCoinService.Model.BitcoinPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinRepository  extends JpaRepository<BitcoinPayment, Integer> {
}
