package com.alibabademo.alibaba.EmailReppo;

import com.alibabademo.alibaba.EmailEntity.EmailReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailReceiverReppo extends JpaRepository<EmailReceiver, Long> {

}
