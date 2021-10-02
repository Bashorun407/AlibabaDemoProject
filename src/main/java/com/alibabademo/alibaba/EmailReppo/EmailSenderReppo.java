package com.alibabademo.alibaba.EmailReppo;

import com.alibabademo.alibaba.EmailEntity.EmailSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailSenderReppo extends JpaRepository<EmailSender, Long> {

    Optional<EmailSender> findByEmailNumber(Long emailNumber);
}
